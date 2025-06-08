package com.seuprojeto.nfe.repository;

import com.seuprojeto.nfe.domain.Emitter;
import com.seuprojeto.nfe.dto.EmitterDTO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class EmitterRepository implements PanacheRepository<Emitter> {

    public Optional<Emitter> findByCnpj(String cnpj){
        return find("cnpj", cnpj).firstResultOptional();
    }

    public Emitter save(EmitterDTO dto){
        Emitter emitter = generateEmitterEntity(dto);
        emitter.persist();
        return emitter;
    }

    private static Emitter generateEmitterEntity(EmitterDTO dto) {
        Emitter emitter = new Emitter();
        emitter.cnpj = dto.cnpj;
        emitter.companyName = dto.companyName;
        emitter.ie = dto.ie;
        emitter.uf = dto.uf;
        return emitter;
    }
}
