package dev.gustavovs.apigestaobeneficiariosplanodesaude.dto.request.documento;

import jakarta.validation.constraints.NotEmpty;

public record SaveDocumentoRequestDto(
        @NotEmpty(message = "O tipo de documento deve ser informado.")
        String tipoDocumento,
        String descricao) {
}
