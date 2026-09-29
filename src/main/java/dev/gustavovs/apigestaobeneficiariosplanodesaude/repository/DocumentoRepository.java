package dev.gustavovs.apigestaobeneficiariosplanodesaude.repository;

import dev.gustavovs.apigestaobeneficiariosplanodesaude.entity.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
}
