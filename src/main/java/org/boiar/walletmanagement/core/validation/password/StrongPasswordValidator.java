package org.boiar.walletmanagement.core.validation.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {
  @Override
  public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
    if (password == null) return false;
    boolean hasUpper = password.matches(".*[A-Z].*");
    boolean hasLower = password.matches(".*[a-z].*");
    boolean hasNumber = password.matches(".*\\d.*");
    boolean hasSpecial = password.matches(".*[@$!%*?&].*");
    boolean hasMinLength = password.length() >= 8;

    return hasUpper && hasLower && hasNumber && hasSpecial && hasMinLength;
  }
}
