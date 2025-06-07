package com.seuprojeto.nfe.repository;

import com.seuprojeto.nfe.domain.Product;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

    public Optional<Product> findByCode(String code) {
        return find("code", code).firstResultOptional();
    }
}
