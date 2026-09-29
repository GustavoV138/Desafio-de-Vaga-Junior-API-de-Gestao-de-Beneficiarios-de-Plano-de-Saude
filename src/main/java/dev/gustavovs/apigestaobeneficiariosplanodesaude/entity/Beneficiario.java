package dev.gustavovs.apigestaobeneficiariosplanodesaude.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
public class Beneficiario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String telefone;
    private LocalDate dataNascimento;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Documento> documentos;
    @Column(updatable = false)
    private Instant dataInclusao;
    private Instant dataAtualizacao;

    public Beneficiario(){}

    public Beneficiario(String nome, String telefone, LocalDate dataNascimento, Set<Documento> documentos) {
        this.nome = nome;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.documentos = documentos;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Set<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(Set<Documento> documentos) {
        this.documentos = documentos;
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
        if (!(o instanceof Beneficiario that)) return false;
        return Objects.equals(id, that.id) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(telefone, that.telefone) &&
                Objects.equals(dataNascimento, that.dataNascimento) &&
                Objects.equals(dataInclusao, that.dataInclusao) &&
                Objects.equals(dataAtualizacao, that.dataAtualizacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, telefone, dataNascimento, dataInclusao, dataAtualizacao);
    }


    public static final class BeneficiarioBuilder {
        private String nome;
        private String telefone;
        private LocalDate dataNascimento;
        private Set<Documento> documentos;

        private BeneficiarioBuilder() {
        }

        public static BeneficiarioBuilder builder() {
            return new BeneficiarioBuilder();
        }

        public BeneficiarioBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public BeneficiarioBuilder telefone(String telefone) {
            this.telefone = telefone;
            return this;
        }

        public BeneficiarioBuilder dataNascimento(LocalDate dataNascimento) {
            this.dataNascimento = dataNascimento;
            return this;
        }

        public BeneficiarioBuilder documentos(Set<Documento> documentos) {
            this.documentos = documentos;
            return this;
        }

        public Beneficiario build() {
            Beneficiario beneficiario = new Beneficiario();
            beneficiario.setNome(nome);
            beneficiario.setTelefone(telefone);
            beneficiario.setDataNascimento(dataNascimento);
            beneficiario.setDocumentos(documentos);
            return beneficiario;
        }
    }
}
