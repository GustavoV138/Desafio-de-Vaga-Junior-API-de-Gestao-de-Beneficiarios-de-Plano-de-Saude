package dev.gustavovs.apigestaobeneficiariosplanodesaude.controller;

import dev.gustavovs.apigestaobeneficiariosplanodesaude.dto.request.beneficiario.SaveBeneficiarioRequestDto;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.dto.request.beneficiario.UpdateBeneficiarioRequestDto;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.dto.response.beneficiario.BeneficiarioResponseDto;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.dto.response.documento.DocumentoResponseDto;
import dev.gustavovs.apigestaobeneficiariosplanodesaude.service.BeneficiarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("beneficiario/")
public class BeneficiarioController {

    private final BeneficiarioService beneficiarioService;

    public BeneficiarioController(BeneficiarioService beneficiarioService) {
        this.beneficiarioService = beneficiarioService;
    }

    @PostMapping
    public ResponseEntity<BeneficiarioResponseDto> save(@Valid @RequestBody SaveBeneficiarioRequestDto request) {
        BeneficiarioResponseDto saved = beneficiarioService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<BeneficiarioResponseDto>> findAll() {
        return ResponseEntity.ok(beneficiarioService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<BeneficiarioResponseDto> findById(@RequestParam Long id) {
        return ResponseEntity.ok(beneficiarioService.findBeneficiarioById(id));
    }

    @GetMapping("{beneficiarioId}/docs")
    public ResponseEntity<List<DocumentoResponseDto>> findAllDocs(@RequestParam Long beneficiarioId) {
        return ResponseEntity.ok(beneficiarioService.findAllDocs(beneficiarioId));
    }

    @PatchMapping("{beneficiarioId}")
    public ResponseEntity<BeneficiarioResponseDto> update(@RequestParam Long beneficiarioId, @RequestBody UpdateBeneficiarioRequestDto request) {
        BeneficiarioResponseDto updated = beneficiarioService.update(beneficiarioId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(updated);
    }

    @DeleteMapping("{beneficiarioId}")
    public ResponseEntity<Void> delete(@RequestParam Long beneficiarioId) {
        beneficiarioService.delete(beneficiarioId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
