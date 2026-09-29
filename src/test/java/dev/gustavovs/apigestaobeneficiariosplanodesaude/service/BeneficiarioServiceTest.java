package dev.gustavovs.apigestaobeneficiariosplanodesaude.service;

import dev.gustavovs.apigestaobeneficiariosplanodesaude.dto.request.beneficiario.SaveBeneficiarioRequestDto;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.dto.request.beneficiario.UpdateBeneficiarioRequestDto;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.dto.request.documento.SaveDocumentoRequestDto;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.dto.response.beneficiario.BeneficiarioResponseDto;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.dto.response.documento.DocumentoResponseDto;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.entity.Beneficiario;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.entity.Documento;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.entity.TipoDocumento;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.exception.beneficiario.BeneficiarioNaoEncontradoException;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.exception.documentos.NumeroInvalidoDeDocumentosException;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.repository.BeneficiarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BeneficiarioServiceTest {


    @Mock
    private BeneficiarioRepository beneficiarioRepository;


    @InjectMocks
    private BeneficiarioService beneficiarioService;

    @Test
    @DisplayName("Deve retornar um beneficiario corretamente.")
    public void save_ShouldReturnValidBeneficiario_WhenSuccessful() {
        Beneficiario beneficiario = Beneficiario.BeneficiarioBuilder.builder()
                .nome("Teste")
                .telefone("0123456789")
                .dataNascimento(LocalDate.of(2026, 1, 1))
                .documentos(Set.of(new Documento(TipoDocumento.valueOf("CPF"), "descricao documento")))
                .build();

        beneficiario.setId(1L);

        Mockito.when(beneficiarioRepository.save(Mockito.any(Beneficiario.class)))
                .thenReturn(beneficiario);

        SaveDocumentoRequestDto docRequestDto = new SaveDocumentoRequestDto("CPF", "descricao documento");
        SaveBeneficiarioRequestDto beneficiarioToSave = new SaveBeneficiarioRequestDto("Teste", "0123456789", LocalDate.of(2026, 1, 1), Set.of(docRequestDto));

        BeneficiarioResponseDto saved = beneficiarioService.save(beneficiarioToSave);

        assertNotNull(saved);
        assertNotNull(saved.id());
        assertEquals(saved.id(), 1L);
        assertEquals(saved.nome(), beneficiarioToSave.nome());
        assertEquals(saved.telefone(), beneficiarioToSave.telefone());

    }

    @Test
    @DisplayName("Deve lançar um NumeroInvalidoDeDocumentosException quando nenhum documento for enviado na requisiçao.")
    public void save_ShouldThrowNumeroInvalidoDeDocumentosException_WhenDocumentosIsEmpty() {
        SaveBeneficiarioRequestDto beneficiarioToSave = new SaveBeneficiarioRequestDto("Teste", "0123456789", LocalDate.of(2026, 1, 1), Set.of());

        assertThrows(NumeroInvalidoDeDocumentosException.class, () -> beneficiarioService.save(beneficiarioToSave));
    }

    @Test
    @DisplayName("Deve retornar uma lista preenchida de beneficiarios.")
    public void findAll_ShouldReturnBeneficiarioList_WhenSuccessful() {
        Beneficiario beneficiario = Beneficiario.BeneficiarioBuilder.builder()
                .nome("Teste")
                .telefone("0123456789")
                .dataNascimento(LocalDate.of(2026, 1, 1))
                .documentos(Set.of(new Documento(TipoDocumento.valueOf("CPF"), "descricao documento")))
                .build();

        beneficiario.setId(1L);

        Mockito.when(beneficiarioRepository.findAll())
                .thenReturn(List.of(beneficiario));

        List<BeneficiarioResponseDto> listBeneficiarios = beneficiarioService.findAll();

        assertNotNull(listBeneficiarios);
        assertEquals(1, listBeneficiarios.size());
        assertEquals(beneficiario.getId(), listBeneficiarios.getFirst().id());
        assertEquals(beneficiario.getNome(), listBeneficiarios.getFirst().nome());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando nenhum beneficiario estiver cadastrado.")
    public void findAll_ShouldReturnEmptyList_WhenNoBeneficiariosIsRegistered() {
        Mockito.when(beneficiarioRepository.findAll())
                .thenReturn(List.of());

        List<BeneficiarioResponseDto> listBeneficiarios = beneficiarioService.findAll();

        assertNotNull(listBeneficiarios);
        assertEquals(0, listBeneficiarios.size());
    }

    @Test
    @DisplayName("Deve retornar com sucesso um beneficiario cujo ID = 1")
    public void findBeneficiarioById_ShouldReturnValidBeneficiario_WhenSuccessful() {
        Beneficiario beneficiario = Beneficiario.BeneficiarioBuilder.builder()
                .nome("Teste")
                .telefone("0123456789")
                .dataNascimento(LocalDate.of(2026, 1, 1))
                .documentos(Set.of(new Documento(TipoDocumento.valueOf("CPF"), "descricao documento")))
                .build();

        beneficiario.setId(1L);

        Mockito.when(beneficiarioRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(beneficiario));

        BeneficiarioResponseDto beneficiarioById = beneficiarioService.findBeneficiarioById(1L);

        assertNotNull(beneficiarioById);
        assertEquals(beneficiario.getId(), beneficiarioById.id());
        assertEquals(beneficiario.getNome(), beneficiarioById.nome());
        assertEquals(beneficiario.getTelefone(), beneficiarioById.telefone());
    }

    @Test
    @DisplayName("Deve lançar um BeneficiarioNaoEncontradoException quando um beneficiario nao for encontrado pelo ID.")
    public void findBeneficiarioById_ThrowBeneficiarioNaoEncontradoException_WhenBeneficiarioIsNotFound() {
        Mockito.when(beneficiarioRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.empty());

        assertThrowsExactly(BeneficiarioNaoEncontradoException.class, () -> beneficiarioService.findBeneficiarioById(1L));
    }

    @Test
    @DisplayName("Deve retornar uma lista de documentos do beneficiario informado.")
    public void findAllDocs_ShouldReturnAllDocsOfaBeneficiario_WhenSuccessful() {
        Beneficiario beneficiario = Beneficiario.BeneficiarioBuilder.builder()
                .nome("Teste")
                .telefone("0123456789")
                .dataNascimento(LocalDate.of(2026, 1, 1))
                .documentos(Set.of(new Documento(TipoDocumento.valueOf("CPF"), "descricao documento")))
                .build();

        beneficiario.setId(1L);

        Mockito.when(beneficiarioRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(beneficiario));

        List<DocumentoResponseDto> allDocs = beneficiarioService.findAllDocs(1L);

        assertNotNull(allDocs);
        assertEquals(1, allDocs.size());
        assertEquals("CPF", allDocs.getFirst().tipoDocumento());
        assertEquals("descricao documento", allDocs.getFirst().descricao());
    }

    @Test
    @DisplayName("Deve retornar um beneficiario atualizado com sucesso")
    public void update_ShouldReturnAnUpdatedBeneficiario_WhenSuccessful() {
        Beneficiario beneficiarioBeforeUpdate = Beneficiario.BeneficiarioBuilder.builder()
                .nome("Teste")
                .telefone("0123456789")
                .dataNascimento(LocalDate.of(2026, 1, 1))
                .documentos(Set.of(new Documento(TipoDocumento.valueOf("CPF"), "descricao documento")))
                .build();
        beneficiarioBeforeUpdate.setId(1L);

        Beneficiario beneficiarioToUpdate = Beneficiario.BeneficiarioBuilder.builder()
                .nome("Teste")
                .telefone("0123456789")
                .dataNascimento(LocalDate.of(2026, 1, 1))
                .documentos(Set.of(new Documento(TipoDocumento.valueOf("CPF"), "descricao documento")))
                .build();
        beneficiarioToUpdate.setId(1L);

        Beneficiario beneficiarioUpdated = Beneficiario.BeneficiarioBuilder.builder()
                .nome("Updated")
                .telefone("9876543210")
                .dataNascimento(LocalDate.of(2005, 2, 2))
                .documentos(Set.of(new Documento(TipoDocumento.valueOf("CPF"), "descricao documento")))
                .build();
        beneficiarioUpdated.setId(1L);

        Mockito.when(beneficiarioRepository.findById(Mockito.any(Long.class)))
                        .thenReturn(Optional.of(beneficiarioToUpdate));

        Mockito.when(beneficiarioRepository.save(Mockito.any(Beneficiario.class)))
                .thenReturn(beneficiarioUpdated);


        UpdateBeneficiarioRequestDto request = new UpdateBeneficiarioRequestDto("Updated", "9876543210", LocalDate.of(2005, 2, 2), Set.of());
        BeneficiarioResponseDto response = beneficiarioService.update(1L, request);

        assertNotNull(response);
        assertEquals(response.id(), beneficiarioBeforeUpdate.getId());
        assertNotEquals(response.nome(), beneficiarioBeforeUpdate.getNome());
        assertNotEquals(response.telefone(), beneficiarioBeforeUpdate.getTelefone());
    }

    @Test
    @DisplayName("")
    public void delete_ShouldRemoveAnExistingBeneficiario_WhenSuccesssful() {
        Beneficiario beneficiario = Beneficiario.BeneficiarioBuilder.builder()
                .nome("Teste")
                .telefone("0123456789")
                .dataNascimento(LocalDate.of(2026, 1, 1))
                .documentos(Set.of(new Documento(TipoDocumento.valueOf("CPF"), "descricao documento")))
                .build();

        beneficiario.setId(1L);

        Mockito.when(beneficiarioRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(beneficiario));

        assertDoesNotThrow(() -> beneficiarioService.delete(1L));
    }
}