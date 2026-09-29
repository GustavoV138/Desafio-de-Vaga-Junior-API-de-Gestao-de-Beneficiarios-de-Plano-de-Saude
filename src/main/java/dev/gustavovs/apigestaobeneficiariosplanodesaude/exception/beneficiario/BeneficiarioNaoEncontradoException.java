package dev.gustavovs.apigestaobeneficiariosplanodesaude.exception.beneficiario;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BeneficiarioNaoEncontradoException extends RuntimeException {
    public BeneficiarioNaoEncontradoException(String message) {
        super(message);
    }
}
