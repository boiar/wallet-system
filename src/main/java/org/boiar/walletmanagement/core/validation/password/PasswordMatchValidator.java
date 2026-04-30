package org.boiar.walletmanagement.core.validation.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    try {
      Field passwordField = value.getClass().getDeclaredField("password");
      Field confirmField = value.getClass().getDeclaredField("confirmPassword");

      passwordField.setAccessible(true);
      confirmField.setAccessible(true);

      Object password = passwordField.get(value);
      Object confirm = confirmField.get(value);

      if (password == null || confirm == null) return false;

      boolean matches = password.equals(confirm);

      if (!matches) {
        context.disableDefaultConstraintViolation();
        context
            .buildConstraintViolationWithTemplate("error.password.mismatch")
            .addPropertyNode("confirmPassword")
            .addConstraintViolation();
      }

      return matches;

    } catch (Exception e) {
      return false;
    }
  }
}
