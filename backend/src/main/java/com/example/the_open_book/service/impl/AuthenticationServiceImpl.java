package com.example.the_open_book.service.impl;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.the_open_book.email.EmailSenderService;
import com.example.the_open_book.exception.ApplicationException;
import com.example.the_open_book.exception.BusinnesException;
import com.example.the_open_book.exception.ErrorMessage;
import com.example.the_open_book.payload.request.AuthenticationRequest;
import com.example.the_open_book.payload.request.RegisterDTO;
import com.example.the_open_book.payload.response.AuthenticationResponse;
import com.example.the_open_book.role.RoleName;
import com.example.the_open_book.role.RoleRepository;
import com.example.the_open_book.security.JwtTokenUtil;
import com.example.the_open_book.server.UrlContextBuilder;
import com.example.the_open_book.service.AuthenticationService;
import com.example.the_open_book.token.EmailTokenTrategy;
import com.example.the_open_book.token.Token;
import com.example.the_open_book.token.TokenService;
import com.example.the_open_book.user.User;
import com.example.the_open_book.user.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * AuthenticationServiceImpl
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
  private final RoleRepository roleRepository;
  private final TokenService tokenService;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final EmailSenderService emailSenderService;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final UrlContextBuilder urlBuilder;



  // TODO
  private long verifiedUserTokenExpireMinutes = 15;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public void register(RegisterDTO registerDTO) {
    // TODO: check if user exist
    // If not enable => Send register if not exist none expired register token exist
    // Else => Return invalid register
    /*
     * var user = userRepository.findByEmail(registerDTO.email());
     * if (user.isPresent()){
     * boolean isUserVerfified = user.get().getEnabled();
     * if (isUserVerfified){
     * throw new ApplicationException("Account ready exist");
     * }
     *
     * if (!tokenService.activeTokenForUser(user.get())){
     * createAndSaveToken(user.get());
     * throw new
     * ApplicationException("Recheck your email to new activate account request");
     * }
     *
     *
     *
     * }
     *
     */
    logger.info("loger before user role accesstor");
    var userRole = roleRepository.findByName(RoleName.ROLE_USER)
        // .orElseThrow(() -> new StaticResourceNotDefinedException("Not found role ", registerDTO));
        .orElseThrow(() -> new BusinnesException(ErrorMessage.STATIC_RESOURCE_NOT_INITIALIZE ,registerDTO ));

    // Creat user and save to database without enable
    var registerUser = User.builder()
        .firstname(registerDTO.firstname())
        .lastname(registerDTO.lastname())
        .password(passwordEncoder.encode(registerDTO.password()))
        .email(registerDTO.email())
        .roles(Set.of(userRole))
        .accountLocked(false)
        .enabled(false)
        .build();
    userRepository.save(registerUser);

    sendVerifiedEmail(registerUser);
  }

  private Token createAndSaveToken(User user) {
    var randomCode = generateVerification(EmailTokenTrategy.UUID_CODE);
    var token = Token.builder()
        .token(randomCode)
        .user(user)
        .createdAt(LocalDateTime.now())
        .expiredAt(LocalDateTime.now().plusMinutes(verifiedUserTokenExpireMinutes))
        .build();

    tokenService.save(token);
    return token;

  }

  /**
   * Create token and send email this token to user
   */
  private void sendVerifiedEmail(User user) {
    var token = createAndSaveToken(user);
    // TODO: change to appropriate with client site in angular
      String confirmationUrl = urlBuilder.emailCofirmation(token);
    /*
     * Why token code for email do not need to be unique
     * TODO: change to uuid as token code
     * https://stackoverflow.com/questions/34947001/how-do-i-generate-a-unique-token
     * -for-e-mail-verification
     */
    emailSenderService.sendActivationAccount(user.getEmail(), token.getToken(), confirmationUrl);
    logger.info("Send user activation email for url {}", confirmationUrl);

  }

  private String generateVerification(EmailTokenTrategy tokenTrategy){
    if (tokenTrategy == EmailTokenTrategy.UUID_CODE){
      return generateVerificationUUIDCODE();
    }
    if (tokenTrategy == EmailTokenTrategy.DIGIT_CODE){
    return generateVerificationDigitCode(6);
    }

    throw new ApplicationException("Not apply token string stragy");
  }

  private String generateVerificationUUIDCODE() {
    return UUID.randomUUID().toString();
  }

  private String generateVerificationDigitCode(int codeLength) {
    String character = "0123456789";

    var builder = new StringBuilder();
    SecureRandom secureRandom = new SecureRandom();
    for (int j = 0; j < codeLength; j++) {
      int randomIndex = secureRandom.nextInt(character.length());
      builder.append(character.charAt(randomIndex));
    }
    return builder.toString();
  }

  @Override
  public void validateToken(String token) throws ApplicationException {
    var tokenEntity = tokenService.findByToken(token)
        .orElseThrow(() -> new BusinnesException(ErrorMessage.INVALID_TOKEN));

    final boolean isTokenExpired = LocalDateTime.now().isAfter(tokenEntity.getExpiredAt());
    if (isTokenExpired) {
      // Resend token email activation
      sendVerifiedEmail(tokenEntity.getUser());
      // throw new BadUserRequestException("Activation email is expired please recheck email for new activation code");
      throw new BusinnesException(ErrorMessage.TOKEN_EXPIRE_RESEND_EMAIL);
    }
    tokenEntity.setValidatedAt(LocalDateTime.now());
    tokenEntity.getUser().setEnabled(true);
  }

  @Override
  public AuthenticationResponse authenticate(AuthenticationRequest req) {
    // TODO :Check if use is enable

    var authToken = new UsernamePasswordAuthenticationToken(req.email(), req.password());
    var auth = authenticationManager.authenticate(authToken);
    var context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(auth);
    SecurityContextHolder.setContext(context);
    var jwtToken = jwtTokenUtil.generateToken((User) auth.getPrincipal());
    var res = AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
    return res;

  }


  @Override
  @Transactional
  public void activateAccount(String code) {
    var token = tokenService.findByToken(code).orElseThrow(() -> new ApplicationException("Opps! Bad activation request"));
    if ( token.isExpired()){
      sendVerifiedEmail(token.getUser());
      throw new ApplicationException("Your activation email has expired. We've sent you a new one. Please check your inbox to activate your account.");
    }
    tokenService.activeUserMatchToken(token);
    token.setValidatedAt(LocalDateTime.now());
    tokenService.save(token);
  }
}
