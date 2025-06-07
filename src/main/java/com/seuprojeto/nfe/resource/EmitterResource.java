package com.seuprojeto.nfe.resource;

import com.seuprojeto.nfe.domain.Emitter;
import com.seuprojeto.nfe.dto.EmitterDTO;
import com.seuprojeto.nfe.service.EmitterService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/emitters")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmitterResource {


    @Inject
    EmitterService emitterService;

    @GET
    public List<Emitter> list() {
        return emitterService.listAll();
    }

    @POST
    public Emitter create(@Valid EmitterDTO dto) {
        return emitterService.save(dto);
    }
}
