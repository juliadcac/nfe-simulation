package com.seuprojeto.nfe.resource;

import com.seuprojeto.nfe.domain.Emitter;
import com.seuprojeto.nfe.dto.EmitterDTO;
import com.seuprojeto.nfe.service.EmitterService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/emitters")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Emitters", description = "Cadastro de emitentes")
public class EmitterResource {


    @Inject
    EmitterService emitterService;

    @Operation(summary = "Lista todos os emitentes")
    @APIResponse(
            responseCode = "200",
            description = "Lista de emitentes",
            content = @Content(schema = @Schema(implementation = Emitter.class))
    )
    @GET
    public List<Emitter> list() {
        return emitterService.listAll();
    }

    @Operation(summary = "Cria um novo emitente")
    @APIResponse(
            responseCode = "201",
            description = "Emitente criado com sucesso",
            content = @Content(schema = @Schema(implementation = Emitter.class))
    )
    @POST
    public Emitter create(@Valid @RequestBody(required = true, content = @Content(schema = @Schema(implementation = EmitterDTO.class))) EmitterDTO dto) {
        return emitterService.save(dto);
    }
}
