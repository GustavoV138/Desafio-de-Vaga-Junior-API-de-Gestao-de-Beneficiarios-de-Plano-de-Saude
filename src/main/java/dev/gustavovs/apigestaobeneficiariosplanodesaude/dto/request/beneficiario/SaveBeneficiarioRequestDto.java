package dev.gustavovs.apigestaobeneficiariosplanodesaude.dto.request.beneficiario;

import dev.gustavovs.apigestaobeneficiariosplanodesaude.dto.request.documento.SaveDocumentoRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.Set;

public record SaveBeneficiarioRequestDto(
        @NotEmpty(message = "'Nome' deve ser informado.")
        String nome,
        @NotEmpty(message = "'Telefone' deve ser informado.")
        String telefone,
        LocalDate dataNascimento,
        @NotEmpty(message = "Ao menos 1 documento deve ser informado.")
        @Valid
        Set<SaveDocumentoRequestDto> documentos) {
}
