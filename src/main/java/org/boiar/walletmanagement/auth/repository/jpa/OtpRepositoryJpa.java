package org.boiar.walletmanagement.auth.repository.jpa;

import java.util.Optional;
import org.boiar.walletmanagement.auth.entity.Otp;
import org.boiar.walletmanagement.auth.enums.OtpTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface OtpRepositoryJpa extends JpaRepository<Otp, Long> {

  Optional<Otp> findTopByUserEmailAndTypeAndUsedFalseOrderByCreatedDateDesc(
      String email, OtpTypeEnum type);

  @Modifying
  @Transactional
  @Query("DELETE FROM Otp o WHERE o.user.email = :email AND o.type = :type")
  void deleteAllByUserEmailAndType(@Param("email") String email, @Param("type") OtpTypeEnum type);
}
