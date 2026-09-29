package dev.gustavovs.apigestaobeneficiariosplanodesaude.exception.documentos;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NumeroInvalidoDeDocumentosException extends RuntimeException {
    public NumeroInvalidoDeDocumentosException(String message) {
        super(message);
    }
}
