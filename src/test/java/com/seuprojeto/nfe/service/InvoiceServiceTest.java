package com.seuprojeto.nfe.service;

import com.seuprojeto.nfe.domain.Emitter;
import com.seuprojeto.nfe.domain.Product;
import com.seuprojeto.nfe.dto.InvoiceDTO;
import com.seuprojeto.nfe.repository.EmitterRepository;
import com.seuprojeto.nfe.repository.ProductRepository;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

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

    @Test
    void mustEmitValidInvoice() {
        var dto = setupData("56317681000170", "P001", "SC");

        String xml = invoiceService.emitInvoice(dto);

        Assertions.assertNotNull(xml);
        Assertions.assertTrue(xml.contains("<NFe>"), "XML gerado não contém cabeçalho esperado");
    }

    @Test
    void mustRejectInvoiceWithZeroValue() {
        var dto = setupData("45723174000110", "P002", "SC");
        dto.items.get(0).quantity = 0;

        Assertions.assertThrows(BadRequestException.class, () -> invoiceService.emitInvoice(dto));
    }

    @Test
    void mustGenerateInvoiceWithCorrectValues() {
        var dto = setupData("69745762000113", "P003", "SC");

        var invoice = invoiceService.generateInvoice(dto);

        Assertions.assertNotNull(invoice);
        Assertions.assertEquals(dto.emitterCNPJ, invoice.emitterCNPJ);
        Assertions.assertEquals(dto.recipientCnpjCpf, invoice.recipientCnpjCpf);
        Assertions.assertEquals(dto.recipientName, invoice.recipientName);
        Assertions.assertEquals(dto.recipientUF, invoice.recipientUF);

        BigDecimal expectedItemTotal = new BigDecimal("150.00");
        BigDecimal expectedICMS = expectedItemTotal.multiply(new BigDecimal("0.18"));
        BigDecimal expectedTotal = expectedItemTotal.add(expectedICMS);

        Assertions.assertEquals(expectedItemTotal, invoice.productsTotal);
        Assertions.assertEquals(expectedICMS, invoice.icms);
        Assertions.assertEquals(expectedTotal, invoice.invoiceTotal);

        Assertions.assertEquals(1, invoice.items.size());
        Assertions.assertEquals("P003", invoice.items.get(0).productCode);
    }

    @Transactional
    InvoiceDTO setupData(String cnpj, String productCode, String uf) {
        Emitter emitter = new Emitter();
        emitter.cnpj = cnpj;
        emitter.companyName = "Empresa Teste";
        emitter.ie = "12345678";
        emitter.uf = uf;
        emitterRepository.persist(emitter);

        Product product = new Product();
        product.code = productCode;
        product.name = "Produto Teste";
        product.cfop = "5102";
        product.ncm = "12345678";
        product.unitValue = new BigDecimal("150.00");
        productRepository.persist(product);

        InvoiceDTO dto = new InvoiceDTO();
        dto.emitterCNPJ = emitter.cnpj;
        dto.recipientCnpjCpf = "98765432100";
        dto.recipientName = "Cliente Teste";
        dto.recipientUF = uf;

        InvoiceDTO.ItemDTO item = new InvoiceDTO.ItemDTO();
        item.productCode = product.code;
        item.quantity = 1;

        dto.items = List.of(item);
        return dto;
    }
}
