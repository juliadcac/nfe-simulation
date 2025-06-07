package com.seuprojeto.nfe.service;

import com.seuprojeto.nfe.domain.Emitter;
import com.seuprojeto.nfe.domain.Product;
import com.seuprojeto.nfe.dto.InvoiceDTO;
import com.seuprojeto.nfe.repository.EmitterRepository;
import com.seuprojeto.nfe.repository.ProductRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

@Transactional
@QuarkusTest
public class InvoiceServiceTest {

    @Inject
    InvoiceService invoiceService;

    @Inject
    EmitterRepository emitterRepository;

    @Inject
    ProductRepository productRepository;

    Emitter emitter;
    Product product;

    @BeforeEach
    void setup() {
        emitter = new Emitter();
        emitter.cnpj = "12345678000195";
        emitter.companyName = "Empresa Exemplo";
        emitter.ie = "12345678";
        emitter.uf = "SC";
        emitterRepository.persist(emitter);

        product = new Product();
        product.code = "P001";
        product.name = "Produto Exemplo";
        product.cfop = "5102";
        product.ncm = "12345678";
        product.unitValue = new BigDecimal("150.00");
        productRepository.persist(product);
    }

    @Test
    void mustEmitValidInvoice() {
        InvoiceDTO dto = newInvoiceDTO("SC");

        String xml = invoiceService.emitInvoice(dto);

        Assertions.assertNotNull(xml);
        Assertions.assertTrue(xml.contains("<NFe>"), "XML gerado não contém cabeçalho esperado");
    }

    @Test
    void mustRejectInvoiceWithZeroValue() {
        InvoiceDTO dto = newInvoiceDTO("SC");
        dto.items.get(0).quantity = 0;

        Assertions.assertThrows(BadRequestException.class, () -> invoiceService.emitInvoice(dto));
    }

    @Test
    void mustGenerateInvoiceWithCorrectValues() {
        InvoiceDTO dto = newInvoiceDTO("SC");

        var invoice = invoiceService.generateInvoice(dto);

        Assertions.assertNotNull(invoice);
        Assertions.assertEquals(emitter.cnpj, invoice.emitterCNPJ);
        Assertions.assertEquals(dto.recipientCnpjCpf, invoice.recipientCnpjCpf);
        Assertions.assertEquals(dto.recipientName, invoice.recipientName);
        Assertions.assertEquals(dto.recipientUF, invoice.recipientUF);

        BigDecimal expectedItemTotal = product.unitValue.multiply(BigDecimal.valueOf(dto.items.get(0).quantity));
        BigDecimal expectedICMS = expectedItemTotal.multiply(new BigDecimal("0.18"));
        BigDecimal expectedTotal = expectedItemTotal.add(expectedICMS);

        Assertions.assertEquals(expectedItemTotal, invoice.productsTotal);
        Assertions.assertEquals(expectedICMS, invoice.icms);
        Assertions.assertEquals(expectedTotal, invoice.invoiceTotal);

        Assertions.assertEquals(1, invoice.items.size());
        Assertions.assertEquals(product.code, invoice.items.get(0).productCode);
    }

    private InvoiceDTO newInvoiceDTO(String recipientUf) {
        InvoiceDTO dto = new InvoiceDTO();
        dto.emitterCNPJ = emitter.cnpj;
        dto.recipientCnpjCpf = "98765432100";
        dto.recipientName = "Cliente Teste";
        dto.recipientUF = recipientUf;

        InvoiceDTO.ItemDTO item = new InvoiceDTO.ItemDTO();
        item.productCode = product.code;
        item.quantity = 1;

        dto.items = List.of(item);
        return dto;
    }
}
