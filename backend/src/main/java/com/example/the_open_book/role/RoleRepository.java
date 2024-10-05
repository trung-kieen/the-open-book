package com.example.the_open_book.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * RoleRepository
 */
@Repository
public interface RoleRepository  extends JpaRepository<Role , Long>  {


  /**
   * Find with predefined role name in class {@link RoleName}
   */
  Optional<Role> findByName(RoleName name);


}
