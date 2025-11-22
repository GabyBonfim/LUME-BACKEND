package br.com.fiap.services;

import br.com.fiap.beans.Colaborador;
import br.com.fiap.beans.FeedbackTeste;
import br.com.fiap.dao.ColaboradorDAO;
import br.com.fiap.dao.FeedbackDAO;
import br.com.fiap.dao.TesteDAO;
import br.com.fiap.conexoes.ConexaoFactory;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/colaboradores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ColaboradorResource {

    private final ColaboradorDAO dao = new ColaboradorDAO();

    // 🔥 BLOQUEIA QUALQUER ROTA SE O BANCO TIVER CAÍDO
    private Response verificarBanco() {
        if (ConexaoFactory.isBancoOffline()) {
            return Response.status(503)
                    .entity("{\"erro\":\"Banco de dados indisponível. Tente novamente mais tarde.\"}")
                    .build();
        }
        return null;
    }

    @GET
    public Response listar() {
        Response erro = verificarBanco();
        if (erro != null) return erro;

        try {
            return Response.ok(dao.selecionar()).build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"erro\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @POST
    public Response inserir(Colaborador c) {
        Response erro = verificarBanco();
        if (erro != null) return erro;

        try {
            String msg = dao.inserir(c);
            return Response.ok("{\"mensagem\":\"" + msg + "\"}").build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"erro\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Colaborador c) {
        Response erro = verificarBanco();
        if (erro != null) return erro;

        try {
            c.setId(id);
            String msg = dao.atualizar(c);
            return Response.ok("{\"mensagem\":\"" + msg + "\"}").build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"erro\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) {
        Response erro = verificarBanco();
        if (erro != null) return erro;

        try {
            String msg = dao.deletar(id);
            return Response.ok("{\"mensagem\":\"" + msg + "\"}").build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"erro\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/{idColaborador}/teste/{idTeste}")
    public Response atrelarTeste(@PathParam("idColaborador") int idColaborador,
                                 @PathParam("idTeste") int idTeste) {

        Response erro = verificarBanco();
        if (erro != null) return erro;

        try {
            String msg = new TesteDAO().atribuirTeste(idColaborador, idTeste);
            return Response.ok("{\"mensagem\":\"" + msg + "\"}").build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"erro\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @GET
    @Path("/testes/{idColaborador}")
    public Response listarTestes(@PathParam("idColaborador") int idColaborador) {

        Response erro = verificarBanco();
        if (erro != null) return erro;

        try {
            TesteDAO testeDAO = new TesteDAO();
            return Response.ok(testeDAO.listarTestesDoColaborador(idColaborador)).build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"erro\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/{idColaborador}/teste/{idTeste}/responder")
    public Response responderTeste(
            @PathParam("idColaborador") int idColaborador,
            @PathParam("idTeste") int idTeste,
            FeedbackTeste resposta) {

        Response erro = verificarBanco();
        if (erro != null) return erro;

        try {
            resposta.setIdColaborador(idColaborador);
            resposta.setIdTeste(idTeste);

            String msg = new FeedbackDAO().registrar(resposta);

            return Response.ok("{\"feedback\":\"" + msg + "\"}").build();

        } catch (Exception e) {
            return Response.status(500).entity("{\"erro\":\"" + e.getMessage() + "\"}").build();
        }
    }
}
