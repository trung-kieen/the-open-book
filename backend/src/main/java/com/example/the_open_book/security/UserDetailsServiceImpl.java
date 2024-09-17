package com.example.the_open_book.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.the_open_book.user.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * CustomUserDetailService
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  public final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
    // User email a claims for extract user
    var user = userRepository.findByEmail(userEmail).orElseThrow(() -> {
      throw new UsernameNotFoundException("email is not valid");
    });
    return user;

    // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method
    // 'loadUserByUsername'");
  }

}
