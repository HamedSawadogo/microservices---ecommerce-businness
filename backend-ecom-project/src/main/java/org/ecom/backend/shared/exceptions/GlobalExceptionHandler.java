package org.ecom.backend.shared.exceptions;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BussinessException.class)
    public ResponseEntity<ErrorResponse> onBusinessException(BussinessException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.builder()
                        .description(e.getMessage())
                        .message("Une erreur business est survenue.")
                        .code(HttpStatus.BAD_REQUEST.value())
                        .occurredAt(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> onBusinessException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ErrorResponse.builder()
                        .message("Une erreur Interne au serveur est survenue.")
                        .description(e.getMessage())
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .occurredAt(LocalDateTime.now())
                        .build()
        );
    }
}

@Builder
record ErrorResponse(String message,
                     String description,
                     int code,
                     LocalDateTime occurredAt) {
}