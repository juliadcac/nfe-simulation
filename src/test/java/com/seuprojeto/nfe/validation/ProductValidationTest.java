package com.seuprojeto.nfe.validation;

import com.seuprojeto.nfe.dto.ProductDTO;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

@QuarkusTest
public class ProductValidationTest {

    @Inject
    Validator validator;

    @Test
    public void invalidNcm() {
        ProductDTO dto = new ProductDTO();
        dto.code = "P003";
        dto.name = "Teclado";
        dto.ncm = "8471"; // inválido: menos de 8 dígitos
        dto.cfop = "5101";
        dto.unitValue = new BigDecimal("120.00");

        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(dto);

        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().anyMatch(v ->
                v.getPropertyPath().toString().equals("ncm") &&
                        v.getMessage().contains("NCM deve conter exatamente 8 dígitos")
        ));
    }

    @Test
    public void invalidUnitValueZero() {
        ProductDTO dto = new ProductDTO();
        dto.code = "P004";
        dto.name = "Monitor";
        dto.ncm = "84716053";
        dto.cfop = "5102";
        dto.unitValue = new BigDecimal("0.00");

        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(dto);

        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().anyMatch(v ->
                v.getPropertyPath().toString().equals("unitValue")
        ));
    }

    @Test
    public void allFieldAreNull() {
        ProductDTO dto = new ProductDTO();

        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(dto);

        Assertions.assertEquals(5, violations.size(), "Todos os campos são obrigatórios.");
    }
}
