package com.example.the_open_book.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.the_open_book.payload.request.RegisterDTO;
import com.example.the_open_book.service.AuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * AuthController
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @GetMapping("/register")
  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseEntity<?> register(@Valid RegisterDTO registerDTO) throws Exception {
    authenticationService.register(registerDTO);

    return null;
  }

}
