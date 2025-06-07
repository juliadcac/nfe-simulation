package com.seuprojeto.nfe.service;

import com.seuprojeto.nfe.domain.Product;
import com.seuprojeto.nfe.dto.ProductDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ProductService {

    @Transactional
    public Product save(ProductDTO dto) {
        Product product = new Product();
        product.code = dto.code;
        product.name = dto.name;
        product.ncm = dto.ncm;
        product.cfop = dto.cfop;
        product.unitValue = dto.unitValue;
        product.persist();
        return product;
    }
}
