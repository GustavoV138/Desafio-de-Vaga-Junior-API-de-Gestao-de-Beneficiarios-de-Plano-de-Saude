package dev.gustavovs.apigestaobeneficiariosplanodesaude.dto.request.beneficiario;

import dev.gustavovs.apigestaobeneficiariosplanodesaude.dto.request.documento.SaveDocumentoRequestDto;

import java.time.LocalDate;
import java.util.Set;

public record UpdateBeneficiarioRequestDto(String nome, String telefone, LocalDate dataNascimento, Set<SaveDocumentoRequestDto> documentos)  {
}
