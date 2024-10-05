package com.example.the_open_book.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.the_open_book.user.DataMigration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * JwtTokenUtil
 */
@Component
public class JwtTokenUtil {

  // TODO: use application properties
  private static final long jwtTokenExpMillis = 900000; // 15 minuts

  /**
   * Require to encript token and validate token.
   * Key must have length enough with charset is number, alpha and some special
   * character.
   * For testing purpose plz use {@link DataMigration#generateSafeToken()} without
   * specical character like `-` or `_` to a valid secret key that long enough
   * @see <a href="https://stackoverflow.com/questions/74511835/the-specified-key-byte-array-is-192-bits-which-is-not-secure-enough-for-any-jwt">charset for key</a>
   */
  // TODO: Use @Value instead
  private CharSequence secretKey = "X8s7BPFUGSrDDiPvcxQMpOtXz8W14DV9IvEInh7l1dhtw";

  /**
   * @param token       jwt token without bear prefix
   * @param userDetails the user with authorities information
   * @return determined token is valid and this token is tie to user in database
   */
  public boolean isValidToken(String token, UserDetails userDetails) {

    final var email = getUsername(token);
    final var exp = extractClaim(token, Claims::getExpiration);
    final boolean isTokenExpired = exp.before(new Date(System.currentTimeMillis()));

    if (email != userDetails.getUsername())
      return false;
    if (isTokenExpired)
      return false;

    return true;

  }

  private <T> T extractClaim(String token, Function<Claims, T> extractorMethod) {
    var claims = extractAllClaims(token);
    return extractorMethod.apply(claims);
  }

  private Claims extractAllClaims(String token) {
     var parser = Jwts.parser()
        .verifyWith(getSignKey())
        .build();

     var claims =  parser.parseSignedClaims(token)
        .getPayload();
    return claims;
  }

  public String getUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Generate token from user object
   *
   * @param userDetails aka principal after authentication process
   * @return token string
   */
  public String generateToken(UserDetails userDetails) {
    return generateToken(userDetails, new HashMap<>());
  }

  /**
   * Generate token from user object and bind claims key pair into token
   *
   * @param userDetails aka principal after authentication process
   * @param claims      use {@link HashMap} to build key pair information about
   *                    this principal
   * @return token string
   */
  public String generateToken(UserDetails userDetails, Map<? extends String, ? extends Object> claims) {
    final String emailAsSub = userDetails.getUsername();
    final Date iat = new Date(System.currentTimeMillis());
    final Date exp = new Date(System.currentTimeMillis() + jwtTokenExpMillis);

    // NOTE: Should we map authorities as claim?
    String jwtToken = Jwts.builder()

        .claims()
        .subject(emailAsSub)
        .issuedAt(iat)
        .expiration(exp)
        // NOTE:
        // .add(claims)
        // ... etc ...
        .add(claims)
        .and() // return back to the JwtBuilder
        .signWith(getSignKey(), Jwts.SIG.HS256) // resume JwtBuilder calls
        // ... etc ...
        .compact();

    return jwtToken;
  }

  /**
   * Generate key for create token and validate token
   */
  private SecretKey getSignKey() {
    byte[] keybytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keybytes);
  }

}
