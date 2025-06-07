package com.seuprojeto.nfe.resource;

import com.seuprojeto.nfe.domain.Invoice;
import com.seuprojeto.nfe.dto.InvoiceDTO;
import com.seuprojeto.nfe.service.InvoiceService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/nfe")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Invoice", description = "Cadastro e Simulação de envio de NF-e")
public class InvoiceResource {

    @Inject
    InvoiceService invoiceService;

    @POST
    @Operation(summary = "Gerar NF-e simulada", description = "Gera uma nota fiscal eletrônica com base nos dados enviados, sem enviar ao SEFAZ.")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "NF-e gerada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Invoice.class))),
            @APIResponse(responseCode = "400", description = "Dados inválidos ou incompletos")
    })
    public Invoice creteInvoice(@RequestBody(required = true, content = @Content(schema = @Schema(implementation = InvoiceDTO.class)))
                                    @Valid InvoiceDTO dto) {
        return invoiceService.generateInvoice(dto);
    }

    @POST
    @Path("/send")
    @Operation(summary = "Emitir NF-e", description = "Realiza a emissão (simulada) da NF-e e retorna o XML com protocolo.")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "NF-e emitida com sucesso",
                    content = @Content(mediaType = "application/xml")),
            @APIResponse(responseCode = "400", description = "Erro de validação ou inconsistência fiscal")
    })
    public Response sendInvoice(@RequestBody(required = true, content = @Content(schema = @Schema(implementation = InvoiceDTO.class)))
                                            @Valid InvoiceDTO invoiceDTO){
        String xml = invoiceService.emitInvoice(invoiceDTO);
        return Response.ok().entity(xml).build();
    }
}
