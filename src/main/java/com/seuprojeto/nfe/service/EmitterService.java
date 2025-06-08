package com.seuprojeto.nfe.service;

import com.seuprojeto.nfe.domain.Emitter;
import com.seuprojeto.nfe.dto.EmitterDTO;
import com.seuprojeto.nfe.repository.EmitterRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class EmitterService {

    @Inject
    EmitterRepository emitterRepository;

    public List<Emitter> listAll() {
        return emitterRepository.listAll();
    }

    @Transactional
    public Emitter save(EmitterDTO dto) {
        Optional<Emitter> existing = emitterRepository.findByCnpj(dto.cnpj);

        if (existing.isPresent()) {
            throw new WebApplicationException("CNPJ já cadastrado", 409);
        }
        return emitterRepository.save(dto);
    }
}
