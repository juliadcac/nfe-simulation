package com.seuprojeto.nfe.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
public class Emitter extends PanacheEntity {

    @NotBlank
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve ter 14 dígitos numéricos")
    public String cnpj;

    @NotBlank
    public String companyName;

    @NotBlank
    public String ie; // Inscrição Estadual

    @NotBlank
    @Pattern(regexp = "[A-Z]{2}", message = "UF deve conter 2 letras maiúsculas")
    public String uf;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIe() {
        return ie;
    }

    public void setIe(String ie) {
        this.ie = ie;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
