package com.seuprojeto.nfe.service;

import com.seuprojeto.nfe.domain.Emitter;
import com.seuprojeto.nfe.dto.EmitterDTO;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class EmittersServiceTest {

    @Inject
    EmitterService emitterService;

    @Test
    @Transactional
    public void shouldCreateEmitter(){
        EmitterDTO dto = new EmitterDTO();
        dto.companyName = "Company Test";
        dto.ie = "123456789";
        dto.uf = "SC";
        dto.cnpj = "12345678000195";

        Emitter emitter = emitterService.save(dto);

        Assertions.assertNotNull(emitter.id);
        Assertions.assertEquals("12345678000195", emitter.cnpj);
        Assertions.assertEquals("123456789", emitter.ie);
        Assertions.assertEquals("SC", emitter.uf);
    }
}
