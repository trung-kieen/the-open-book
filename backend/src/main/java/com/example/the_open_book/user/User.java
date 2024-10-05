package com.example.the_open_book.user;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.the_open_book.role.Role;
import com.example.the_open_book.token.Token;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@Entity
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")

/**
 * Class implement Userdetail for authetication and principal for get object in
 * security context holder
 */
public class User implements UserDetails, Principal {
  @Id
  @Column(name = "user_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @NotBlank
  @NotNull
  private String firstname;

  @NotBlank
  @NotNull
  private String lastname;

  @Temporal(TemporalType.DATE)
  @Column(name = "date_of_birth", nullable = true)
  private LocalDate dateOfBirth;

  @Basic
  @Column(name = "password", nullable = false, length = 100)
  private String password;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<Token> tokens;

  @NotBlank
  @Column(name = "email", nullable = false, unique = true, length = 100)
  @Email
  private String email;

  @Column(name = "account_locked", columnDefinition = "boolean default false")
  private Boolean accountLocked;

  /**
   * User need to verified email to enable account
   */
  @Column(name = "enabled", columnDefinition = "boolean default false")
  private Boolean enabled;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
  private Set<Role> roles;

  @CreatedDate
  @Column(name = "create_at")
  private LocalDateTime createAt; // or createdDate

  @LastModifiedDate
  @Column(name = "last_update")
  private LocalDateTime lastUpdate; // or lastModifiedDate

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public String getName() {
    return email;
  }

  /**
   * Calculate field
   */
  public String getFullname() {
    return firstname + " " + lastname;
  }

}
