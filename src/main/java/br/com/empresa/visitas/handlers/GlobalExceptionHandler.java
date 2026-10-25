package br.com.empresa.visitas.handlers;

import br.com.empresa.visitas.dtos.ApiError;
import br.com.empresa.visitas.infrastructure.ClientIdConflictException;
import br.com.empresa.visitas.infrastructure.VisitNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(VisitNotFoundException.class)
    public ResponseEntity<ApiError> notFound(VisitNotFoundException ex, HttpServletRequest req) {
        return error(HttpStatus.NOT_FOUND, ex.getMessage(), req, Map.of());
    }
    @ExceptionHandler({ClientIdConflictException.class, DataIntegrityViolationException.class})
    public ResponseEntity<ApiError> conflict(Exception ex, HttpServletRequest req) {
        String message = ex instanceof ClientIdConflictException ? ex.getMessage() : "Conflito de dados. Verifique se o clientId já foi utilizado.";
        return error(HttpStatus.CONFLICT, message, req, Map.of());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> validation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        Map<String, String> fields = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> fields.putIfAbsent(e.getField(), e.getDefaultMessage()));
        return error(HttpStatus.BAD_REQUEST, "Existem campos inválidos.", req, fields);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> unreadable(HttpMessageNotReadableException ex, HttpServletRequest req) {
        return error(HttpStatus.BAD_REQUEST, "JSON inválido ou formato de data incorreto.", req, Map.of());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> unexpected(Exception ex, HttpServletRequest req) {
        ex.printStackTrace();
        return error(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno ao processar a solicitação.", req, Map.of());
    }
    private ResponseEntity<ApiError> error(HttpStatus status, String message, HttpServletRequest req, Map<String, String> fields) {
        ApiError body = new ApiError(LocalDateTime.now(), status.value(), status.getReasonPhrase(), message, req.getRequestURI(), fields);
        return ResponseEntity.status(status).body(body);
    }
}
