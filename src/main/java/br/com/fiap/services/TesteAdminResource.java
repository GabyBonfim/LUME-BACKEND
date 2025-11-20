package br.com.fiap.services;

import br.com.fiap.beans.Teste;
import br.com.fiap.dao.TesteDAO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/admin/testes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TesteAdminResource {

    private final TesteDAO dao = new TesteDAO();

    // DTO para atribuir teste
    public static class AtribuirRequest {
        public int idColaborador;
        public int idTeste;
    }

    @POST
    @Path("/atribuir")
    public Response atribuir(AtribuirRequest req) {
        try {
            if (req.idColaborador <= 0 || req.idTeste <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("idColaborador e idTeste são obrigatórios")
                        .build();
            }

            String msg = dao.atribuirTeste(req.idColaborador, req.idTeste);
            return Response.ok(msg).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atribuir teste: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/colaborador/{id}")
    public Response listarTestesColaborador(@PathParam("id") int idColab) {
        try {
            List<Teste> lista = dao.listarTestesDoColaborador(idColab);
            return Response.ok(lista).build();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar testes do colaborador")
                    .build();
        }
    }
}
