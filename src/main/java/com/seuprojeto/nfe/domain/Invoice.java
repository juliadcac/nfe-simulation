package com.seuprojeto.nfe.domain;

import java.math.BigDecimal;
import java.util.List;

public class Invoice {

    public String emitterCNPJ;
    public String recipientCnpjCpf;
    public String recipientName;
    public String recipientUF;

    public List<ItemNota> items;
    public BigDecimal productsTotal;
    public BigDecimal icms;
    public BigDecimal invoiceTotal;

    public static class ItemNota {
        public String productCode;
        public String productName;
        public BigDecimal unitValue;
        public Integer quantity;
        public BigDecimal itemTotal;
    }
}
