package com.example.the_open_book.user;

import java.net.InetAddress;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.the_open_book.role.Role;
import com.example.the_open_book.role.RoleName;
import com.example.the_open_book.role.RoleRepository;

import lombok.RequiredArgsConstructor;

/**
 * DataMigration
 */
@Component
@RequiredArgsConstructor
public class DataMigration implements CommandLineRunner {

  private final RoleRepository roleRepository;


  @Override
  public void run(String... args) throws Exception {
    generateRoleBase();
    // System.out.println(generateSafeToken());
    System.out.println(InetAddress.getLocalHost().getHostAddress());

  }

  /*
   * https://stackoverflow.com/questions/55545957/io-jsonwebtoken-security-weakkeyexception-the-verification-keys-size-is-48-bit
   */
  public String generateSafeToken(){

    SecureRandom  ran = new SecureRandom();

    byte [] bytes = new byte[36];

    // Generate random value for bytes variable
    ran.nextBytes(bytes);

    // Use base64 to convert to string base
    var encoder = Base64.getUrlEncoder().withoutPadding();
    return encoder.encodeToString(bytes);
  }


  private void generateRoleBase(){
    for (var role : RoleName.values()) {

      try {
        var r = Role.builder()
            .name(role)
            .build();
        roleRepository.save(r);

      } catch (Exception e) {
      }
    }
  }

}
