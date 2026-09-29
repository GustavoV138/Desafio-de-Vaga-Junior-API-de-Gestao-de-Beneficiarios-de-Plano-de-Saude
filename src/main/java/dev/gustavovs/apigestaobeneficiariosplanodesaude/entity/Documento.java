package dev.gustavovs.apigestaobeneficiariosplanodesaude.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TipoDocumento tipoDocumento;
    private String descricao;
    @Column(updatable = false)
    private Instant dataInclusao;
    private Instant dataAtualizacao;

    public Documento() {}

    public Documento(TipoDocumento tipoDocumento, String descricao) {
        this.tipoDocumento = tipoDocumento;
        this.descricao = descricao;
    }

    @PrePersist
    void onCreate() {
        this.dataInclusao = Instant.now();
        this.dataAtualizacao = dataInclusao;
    }

    @PreUpdate
    void onUpdate() {
        this.dataAtualizacao = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Instant getDataInclusao() {
        return dataInclusao;
    }

    public Instant getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Instant dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Documento documento = (Documento) o;
        return Objects.equals(id, documento.id) && Objects.equals(tipoDocumento, documento.tipoDocumento) && Objects.equals(descricao, documento.descricao) && Objects.equals(dataInclusao, documento.dataInclusao) && Objects.equals(dataAtualizacao, documento.dataAtualizacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipoDocumento, descricao, dataInclusao, dataAtualizacao);
    }
}
