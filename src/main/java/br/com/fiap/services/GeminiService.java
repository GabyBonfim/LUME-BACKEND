package br.com.fiap.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GeminiService {

    // Usa variável de ambiente ou a key fixa
    private static final String API_KEY =
            System.getenv("GEMINI_KEY") != null
                    ? System.getenv("GEMINI_KEY")
                    : "AIzaSyADyaKK3Ve5f83w0U5d8F2QewpkaM3L_6I";

    // MODELO CORRETO — 100% gratuito e suportado
    private static final String GEMINI_URL =
            "https://generativelanguage.googleapis.com/v1/models/gemini-1.5-flash:generateContent?key=";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();


    // PROMPT PADRÃO DA LUM.IA
    private static final String PROMPT_MOLDURA = """
            Você é a LUM.IA, a inteligência da plataforma LUME, uma plataforma online que gera 
            testes que treinam soft skills de colaboradores. 
            
            Sempre responda seguindo este formato elegante em Markdown:

            # 🎯 {Título Principal}

            ## 📌 Contexto
            {Explique em 2–4 linhas.}

            ## 🧩 O teste
            - Insight 1
            - Insight 2
            - Insight 3

            ## 🚀 Recomendações Práticas
            1. Ação 1
            2. Ação 2
            3. Ação 3

            ## 📊 Insight Final
            {Conclusão curta.}

            Emojis corporativos discretos, estrutura limpa e organizada.
            """;


    // ===========================================
    // GERAR TESTE
    // ===========================================
    public String gerarConteudoTeste(String tema, int quantidade) throws Exception {

        String prompt = """
                Gere um teste sobre o tema: "%s".
                Crie exatamente %d questões objetivas (A, B, C, D).
                Destaque a alternativa correta com **CORRETA:**.
                
                Use Markdown.
                """.formatted(tema, quantidade);

        return enviarParaGemini(prompt);
    }


    // ===========================================
    // CHAT
    // ===========================================
    public String conversar(String mensagem) throws Exception {

        String promptFinal = PROMPT_MOLDURA
                + "\n\nMensagem do usuário:\n"
                + mensagem;

        return enviarParaGemini(promptFinal);
    }


    // ===========================================
    // FUNÇÃO CENTRAL — ENVIA PARA A GEMINI
    // ===========================================
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
                    "Erro da API Gemini (" + resp.statusCode() + "):\n" + resp.body()
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
