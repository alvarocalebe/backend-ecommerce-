package br.unitins.topicos1.resource;

import br.unitins.topicos1.dto.EnderecoDTO;
import br.unitins.topicos1.service.EnderecoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/endereco")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    @Inject
    EnderecoService service;

    @POST
    @Path("/{id_cliente}")
    public Response insert(@Valid EnderecoDTO dto, @PathParam("id_cliente") Long idCliente) {
        return Response.status(Status.CREATED).entity(service.insert(dto, idCliente)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(EnderecoDTO dto, @PathParam("id") Long id) {
        service.update(dto, id);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    @GET
    @Path("/busca/{cep}")
    public Response findByCep(@PathParam("cep") String cep) {
        return Response.ok(service.findByCep(cep)).build();
    }
}