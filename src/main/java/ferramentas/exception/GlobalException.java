package ferramentas.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import ferramentas.response.FieldErrorDetail;
import ferramentas.response.ResponseApi;
import ferramentas.response.ResponseBuilder;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ResponseApi<Void>> handleHandlerMethodValidation(HandlerMethodValidationException ex, HttpServletRequest request) {

        log.warn("Erro de validação de parâmetros | path={} | message={}", request.getRequestURI(), ex.getMessage());
        return ResponseBuilder.error("Parâmetro inválido na requisição", HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseApi<Void>> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {

        log.warn("Entidade não encontrada | path={}", request.getRequestURI());
        return ResponseBuilder.error("Recurso não encontrado", HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseApi<Void>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<FieldErrorDetail> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> new FieldErrorDetail(
                        e.getField(),
                        e.getDefaultMessage(),
                        e.getRejectedValue()))
                .toList();

        log.warn("Erro de validação | path={} | fields={}", request.getRequestURI(),
                errors.stream().map(FieldErrorDetail::getField).toList());

        return ResponseBuilder.error("Erro de validação nos campos", errors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseApi<Void>> handleBind(BindException ex, HttpServletRequest request) {

        List<FieldErrorDetail> errors = ex.getFieldErrors()
                .stream()
                .map(e -> new FieldErrorDetail(
                        e.getField(),
                        e.getDefaultMessage(),
                        e.getRejectedValue()))
                .toList();

        log.warn("Erro de bind | path={} | fields={}", request.getRequestURI(),
                errors.stream().map(FieldErrorDetail::getField).toList());

        return ResponseBuilder.error("Erro de validação nos campos", errors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseApi<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {

        log.warn("JSON malformado | path={} | message={}", request.getRequestURI(), ex.getMessage());

        return ResponseBuilder.error("Requisição JSON malformada ou inválida", HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseApi<Void>> handleGeneric(Exception ex, HttpServletRequest request) {

        log.error("Erro interno | path={} | error={}", request.getRequestURI(), ex.getMessage(), ex);

        return ResponseBuilder.error("Erro interno no servidor", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseApi<Void>> handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request) {

        log.warn("Violação de integridade | path={} | message={}", request.getRequestURI(), ex.getMessage());
        return ResponseBuilder.error("Dados já cadastrados ou violação de integridade", HttpStatus.CONFLICT);
    }



    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseApi<Void>> UsernameNotFoundException(UsernameNotFoundException ex, HttpServletRequest request) {

        log.warn("usuario não encontrado | path={} | message={}", request.getRequestURI(), ex.getMessage());
        return  ResponseBuilder.error(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseApi<Void>> BadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {

        log.warn("Usuario inexistente ou senha invalida | path={} | message={}", request.getRequestURI(), ex.getMessage());
        return  ResponseBuilder.error(ex.getMessage(),HttpStatus.NOT_FOUND);
    }




}
