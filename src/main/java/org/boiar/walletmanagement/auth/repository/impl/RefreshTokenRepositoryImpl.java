package org.boiar.walletmanagement.auth.repository.impl;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.boiar.walletmanagement.auth.entity.RefreshToken;
import org.boiar.walletmanagement.auth.repository.RefreshTokenRepository;
import org.boiar.walletmanagement.auth.repository.jpa.RefreshTokenRepositoryJpa;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

  private final RefreshTokenRepositoryJpa refreshTokenRepositoryJpa;

  @Override
  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepositoryJpa.findByToken(token);
  }

  @Override
  public void revokeAllByUserId(UUID userId) {
    refreshTokenRepositoryJpa.revokeAllByUserId(userId);
  }

  @Override
  public void save(RefreshToken refreshToken) {
    refreshTokenRepositoryJpa.save(refreshToken);
  }
}
