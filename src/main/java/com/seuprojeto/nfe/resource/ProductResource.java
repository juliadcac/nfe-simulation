package com.seuprojeto.nfe.resource;

import com.seuprojeto.nfe.domain.Emitter;
import com.seuprojeto.nfe.domain.Product;
import com.seuprojeto.nfe.dto.ProductDTO;
import com.seuprojeto.nfe.service.ProductService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Products", description = "Cadastro de produtos")
public class ProductResource {

    @Inject
    ProductService productService;

    @Operation(summary = "Cria um novo produto")
    @APIResponse(
            responseCode = "201",
            description = "Produto criado com sucesso",
            content = @Content(schema = @Schema(implementation = Product.class))
    )
    @POST
    public Product create(@Valid ProductDTO dto) {
        return productService.save(dto);
    }
}
