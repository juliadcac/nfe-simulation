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

@Path("/nfe")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InvoiceResource {

    @Inject
    InvoiceService invoiceService;

    @POST
    public Invoice creteInvoice(@Valid InvoiceDTO dto) {
        return invoiceService.generateInvoice(dto);
    }

    @POST
    @Path("/send")
    public Response sendInvoice(@Valid InvoiceDTO invoiceDTO){
        String xml = invoiceService.emitInvoice(invoiceDTO);
        return Response.ok().entity(xml).build();
    }
}
