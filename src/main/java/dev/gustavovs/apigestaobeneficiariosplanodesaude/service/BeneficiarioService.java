package dev.gustavovs.apigestaobeneficiariosplanodesaude.service;

import dev.gustavovs.apigestaobeneficiariosplanodesaude.dto.request.beneficiario.SaveBeneficiarioRequestDto;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.dto.request.beneficiario.UpdateBeneficiarioRequestDto;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.dto.response.beneficiario.BeneficiarioResponseDto;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.dto.response.documento.DocumentoResponseDto;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.entity.Beneficiario;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.entity.Documento;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.entity.TipoDocumento;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.exception.beneficiario.BeneficiarioNaoEncontradoException;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.exception.documentos.NumeroInvalidoDeDocumentosException;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.repository.BeneficiarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BeneficiarioService {

    private final BeneficiarioRepository beneficiarioRepository;

    public BeneficiarioService(BeneficiarioRepository beneficiarioRepository) {
        this.beneficiarioRepository = beneficiarioRepository;
    }

    public BeneficiarioResponseDto save(SaveBeneficiarioRequestDto request) {

        if(request.documentos().isEmpty()) throw new NumeroInvalidoDeDocumentosException("Ao menos 1 documento deve ser informado.");

        Set<Documento> documentosToSave = request.documentos().stream()
                .map(d -> new Documento(TipoDocumento.valueOf(d.tipoDocumento()), d.descricao()))
                .collect(Collectors.toSet());

        Beneficiario beneficiario = Beneficiario.BeneficiarioBuilder.builder()
                .nome(request.nome())
                .telefone(request.telefone())
                .dataNascimento(request.dataNascimento())
                .documentos(documentosToSave)
                .build();

        Beneficiario saved = beneficiarioRepository.save(beneficiario);

        return new BeneficiarioResponseDto(saved.getId(), saved.getNome(), saved.getTelefone());

    }

    public List<BeneficiarioResponseDto> findAll () {

        return beneficiarioRepository.findAll().stream()
                .map(b -> new BeneficiarioResponseDto(b.getId(), b.getNome(), b.getTelefone()))
                .toList();
    }

    public BeneficiarioResponseDto findBeneficiarioById(Long id) {
        Beneficiario beneficiario = findBeneficiarioInternal(id);
        return new BeneficiarioResponseDto(beneficiario.getId(), beneficiario.getNome(), beneficiario.getTelefone());
    }

    public List<DocumentoResponseDto> findAllDocs (Long beneficiarioId) {

        Beneficiario beneficiario = findBeneficiarioInternal(beneficiarioId);

        return beneficiario.getDocumentos().stream()
                .map(doc -> new DocumentoResponseDto(doc.getTipoDocumento().getDocumento(), doc.getDescricao()))
                .toList();
    }

    @Transactional
    public BeneficiarioResponseDto update(Long id, UpdateBeneficiarioRequestDto request) {

        Beneficiario beneficiario = findBeneficiarioInternal(id);

        beneficiario.setNome(request.nome());
        beneficiario.setTelefone(request.telefone());
        beneficiario.setDataNascimento(request.dataNascimento());

        if(!request.documentos().isEmpty()) {
            Set<Documento> documentos = request.documentos().stream()
                    .map(doc -> new Documento(TipoDocumento.valueOf(doc.tipoDocumento()), doc.descricao()))
                    .collect(Collectors.toSet());

            beneficiario.getDocumentos().addAll(documentos);
        }

        Beneficiario saved = beneficiarioRepository.save(beneficiario);

        return new BeneficiarioResponseDto(saved.getId(), saved.getNome(), saved.getTelefone());
    }

    public void delete(Long id) {

        Beneficiario beneficiario = findBeneficiarioInternal(id);
        beneficiarioRepository.delete(beneficiario);
    }

    private Beneficiario findBeneficiarioInternal(Long id) {
        Optional<Beneficiario> byId = beneficiarioRepository.findById(id);
        if(byId.isEmpty()) throw new BeneficiarioNaoEncontradoException(String.format("Id: '%d' não encontrado.", id));

        return byId.get();
    }

}
