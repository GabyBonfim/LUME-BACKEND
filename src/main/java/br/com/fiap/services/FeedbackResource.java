package br.com.fiap.services;

import br.com.fiap.dao.FeedbackDAO;
import br.com.fiap.beans.FeedbackTeste;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/feedbacks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FeedbackResource {

    private final FeedbackDAO dao = new FeedbackDAO();

    @POST
    @Path("/registrar")
    public Response registrar(FeedbackTeste fb) {
        try {
            String msg = dao.registrar(fb);
            return Response.ok(msg).build();

        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao registrar feedback: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/colaborador/{id}")
    public Response listar(@PathParam("id") int idColab) {
        try {
            List<FeedbackTeste> lista = dao.listarPorColaborador(idColab);
            return Response.ok(lista).build();

        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar feedbacks: " + e.getMessage())
                    .build();
        }
    }
}
