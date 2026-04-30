package org.boiar.walletmanagement.auth.repository.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.boiar.walletmanagement.auth.entity.Otp;
import org.boiar.walletmanagement.auth.enums.OtpTypeEnum;
import org.boiar.walletmanagement.auth.repository.OtpRepository;
import org.boiar.walletmanagement.auth.repository.jpa.OtpRepositoryJpa;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OtpRepositoryImpl implements OtpRepository {

  private final OtpRepositoryJpa otpRepositoryJpa;

  @Override
  public Optional<Otp> findTopByUserEmailAndTypeAndUsedFalseOrderByCreatedAtDesc(
      String email, OtpTypeEnum type) {
    return otpRepositoryJpa.findTopByUserEmailAndTypeAndUsedFalseOrderByCreatedAtDesc(email, type);
  }

  @Override
  public void deleteAllByUserEmailAndType(String email, OtpTypeEnum type) {
    otpRepositoryJpa.deleteAllByUserEmailAndType(email, type);
  }

  @Override
  public void save(Otp otp) {
    otpRepositoryJpa.save(otp);
  }
}
