package com.seuprojeto.nfe.validation;

import com.seuprojeto.nfe.dto.EmitterDTO;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

@QuarkusTest
public class EmitterValidationTest {

    @Inject
    Validator validator;

    @Test
    public void incorrectCNPJ(){
        EmitterDTO dto = new EmitterDTO();
        dto.cnpj = "123";
        dto.companyName = "Empresa XYZ";
        dto.ie = "123456789";
        dto.uf = "SP";

        Set<ConstraintViolation<EmitterDTO>> violations = validator.validate(dto);

        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("cnpj")));
    }

    @Test
    public void allFieldAreNull(){
        EmitterDTO dto = new EmitterDTO();

        Set<ConstraintViolation<EmitterDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(4, violations.size());
    }
}
