package com.seuprojeto.nfe.dto;

import com.seuprojeto.nfe.validation.CNPJ;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class EmitterDTO {
    @CNPJ
    public String cnpj;

    @NotBlank
    public String companyName;

    @NotBlank
    public String ie;

    @NotBlank
    @Pattern(regexp = "[A-Z]{2}", message = "UF deve conter 2 letras maiúsculas")
    public String uf;
}
