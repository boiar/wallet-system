package org.boiar.walletmanagement.auth.repository;

import java.util.Optional;
import org.boiar.walletmanagement.auth.entity.Otp;
import org.boiar.walletmanagement.auth.enums.OtpTypeEnum;

public interface OtpRepository {
  Optional<Otp> findTopByUserEmailAndTypeAndUsedFalseOrderByCreatedDateDesc(
      String email, OtpTypeEnum type);

  void deleteAllByUserEmailAndType(String email, OtpTypeEnum type);

  void save(Otp otp);
}
