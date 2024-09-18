package com.example.the_open_book.security;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

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
  private static final long jwtTokenExpMillis = 0;
  private CharSequence secretKey;

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
    var claims = extractClaims(token);
    return extractorMethod.apply(claims);
  }

  private Claims extractClaims(String token) {
    var claims = Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();
    return claims;
  }

  private SecretKey getSignKey() {
    byte[] keybytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keybytes);
  }

  public String getUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  private String generateToken(UserDetails userDetails) {
    return generateToken(userDetails, new HashMap<>());
  }

  private String generateToken(UserDetails userDetails, Map<? extends String, ? extends Object> claims) {
    final String emailAsSub = userDetails.getUsername();
    final Date iat = new Date(System.currentTimeMillis());
    final Date exp = new Date(System.currentTimeMillis() + jwtTokenExpMillis);

    Collection<? extends String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::toString)
        .toList();
    String jwtToken = Jwts.builder()

        .claims()
        .subject(emailAsSub)
        .issuedAt(iat)
        .expiration(exp)
        .add(claims)
        .add("authorities", authorities)
        // ... etc ...
        .and() // return back to the JwtBuilder

        .signWith(getKey()) // resume JwtBuilder calls
        // ... etc ...
        .compact();

    return jwtToken;
  }

  private Key getKey() {
    byte[] key = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(key);
  }

}
