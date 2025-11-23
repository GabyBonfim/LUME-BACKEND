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

    // MODELO 100% GRATUITO E COMPATÍVEL
    private static final String GEMINI_URL =
            "https://generativelanguage.googleapis.com/v1/models/gemini-pro:generateContent?key=";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();


    // PROMPT PADRÃO DA LUM.IA
    private static final String PROMPT_MOLDURA = """
            Você é a LUM.IA, a inteligência da plataforma LUME, uma plataforma online que gera 
            testes que treinam soft skills de colaboradores. 
            
            Quando te perguntarem sobre quais testes temos disponíveis ou se você pode gerar,
            você deve questionar o nome e ID do colaborador, segmento da empresa, o setor 
            e a função específica desse colaborador, e gerar um teste com base nisso.
            Os testes devem ser divertidos, úteis e práticos.

            IMPORTANTE:
            Sempre responda seguindo EXATAMENTE este formato:

            # 🎯 {Título Principal}

            ## 📌 Contexto
            {Explique em 2–4 linhas de forma clara e profissional.}

            ## 🧩 O teste
            - Insight 1
            - Insight 2
            - Insight 3

            ## 🚀 Recomendações Práticas
            1. Ação 1
            2. Ação 2
            3. Ação 3

            ## 📊 Insight Final
            {Conclusão curta e objetiva.}

            Mantenha:
            - clareza
            - bullets
            - espaçamentos
            - Markdown elegante
            - emojis profissionais discretos
            
            Se receber um teste já respondido, gere a análise e peça para adicionar na aba "minhas análises".
            """;


    // ===========================================
    // GERAR TESTE
    // ===========================================
    public String gerarConteudoTeste(String tema, int quantidade) throws Exception {

        String prompt = """
                Você é a LUM.IA. Gere um teste sobre o tema: "%s".
                Crie exatamente %d questões objetivas (A, B, C, D)
                e destaque a correta com **CORRETA:**.

                Use Markdown padronizado.
                """.formatted(tema, quantidade);

        return enviarParaGemini(prompt);
    }


    // ===========================================
    // CHAT
    // ===========================================
    public String conversar(String mensagem) throws Exception {

        String promptFinal = PROMPT_MOLDURA + "\n\n"
                + "Mensagem do usuário:\n"
                + mensagem;

        return enviarParaGemini(promptFinal);
    }


    // ===========================================
    // FUNÇÃO CENTRAL: ENVIA PARA A GEMINI
    // ===========================================
    private String enviarParaGemini(String texto) throws Exception {

        if (API_KEY == null || API_KEY.isBlank()) {
            throw new RuntimeException("API KEY da Gemini não configurada.");
        }

        // Corpo da requisição SÓ ACEITA ESTE FORMATO
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

        HttpResponse<String> resp = httpClient.send(req, HttpResponse.BodyHandlers.ofString());

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
            throw new RuntimeException("Resposta inesperada: " + resp.body());
        }

        return textNode.asText();
    }
}
