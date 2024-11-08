package com.example.the_open_book.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.the_open_book.exception.ApplicationException;
import com.example.the_open_book.payload.request.AuthenticationRequest;
import com.example.the_open_book.payload.request.RegisterDTO;
import com.example.the_open_book.payload.response.AuthenticationResponse;
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

  @PostMapping("register")
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  // TODO: Add @Valid
  public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO registerDTO) throws Exception {
    authenticationService.register(registerDTO);
    return ResponseEntity.ok().body("Register success checkout email for activate");

  }
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("validate-token/{token}")
  public void validateToken(@PathVariable String token ) throws ApplicationException{
    authenticationService.validateToken(token);
  }

  @PostMapping("authenticate")
  @ResponseStatus(value = HttpStatus.OK)
  public AuthenticationResponse register(@Valid @RequestBody AuthenticationRequest request) throws Exception {
    return authenticationService.authenticate(request);
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @GetMapping("activate-account")
  public void activateAccount(@RequestParam(value = "code" , required = true ) String code){
    authenticationService.activateAccount(code);
    // TODO: forward request to other page in client request instead of blank page

  }

}
