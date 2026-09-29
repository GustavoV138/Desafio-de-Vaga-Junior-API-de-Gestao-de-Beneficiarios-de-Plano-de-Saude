package dev.gustavovs.apigestaobeneficiariosplanodesaude.exception;

import dev.gustavovs.apigestaobeneficiariosplanodesaude.exception.beneficiario.BeneficiarioNaoEncontradoException;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.exception.documentos.NumeroInvalidoDeDocumentosException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BeneficiarioNaoEncontradoException.class)
    public ResponseEntity<ProblemDetail> handleBeneficiarioNaoEncontradoException(BeneficiarioNaoEncontradoException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Beneficiário não encontrado.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(NumeroInvalidoDeDocumentosException.class)
    public ResponseEntity<ProblemDetail> handleNumeroInvalidoDeDocumentosException(NumeroInvalidoDeDocumentosException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Número inválido no envio de documentos.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumetNotValidException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : fieldErrors) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Problemas no preenchimento em 1 ou mais campos.");
        problemDetail.setProperty("Campos inválidos", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

}
