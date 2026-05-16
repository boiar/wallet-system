package org.boiar.walletmanagement.user.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.boiar.walletmanagement.shared.entity.EntityAuditTimingData;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Embedded private EntityAuditTimingData timingData = new EntityAuditTimingData();

  /* Cols */
  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @Column(name = "phone", unique = true, nullable = false)
  private String phone;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "is_enabled")
  private boolean enabled;

  @Column(name = "is_email_verified")
  private boolean emailVerified;

  @Column(name = "profile_picture_url")
  private String profilePictureUrl;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.email;
  }


  @Override
  public boolean isEnabled() {
    return this.enabled;
  }

  public boolean isVerified() {
    return this.emailVerified;
  }


  public String getFullName() {
    return this.firstName + " " + this.lastName;
  }

  @PrePersist
  public void prePersist() {
    if (timingData == null) timingData = new EntityAuditTimingData();
    timingData.setCreatedDate(LocalDateTime.now());
    timingData.setUpdatedDate(LocalDateTime.now());
  }

  @PreUpdate
  public void preUpdate() {
    if (timingData == null) timingData = new EntityAuditTimingData();
    timingData.setUpdatedDate(LocalDateTime.now());
  }
}
