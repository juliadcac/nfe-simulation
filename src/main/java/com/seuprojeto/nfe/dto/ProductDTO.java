package com.seuprojeto.nfe.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public class ProductDTO {

    @NotBlank
    public String code;

    @NotBlank
    public String name;

    @NotBlank
    @Pattern(regexp = "^\\d{8}$", message = "NCM deve conter exatamente 8 dígitos numéricos")
    public String ncm;

    @NotBlank
    @Pattern(
            regexp = "^(5|6)\\d{3}$",
            message = "CFOP deve começar com 5 ou 6 e conter 4 dígitos no total"
    )    public String cfop;

    @NotNull
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
    public BigDecimal unitValue;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNcm() {
        return ncm;
    }

    public void setNcm(String ncm) {
        this.ncm = ncm;
    }

    public String getCfop() {
        return cfop;
    }

    public void setCfop(String cfop) {
        this.cfop = cfop;
    }

    public BigDecimal getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(BigDecimal unitValue) {
        this.unitValue = unitValue;
    }
}
