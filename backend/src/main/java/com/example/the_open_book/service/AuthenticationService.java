package com.example.the_open_book.service;

import com.example.the_open_book.exception.ApplicationException;
import com.example.the_open_book.payload.request.AuthenticationRequest;
import com.example.the_open_book.payload.request.RegisterDTO;
import com.example.the_open_book.payload.response.AuthenticationResponse;

/**
 * AuthenticationService
 */

public interface AuthenticationService {

  void register(RegisterDTO registerDTO);

void validateToken(String token) throws ApplicationException;


AuthenticationResponse authenticate(AuthenticationRequest authRequest);

 void activateAccount(String code);

}
