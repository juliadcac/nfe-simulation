package com.seuprojeto.nfe.service;

import com.seuprojeto.nfe.domain.Emitter;
import com.seuprojeto.nfe.domain.Invoice;
import com.seuprojeto.nfe.domain.Invoice.ItemNota;
import com.seuprojeto.nfe.domain.Product;
import com.seuprojeto.nfe.dto.InvoiceDTO;
import com.seuprojeto.nfe.dto.InvoiceDTO.ItemDTO;
import com.seuprojeto.nfe.repository.EmitterRepository;
import com.seuprojeto.nfe.repository.ProductRepository;
import com.seuprojeto.nfe.util.InvoiceXmlGenerator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.WebApplicationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class InvoiceService {

    public static final String PREFIX_PROTOCOL = "PROTOCOLO-";

    @Inject
    EmitterRepository emitterRepository;

    @Inject
    ProductRepository productRepository;

    @Transactional
    public Invoice generateInvoice(InvoiceDTO dto){
        return processInvoiceData(dto);
    }

    public String emitInvoice(InvoiceDTO dto) {
        Invoice invoice = processInvoiceData(dto);
        String protocol = PREFIX_PROTOCOL + System.currentTimeMillis();

        return InvoiceXmlGenerator.generateXml(
                dto,
                invoice.productsTotal.doubleValue(),
                invoice.icms.doubleValue(),
                invoice.invoiceTotal.doubleValue(),
                protocol
        );

    }

    private void validateCfop(Product product, boolean interestadual) {
        String cfop = product.getCfop();

        if (cfop == null || !cfop.matches("\\d{4}")) {
            throw new WebApplicationException("CFOP inválido: formato incorreto", 400);
        }

        boolean cfopValid = interestadual
                ? cfop.startsWith("6") // CFOPs interestaduais
                : cfop.startsWith("5"); // CFOPs internos

        if (!cfopValid) {
            throw new WebApplicationException("CFOP " + cfop + " incompatível com operação " +
                    (interestadual ? "interestadual" : "interna"), 400);
        }
    }

    private Invoice processInvoiceData(InvoiceDTO dto) {
        Emitter emitter = emitterRepository.findByCnpj(dto.emitterCNPJ)
                .orElseThrow(() -> new BadRequestException("Emitente não encontrado"));

        boolean interstateOperation = !emitter.getUf().equalsIgnoreCase(dto.getRecipientUF());

        List<ItemNota> invoiceItems = new ArrayList<>();
        BigDecimal totalProdutos = BigDecimal.ZERO;

        for (ItemDTO itemDTO : dto.getItems()) {
            Product product = productRepository.findByCode(itemDTO.getProductCode())
                    .orElseThrow(() -> new WebApplicationException("Produto não encontrado: " + itemDTO.getProductCode(), 404));

            if (!product.getNcm().matches("\\d{8}")) {
                throw new WebApplicationException("NCM inválido para produto " + product.getCode(), 400);
            }

            validateCfop(product, interstateOperation);

            BigDecimal totalItem = product.getUnitValue().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));
            totalProdutos = totalProdutos.add(totalItem);

            ItemNota itemNota = new ItemNota();
            itemNota.productCode = product.getCode();
            itemNota.productName = product.getName();
            itemNota.unitValue = product.getUnitValue();
            itemNota.quantity = itemDTO.getQuantity();
            itemNota.itemTotal = totalItem;

            invoiceItems.add(itemNota);
        }

        if (totalProdutos.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Valor total da nota não pode ser zero");
        }

        BigDecimal icms = totalProdutos.multiply(new BigDecimal("0.18"));
        BigDecimal invoiceTotal = totalProdutos.add(icms);

        Invoice invoice = new Invoice();
        invoice.emitterCNPJ = emitter.getCnpj();
        invoice.recipientCnpjCpf = dto.getRecipientCnpjCpf();
        invoice.recipientName = dto.getRecipientName();
        invoice.recipientUF = dto.getRecipientUF();
        invoice.items = invoiceItems;
        invoice.productsTotal = totalProdutos;
        invoice.icms = icms;
        invoice.invoiceTotal = invoiceTotal;

        return invoice;
    }
}
