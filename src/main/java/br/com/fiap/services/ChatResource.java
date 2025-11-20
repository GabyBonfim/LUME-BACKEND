package br.com.fiap.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/chat")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChatResource {

    private final GeminiService gemini = new GeminiService();

    // DTO para receber do front
    public static class ChatRequest {
        public String mensagem;
    }

    // DTO para enviar ao front
    public static class ChatResponse {
        public String resposta;

        public ChatResponse(String resposta) {
            this.resposta = resposta;
        }
    }

    @POST
    public Response conversar(ChatRequest req) {
        try {
            if (req == null || req.mensagem == null || req.mensagem.isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Campo 'mensagem' é obrigatório.")
                        .build();
            }

            String answer = gemini.conversar(req.mensagem);

            return Response.ok(new ChatResponse(answer)).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao conversar com a LUM.IA: " + e.getMessage())
                    .build();
        }
    }
}
