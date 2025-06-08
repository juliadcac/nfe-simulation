package com.seuprojeto.nfe.service;

import com.seuprojeto.nfe.domain.Product;
import com.seuprojeto.nfe.dto.ProductDTO;
import com.seuprojeto.nfe.repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;

import java.util.Optional;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;

    @Transactional
    public Product save(ProductDTO dto) {
        Optional<Product> product = productRepository.findByCode(dto.getCode());

        if(product.isPresent()){
            throw new WebApplicationException("Código de produto já cadastrado", 409);
        }

        return productRepository.save(dto);
    }
}
