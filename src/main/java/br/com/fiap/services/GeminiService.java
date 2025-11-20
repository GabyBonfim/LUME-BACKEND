package br.com.fiap.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GeminiService {

    // Usa variável de ambiente, mas se não existir, usa KEY fixa (evita erro)
    private static final String API_KEY =
            System.getenv("GEMINI_KEY") != null ?
                    System.getenv("GEMINI_KEY") :
                    "AIzaSyCiBDQyrylTQVXpfV1HqLiZN6NJ3hKx56U"; // <-- coloque aqui se quiser evitar erro

    private static final String GEMINI_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    // ========= GERAR TESTE =========
    public String gerarConteudoTeste(String tema, int quantidade) throws Exception {

        String prompt = """
                Você é a LUM.IA, inteligênca da plataforma LUME.
                Gere um teste sobre o tema: "%s".
                Crie exatamente %d questões objetivas (A, B, C, D).
                Destaque a alternativa correta com "**CORRETA:**".
                Formate em Markdown organizado.
                """.formatted(tema, quantidade);

        return enviarParaGemini(prompt);
    }

    // ========= CHAT =========
    public String conversar(String mensagem) throws Exception {
        return enviarParaGemini(mensagem);
    }

    // ========= FUNÇÃO CENTRAL =========
    private String enviarParaGemini(String texto) throws Exception {

        if (API_KEY == null || API_KEY.isBlank()) {
            throw new RuntimeException("API KEY da Gemini não configurada.");
        }

        String jsonBody = """
                {
                  "contents": [
                    {
                      "parts": [
                        { "text": %s }
                      ]
                    }
                  ]
                }
                """.formatted(mapper.writeValueAsString(texto));

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(GEMINI_URL + API_KEY))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> resp =
                httpClient.send(req, HttpResponse.BodyHandlers.ofString());

        if (resp.statusCode() != 200) {
            throw new RuntimeException(
                    "Erro da API Gemini (" + resp.statusCode() + "): " + resp.body()
            );
        }

        JsonNode json = mapper.readTree(resp.body());

        JsonNode textNode = json
                .path("candidates").path(0)
                .path("content")
                .path("parts").path(0)
                .path("text");

        if (textNode.isMissingNode()) {
            throw new RuntimeException("Resposta inesperada da Gemini: " + resp.body());
        }

        return textNode.asText();
    }
}
