package org.boiar.walletmanagement.core.response;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;
import lombok.*;
import org.boiar.walletmanagement.core.exception.BaseException;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
  private String code;
  private String message;
  private int status;
  private String path;
  private Instant timestamp;
  private List<FieldError> fieldErrors;

  // For BaseException (no field errors)
  public static ErrorResponse of(BaseException ex, String message, HttpServletRequest request) {
    return ErrorResponse.builder()
        .code(ex.getCode())
        .message(message)
        .status(ex.getHttpStatus().value())
        .path(request.getRequestURI())
        .timestamp(Instant.now())
        .fieldErrors(null)
        .build();
  }

  // For MethodArgumentNotValidException with field errors
  public static ErrorResponse ofValidation(
      String message, List<FieldError> fieldErrors, HttpServletRequest request) {
    return ErrorResponse.builder()
        .code("error.validation")
        .message(message)
        .status(400)
        .path(request.getRequestURI())
        .timestamp(Instant.now())
        .fieldErrors(fieldErrors)
        .build();
  }
}
