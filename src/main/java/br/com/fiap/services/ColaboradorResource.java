package br.com.fiap.services;

import br.com.fiap.beans.Colaborador;
import br.com.fiap.dao.ColaboradorDAO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/colaboradores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ColaboradorResource {

    private final ColaboradorDAO dao = new ColaboradorDAO();

    @GET
    public List<Colaborador> listar() throws SQLException, ClassNotFoundException {
        return dao.selecionar();
    }

    @POST
    public Response inserir(Colaborador c) throws SQLException, ClassNotFoundException {
        String msg = dao.inserir(c);
        return Response.ok(msg).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Colaborador c)
            throws SQLException, ClassNotFoundException {

        c.setId(id);
        String msg = dao.atualizar(c);
        return Response.ok(msg).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id)
            throws SQLException, ClassNotFoundException {

        String msg = dao.deletar(id);
        return Response.ok(msg).build();
    }
}
