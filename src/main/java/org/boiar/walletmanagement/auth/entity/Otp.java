package org.boiar.walletmanagement.auth.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.boiar.walletmanagement.auth.enums.OtpTypeEnum;
import org.boiar.walletmanagement.user.entity.User;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "otps")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Otp {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private String code;

  @Enumerated(EnumType.STRING)
  @Column(name = "otp_type", nullable = false)
  private OtpTypeEnum type;

  @Column(nullable = false)
  private LocalDateTime expiresAt;

  private boolean used;

  @CreatedDate
  @Column(name = "created_date", updatable = false)
  private LocalDateTime createdDate;

  public boolean isExpired() {
    return LocalDateTime.now().isAfter(expiresAt);
  }
}
