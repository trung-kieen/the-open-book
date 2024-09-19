package com.example.the_open_book.service;

import com.example.the_open_book.payload.request.RegisterDTO;

/**
 * AuthenticationService
 */

public interface AuthenticationService {

  void register(RegisterDTO registerDTO);

}
