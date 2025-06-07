package com.seuprojeto.nfe.util;

import com.seuprojeto.nfe.dto.InvoiceDTO;

public class InvoiceXmlGenerator {
    public static String generateXml(InvoiceDTO dto, double total, double icms, double totalWithTax, String protocol) {
        StringBuilder sb = new StringBuilder();
        sb.append("<NFe>\n");
        sb.append("  <infNFe>\n");

        sb.append("    <emitente>\n");
        sb.append("      <cnpj>").append(dto.getEmitterCNPJ()).append("</cnpj>\n");
        sb.append("    </emitente>\n");

        sb.append("    <destinatario>\n");
        sb.append("      <cpfCnpj>").append(dto.getRecipientCnpjCpf()).append("</cpfCnpj>\n");
        sb.append("      <nome>").append(dto.getRecipientName()).append("</nome>\n");
        sb.append("      <uf>").append(dto.getRecipientUF()).append("</uf>\n");
        sb.append("    </destinatario>\n");

        sb.append("    <itens>\n");
        for (InvoiceDTO.ItemDTO item : dto.getItems()) {
            sb.append("      <item>\n");
            sb.append("        <produto>").append(item.getProductCode()).append("</produto>\n");
            sb.append("        <quantidade>").append(item.getQuantity()).append("</quantidade>\n");
            sb.append("      </item>\n");
        }
        sb.append("    </itens>\n");

        sb.append("    <totais>\n");
        sb.append("      <valorTotal>").append(total).append("</valorTotal>\n");
        sb.append("      <icms>").append(icms).append("</icms>\n");
        sb.append("      <totalWithTax>").append(totalWithTax).append("</totalWithTax>\n");
        sb.append("    </totais>\n");

        sb.append("    <protocol>").append(protocol).append("</protocol>\n");

        sb.append("  </infNFe>\n");
        sb.append("</NFe>");
        return sb.toString();
    }
}
