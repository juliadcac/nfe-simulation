package com.seuprojeto.nfe.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
public class Product  extends PanacheEntity {

    @NotBlank
    public String code;

    @NotBlank
    public String name;

    @NotBlank
    public String ncm;

    @NotBlank
    public String cfop;

    @NotNull
    public BigDecimal unitValue;
}
