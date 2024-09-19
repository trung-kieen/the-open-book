package com.example.the_open_book.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.the_open_book.user.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * CustomUserDetailService
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  public final UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
    // User email a claims for extract user
    var user = userRepository.findByEmail(userEmail).orElseThrow(() -> {
      throw new UsernameNotFoundException("email is not valid");
    });
    return user;
  }

}
