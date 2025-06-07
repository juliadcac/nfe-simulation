package com.seuprojeto.nfe.service;

import com.seuprojeto.nfe.domain.Product;
import com.seuprojeto.nfe.dto.ProductDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ProductService {

    @Transactional
    public Product save(ProductDTO dto) {
        Product produto = new Product();
        produto.code = dto.code;
        produto.name = dto.name;
        produto.ncm = dto.ncm;
        produto.cfop = dto.cfop;
        produto.unitValue = dto.unitValue;
        produto.persist();
        return produto;
    }
}
