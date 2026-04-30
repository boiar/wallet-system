package org.boiar.walletmanagement.auth.repository.jpa;

import java.util.Optional;
import org.boiar.walletmanagement.auth.entity.Otp;
import org.boiar.walletmanagement.auth.enums.OtpTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepositoryJpa extends JpaRepository<Otp, Long> {

  Optional<Otp> findTopByUserEmailAndTypeAndUsedFalseOrderByCreatedAtDesc(
      String email, OtpTypeEnum type);

  void deleteAllByUserEmailAndType(String email, OtpTypeEnum type);
}
