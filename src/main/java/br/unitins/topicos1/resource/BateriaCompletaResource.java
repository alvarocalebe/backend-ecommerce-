package br.unitins.topicos1.resource;


import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import br.unitins.topicos1.application.Error;
import br.unitins.topicos1.application.Result;
import br.unitins.topicos1.dto.BateriaCompletaDTO;
import br.unitins.topicos1.form.ProdutoImageForm;
import br.unitins.topicos1.service.ProdutoFileService;
import br.unitins.topicos1.service.BateriaCompletaService;

import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

@Path("/bateriacompleta")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BateriaCompletaResource {

    @Inject
    BateriaCompletaService service;

    @Inject
    ProdutoFileService fileService;

    private static final Logger LOG = Logger.getLogger(BateriaCompletaResource.class);

       @PATCH
    @Path("/upload/imagem/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response salvarImagem(@MultipartForm ProdutoImageForm form,@PathParam("id") Long id ){
        try {
            fileService.salvar(form.getNomeImagem(), form.getImagem());
            LOG.infof("Download do arquivo concluido com sucesso: " + form.getNomeImagem());
        } catch (Exception e) {
            LOG.error("Erro ao realizar o download do arquivo");
            e.printStackTrace();
            Error error = new Error("409", e.getMessage());
            return Response.status(Status.CONFLICT).entity(error).build();
        }

       
     service.updateNomeImagem(id, form.getNomeImagem());

        return Response.ok(Status.NO_CONTENT).build();

    }

    @GET
    @Path("/download/imagem/{nomeImagem}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("nomeImagem") String nomeImagem) {
        ResponseBuilder response = Response.ok(fileService.obter(nomeImagem));
        response.header("Content-Disposition", "attachment;filename="+nomeImagem);
        return response.build();
    }



    @POST
    public Response insert(@Valid BateriaCompletaDTO dto) {
        LOG.infof("Inserindo bateria completa: %s", dto.nomeBateria());
        Result result = null;
        try {
            LOG.info("Bateria Completa inserida.");
            return Response.status(Status.CREATED).entity(service.insert(dto)).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir uma Bateria.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);

        }
        return Response
        .status(Status.NOT_FOUND)
        .entity(result)
        .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(BateriaCompletaDTO dto, @PathParam("id") Long id) {
        Result result = null;
        try {
            service.update(dto, id);
            LOG.infof("Bateria (%d) atualizado com sucesso.", id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            LOG.errorf("Erro ao atualizar uma Bateria. ", id, e);
            LOG.debug(e.getMessage());

            result = new Result(e.getConstraintViolations());
        }catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);

        }
        return Response
                .status(Status.NOT_FOUND)
                .entity(result)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {        
            LOG.info("Buscando Bateria por ID... ");
           if (service.findById(id)!=null) {
              service.delete(id);
            LOG.infof("Bateria excluido com sucesso.");
           return Response.noContent().build();
              }
              LOG.errorf("Nao existe produto com esse id.");

                return Response.status(Status.NOT_FOUND).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        LOG.infof("Buscando Bateria por ID. ", id);
        if (service.findById(id)!=null) {
            LOG.infof("Bateria (%d) encontrado com sucesso.", id);
            return Response.ok(service.findById(id)).build();
        }
        LOG.errorf("Bateria com id (%d) nao encontrado.", id);
        return Response.status(Status.NOT_FOUND).build();
    }


    @GET
    @Path("/busca/{nome}")
    public Response findByName(@PathParam("nome") String nome) {
        LOG.infof("Buscando Bateria pelo (%d). ", nome);
        try {
            if (!service.findByName(nome).isEmpty()) {
            LOG.infof("Nome (%d) encontrado com sucesso.", nome);
            return Response.ok(service.findByName(nome)).build();
        }
        } catch (Exception e) {
            LOG.errorf("Nome (%d) nao encontrado.", nome);
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @GET
    public Response findAll() {
    
        return Response.ok(service.getAll()).build();
    }

}