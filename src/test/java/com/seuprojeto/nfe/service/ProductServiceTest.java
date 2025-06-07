package com.seuprojeto.nfe.service;

import com.seuprojeto.nfe.domain.Product;
import com.seuprojeto.nfe.dto.ProductDTO;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class ProductServiceTest {

    @Inject
    ProductService productService;

    @Test
    @Transactional
    public void shouldCreateProduct(){
        ProductDTO dto = new ProductDTO();
        dto.code = "001";
        dto.name = "Teclado";
        dto.ncm = "84713012";
        dto.cfop = "5102";
        dto.unitValue = new java.math.BigDecimal("150.00");

        Product produto = productService.save(dto);

        Assertions.assertNotNull(produto.id);
        Assertions.assertEquals("001", produto.code);
        Assertions.assertEquals("Teclado", produto.name);
        Assertions.assertEquals("84713012", produto.ncm);
        Assertions.assertEquals("5102", produto.cfop);
        Assertions.assertEquals(new java.math.BigDecimal("150.00"), produto.unitValue);
    }
}
