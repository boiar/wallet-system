package org.boiar.walletmanagement.core.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
  private boolean success;
  private String message;
  private T data;

  public static <T> ApiResponse<T> success(String message, T data) {

    T responseData = data;

    if (responseData == null) {
      responseData = (T) Collections.emptyList();
    }

    return ApiResponse.<T>builder()
            .success(true)
            .message(message)
            .data(responseData)
            .build();
  }

  public static ApiResponse<Object> success(String message) {
    return ApiResponse.builder()
            .success(true)
            .message(message)
            .data(Collections.emptyList())
            .build();
  }

  public static <T> ApiResponse<T> error(String message, T data) {
    return ApiResponse.<T>builder().success(false).message(message).data(data).build();
  }

  public static ApiResponse<Object> error(String message) {
    return ApiResponse.builder()
            .success(false)
            .message(message)
            .data(Collections.emptyList())
            .build();
  }

}
