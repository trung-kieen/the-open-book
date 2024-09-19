package com.example.the_open_book.service.impl;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.the_open_book.email.EmailSenderService;
import com.example.the_open_book.exception.StaticResourceNotDefinedException;
import com.example.the_open_book.payload.request.RegisterDTO;
import com.example.the_open_book.role.RoleRepository;
import com.example.the_open_book.service.AuthenticationService;
import com.example.the_open_book.token.Token;
import com.example.the_open_book.token.TokenRepository;
import com.example.the_open_book.user.User;
import com.example.the_open_book.user.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * AuthenticationServiceImpl
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
  private final RoleRepository roleRepository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final EmailSenderService emailSenderService;
  // TODO
  private long verifiedUserTokenExpireMinutes = 15;

  @Override
  public void register(RegisterDTO registerDTO) {
    var userRole = roleRepository.findByName(registerDTO.email())
        .orElseThrow(() -> new StaticResourceNotDefinedException(registerDTO));

    var registerUser = User.builder()
        .firstname(registerDTO.firstname())
        .lastname(registerDTO.lastname())
        .password(passwordEncoder.encode(registerDTO.password()))
        .email(registerDTO.email())
        .dateOfBirth(registerDTO.dateOfBirth())
        .roles(Set.of(userRole))
        .accountLocked(false)
        .enabled(false)
        .build();
    userRepository.save(registerUser);

    // sendVerifiedEmail(registerUser);
  }

  private void sendVerifiedEmail(User registerUser) {
    var randomCode = generateVerificationTokenCode(6);
    var token = Token.builder()
        .user(registerUser)
        .createdAt(LocalDateTime.now())
        .expiredAt(LocalDateTime.now().plusMinutes(verifiedUserTokenExpireMinutes))
        .build();

    tokenRepository.save(token);

    // TODO
    String confirmationUrl = "http://localhost:4200/activate-account";
    emailSenderService.sendActivationAccount(registerUser.getEmail(), token.getToken(), confirmationUrl);

  }

  private String generateVerificationTokenCode(int codeLength) {
    String character = "0123456789";

    var builder = new StringBuilder();
    SecureRandom secureRandom = new SecureRandom();
    for (int j = 0; j < codeLength; j++) {
      int randomIndex = secureRandom.nextInt(character.length());
      builder.append(character.charAt(randomIndex));
    }
    return builder.toString();
  }

}
