package org.boiar.walletmanagement.core.security.service;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public interface JwtService {
  String generateToken(UUID userId);

  String generateToken(Map<String, Object> extraClaims, UUID userId);

  UUID extractUserId(String token);

  boolean isTokenValid(String token, UUID userId);

  boolean isTokenExpired(String token);

  Date extractExpiration(String token);
}
