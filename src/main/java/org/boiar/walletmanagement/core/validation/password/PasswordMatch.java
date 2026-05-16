package org.boiar.walletmanagement.core.validation.password;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/*
* Validates that password and confirm password fields match.
*
* */


@Documented
@Constraint(validatedBy = PasswordMatchValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatch {
  String message() default "error.password.mismatch";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
