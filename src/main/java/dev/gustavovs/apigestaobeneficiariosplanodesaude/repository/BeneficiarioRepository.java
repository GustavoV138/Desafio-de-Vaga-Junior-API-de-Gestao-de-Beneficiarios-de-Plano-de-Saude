package dev.gustavovs.apigestaobeneficiariosplanodesaude.repository;

import dev.gustavovs.apigestaobeneficiariosplanodesaude.entity.Beneficiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeneficiarioRepository extends JpaRepository<Beneficiario, Long> {
}
