package br.com.fiap.services;

import br.com.fiap.beans.Teste;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Endpoints REST relacionados a Testes.
 * O frontend vai consumir esses caminhos.
 */
@Path("/testes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TesteResource {

    private final TesteService testeService = new TesteService();

    // DTO simples para receber o corpo do POST
    public static class GerarTesteRequest {
        public String tema;
        public int quantidade;
    }

    /**
     * POST /testes/gerar
     * Corpo esperado:
     * {
     *   "tema": "Comunicação não violenta",
     *   "quantidade": 5
     * }
     */
    @POST
    @Path("/gerar")
    public Response gerarTeste(GerarTesteRequest request) {
        try {
            if (request == null || request.tema == null || request.tema.isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Tema é obrigatório").build();
            }

            int qtd = request.quantidade <= 0 ? 5 : request.quantidade;

            Teste teste = testeService.gerarTeste(request.tema, qtd);
            return Response.ok(teste).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao gerar teste: " + e.getMessage()).build();
        }
    }

    /**
     * GET /testes
     */
    @GET
    public Response listarTodos() {
        try {
            ArrayList<Teste> lista = testeService.listarTodos();
            return Response.ok(lista).build();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar testes: " + e.getMessage()).build();
        }
    }

    /**
     * DELETE /testes/{id}
     */
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) {
        try {
            String msg = testeService.deletar(id);
            return Response.ok(msg).build();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar teste: " + e.getMessage()).build();
        }
    }
}
