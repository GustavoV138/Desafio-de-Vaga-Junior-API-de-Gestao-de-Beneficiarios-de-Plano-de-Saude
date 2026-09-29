package dev.gustavovs.apigestaobeneficiariosplanodesaude.entity;

public enum TipoDocumento {

    CPF("CPF"),
    RG("RG"),
    CNH("CNH"),
    PASSAPORTE("Passaporte");

    private final String documento;

    TipoDocumento(String documento) {
        this.documento = documento;
    }

    public String getDocumento() {
        return this.documento;
    }
}
