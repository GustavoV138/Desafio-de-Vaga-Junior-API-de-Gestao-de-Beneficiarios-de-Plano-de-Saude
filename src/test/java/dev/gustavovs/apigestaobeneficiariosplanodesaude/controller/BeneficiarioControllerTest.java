package dev.gustavovs.apigestaobeneficiariosplanodesaude.controller;

import dev.gustavovs.apigestaobeneficiariosplanodesaude.dto.request.beneficiario.SaveBeneficiarioRequestDto;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.dto.request.beneficiario.UpdateBeneficiarioRequestDto;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.dto.request.documento.SaveDocumentoRequestDto;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.dto.response.beneficiario.BeneficiarioResponseDto;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.dto.response.documento.DocumentoResponseDto;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.service.BeneficiarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BeneficiarioControllerTest {

    @Mock
    private BeneficiarioService beneficiarioService;

    @InjectMocks
    private BeneficiarioController beneficiarioController;

    @Test
    @DisplayName("Deve persistir e retornar um BeneficiarioDto em um ResponseEntity com o Status Http CREATED.")
    public void save_ShouldPersistAndReturnBeneficiarioDtoWithHttpStatusCREATED_WhenSuccessful() {
        BeneficiarioResponseDto response = new BeneficiarioResponseDto(1L, "Teste", "1234567890");

        Mockito.when(beneficiarioService.save(Mockito.any(SaveBeneficiarioRequestDto.class)))
                .thenReturn(response);

        SaveBeneficiarioRequestDto request = new SaveBeneficiarioRequestDto("Teste", "1234567890", LocalDate.of(2001, 1, 1), Set.of(new SaveDocumentoRequestDto("CPF", "desc")));

        ResponseEntity<BeneficiarioResponseDto> responseEntity = beneficiarioController.save(request);

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody().id());
        assertEquals(request.nome(), responseEntity.getBody().nome());
        assertEquals(request.telefone(), responseEntity.getBody().telefone());
    }

    @Test
    @DisplayName("Deve retornar uma lista de Beneficiarios populada com sucesso.")
    public void findAll_ShouldReturnBeneficiarioResponseDtoListPopulated_WhenSuccessful() {
        BeneficiarioResponseDto response = new BeneficiarioResponseDto(1L, "Teste", "1234567890");

        Mockito.when(beneficiarioService.findAll())
                .thenReturn(List.of(response));

        ResponseEntity<List<BeneficiarioResponseDto>> responseEntity = beneficiarioController.findAll();

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals(response, responseEntity.getBody().getFirst());
    }

    @Test
    @DisplayName("Deve retornar um ResponseEntity com StatusCode OK e Body com uma Lista vazia.")
    public void findAll_ShouldReturnResponseEntityWithEmptyList_WhenNoBenefiariosIsFound() {
        Mockito.when(beneficiarioService.findAll())
                .thenReturn(List.of());

        ResponseEntity<List<BeneficiarioResponseDto>> responseEntity = beneficiarioController.findAll();

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, responseEntity.getBody().size());
    }

    @Test
    @DisplayName("Deve retornar um ResponseEntity com o Beneficiario quando for encontrado.")
    public void findById_ShouldReturnResponseEntityWithBeneficario_WhenSuccessful() {
        BeneficiarioResponseDto response = new BeneficiarioResponseDto(1L, "Teste", "1234567890");

        Mockito.when(beneficiarioService.findBeneficiarioById(Mockito.any(Long.class)))
                .thenReturn(response);

        ResponseEntity<BeneficiarioResponseDto> responseEntity = beneficiarioController.findById(1L);

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody().id());
        assertEquals("Teste", responseEntity.getBody().nome());
        assertEquals("1234567890", responseEntity.getBody().telefone());
    }

    @Test
    @DisplayName("Deve retornar um ResponseEntity com uma lista de documentos de um determinado beneficiario.")
    public void findALlDocs_ShouldReturnDocumentosListOfABeneficiario_WhenSuccessful() {
        DocumentoResponseDto response = new DocumentoResponseDto("CPF", "descricao CPF");

        Mockito.when(beneficiarioService.findAllDocs(Mockito.any(Long.class)))
                .thenReturn(List.of(response));

        ResponseEntity<List<DocumentoResponseDto>> responseEntity = beneficiarioController.findAllDocs(1L);

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals("CPF", responseEntity.getBody().getFirst().tipoDocumento());
        assertEquals("descricao CPF", responseEntity.getBody().getFirst().descricao());
    }

    @Test
    @DisplayName("Deve atualizar um Beneficiario e retornar um ResponseEntity com status CREATED com sucesso.")
    public void update_ShouldReturnAnUpdatedBeneficiario_WhenSuccessful() {
        BeneficiarioResponseDto response = new BeneficiarioResponseDto(1L, "Teste atualizado", "9876543210");

        Mockito.when(beneficiarioService.update(Mockito.anyLong(),Mockito.any(UpdateBeneficiarioRequestDto.class)))
                .thenReturn(response);

        BeneficiarioResponseDto beneficiarioToCompare = new BeneficiarioResponseDto(1L, "Teste", "1234567890");

        UpdateBeneficiarioRequestDto request = new UpdateBeneficiarioRequestDto("Teste atualizado", "9876543210", LocalDate.of(2010, 1, 1), Set.of());

        ResponseEntity<BeneficiarioResponseDto> responseEntity = beneficiarioController.update(1L, request);

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(beneficiarioToCompare.id(), responseEntity.getBody().id());
        assertNotEquals(beneficiarioToCompare.nome(), responseEntity.getBody().nome());
        assertNotEquals(beneficiarioToCompare.telefone(), responseEntity.getBody().telefone());
    }

    @Test
    @DisplayName("Deve retornar um ResponseEntity com Body null e status code NO_CONTENT com sucesso.")
    public void delete_ShouldReturnResponsesEntityWithStatusCodeNOCONTENT_WhenSuccessful() {
        Mockito.doNothing().when(beneficiarioService).delete(Mockito.anyLong());

        ResponseEntity<Void> responseEntity = beneficiarioController.delete(1L);

        assertNotNull(responseEntity);
        assertNull(responseEntity.getBody());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}