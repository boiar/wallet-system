package org.boiar.walletmanagement.auth.repository;

import java.util.Optional;
import java.util.UUID;
import org.boiar.walletmanagement.auth.entity.RefreshToken;

public interface RefreshTokenRepository {

  Optional<RefreshToken> findByToken(String token);

  void revokeAllByUserId(UUID userId);

  void save(RefreshToken refreshToken);
}
