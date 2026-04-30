package org.boiar.walletmanagement.core.handler;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.boiar.walletmanagement.core.exception.BaseException;
import org.boiar.walletmanagement.core.lang.LocaleHelper;
import org.boiar.walletmanagement.core.response.ErrorResponse;
import org.boiar.walletmanagement.core.response.FieldError;
import org.springframework.http.HttpStatus;
// import org.boiar.walletmanagement.multitenancy.TenantSchemaService.SchemaProvisioningException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
  private final LocaleHelper localeHelper;

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ErrorResponse> handleBase(BaseException ex, HttpServletRequest req) {
    String message = localeHelper.get(ex.getErrorCode().getMessageKey(), ex.getArgs());

    return ResponseEntity.status(ex.getHttpStatus()).body(ErrorResponse.of(ex, message, req));
  }

  // 400 Validation → localised field errors
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidation(
      MethodArgumentNotValidException ex, HttpServletRequest request) {

    List<FieldError> fieldErrors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(
                fe ->
                    FieldError.builder()
                        .field(fe.getField())
                        .message(resolveFieldMessage(fe)) // ✅ localised per field
                        .build())
            .toList();

    return ResponseEntity.badRequest()
        .body(
            ErrorResponse.ofValidation(localeHelper.get("error.validation"), fieldErrors, request));
  }

  //  ---------- Exception 500 ----------

  // 500 Schema provisioning
  /*  @ExceptionHandler(SchemaProvi)
   */

  // 500 fallback
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneral(Exception ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            ErrorResponse.builder()
                .code("ERR_INTERNAL")
                .message(localeHelper.get("error.internal"))
                .status(500)
                .path(request.getRequestURI())
                .timestamp(Instant.now())
                .build());
  }

  private String resolveFieldMessage(org.springframework.validation.FieldError fe) {
    try {
      // fe.getDefaultMessage() =  "error.field.required"
      return localeHelper.get(fe.getDefaultMessage(), fe.getArguments());
    } catch (Exception e) {
      return fe.getDefaultMessage();
    }
  }
}
