package com.seuprojeto.nfe.repository;

import com.seuprojeto.nfe.domain.Emitter;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class EmitterRepository implements PanacheRepository<Emitter> {

    public Optional<Emitter> findByCnpj(String cnpj){
        return find("cnpj", cnpj).firstResultOptional();
    }
}
