package com.seuprojeto.nfe.repository;

import com.seuprojeto.nfe.domain.Product;
import com.seuprojeto.nfe.dto.ProductDTO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

    public Optional<Product> findByCode(String code) {
        return find("code", code).firstResultOptional();
    }

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
