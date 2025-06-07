package com.seuprojeto.nfe.resource;

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
import jakarta.ws.rs.core.Response;

@Path("/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductService productService;

    @POST
    public Product create(@Valid ProductDTO dto) {
        return productService.save(dto);
    }
}
