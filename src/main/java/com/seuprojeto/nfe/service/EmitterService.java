package com.seuprojeto.nfe.service;

import com.seuprojeto.nfe.domain.Emitter;
import com.seuprojeto.nfe.dto.EmitterDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class EmitterService {
    public List<Emitter> listAll() {
        return Emitter.listAll();
    }

    @Transactional
    public Emitter save(EmitterDTO dto) {
        Emitter emitente = new Emitter();
        emitente.cnpj = dto.cnpj; //validar digito verificador?
        emitente.companyName = dto.companyName;
        emitente.ie = dto.ie;
        emitente.uf = dto.uf;
        emitente.persist();
        return emitente;
    }
}
