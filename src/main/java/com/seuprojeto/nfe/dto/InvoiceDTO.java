package com.seuprojeto.nfe.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class InvoiceDTO {
    @NotNull
    public String emitterCNPJ;

    @NotBlank
    public String recipientCnpjCpf;

    @NotBlank
    public String recipientName;

    @NotBlank
    public String recipientUF;

    @NotEmpty
    public List<ItemDTO> items;

    public String getEmitterCNPJ() {
        return emitterCNPJ;
    }

    public void setEmitterCNPJ(String emitterCNPJ) {
        this.emitterCNPJ = emitterCNPJ;
    }

    public String getRecipientCnpjCpf() {
        return recipientCnpjCpf;
    }

    public void setRecipientCnpjCpf(String recipientCnpjCpf) {
        this.recipientCnpjCpf = recipientCnpjCpf;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientUF() {
        return recipientUF;
    }

    public void setRecipientUF(String recipientUF) {
        this.recipientUF = recipientUF;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

    public static class ItemDTO {
        @NotBlank
        public String productCode;

        @NotNull
        @Min(1)
        public Integer quantity;

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
}
