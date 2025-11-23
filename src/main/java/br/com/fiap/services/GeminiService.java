package br.com.fiap.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GeminiService {

    // Usa variável de ambiente, mas se não existir, usa uma key fixa
    private static final String API_KEY =
            System.getenv("GEMINI_KEY") != null ?
                    System.getenv("GEMINI_KEY") :
                    "AIzaSyADyaKK3Ve5f83w0U5d8F2QewpkaM3L_6I";

    private static final String GEMINI_URL =
            "https://generativelanguage.googleapis.com/v1/models/gemini-pro:generateContent?key=";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();


    // ============================================================
    // PROMPT BASE – Moldura para TODAS as respostas da LUM.IA
    // ============================================================
    private static final String PROMPT_MOLDURA = """
            Você é a LUM.IA, a inteligência da plataforma LUME, uma plataforma online que gera 
            testes que treinam soft skills de colaboradores. 
            
            Quando te perguntarem sobre quais testes temos disponíveis ou se você pode gerar,
            você deve questionar o nome e ID do colaborador, segmento da empresa, o setor 
            e uma função específica desse colaborador e gerar um teste com base nisso. 
            Os testes devem ser divertidos e práticos.

            IMPORTANTE:
            Sempre responda seguindo EXATAMENTE este formato, usando Markdown bonito:

            # 🎯 {Título Principal}

            ## 📌 Contexto
            {Explique em 2–4 linhas o cenário de forma clara, organizada e objetiva.}

            ## 🧩 O teste
            - Insight 1
            - Insight 2
            - Insight 3

            ## 🚀 Recomendações Práticas
            1. Ação 1
            2. Ação 2
            3. Ação 3

            ## 📊 Insight Final
            {Conclusão curta, direta e útil.}

            Mantenha:
            - clareza
            - espaçamentos
            - bullets
            - negritos
            - emojis corporativos discretos
            - visual padronizado e elegante da LUME.
            
            IMPORTANTE: 
            Quando você receber um teste já respondido por um colaborador, deve gerar uma 
            análise e solicitar que o colaborador a adicione na aba "minhas análises".
            
            Deve também guardar essa análise e quando o gestor/ADM solicitar,
            indicar com base no ID do colaborador seguido da análise gerada.
            """;


    // ============================================================
    // GERAR TESTE
    // ============================================================
    public String gerarConteudoTeste(String tema, int quantidade) throws Exception {

        String prompt = """
                Você é a LUM.IA, inteligência da plataforma LUME.
                Gere um teste sobre o tema: "%s".
                Crie exatamente %d questões objetivas (A, B, C, D).
                Destaque a alternativa correta com "**CORRETA:**".

                Use formatação em Markdown para manter visual profissional.
                """.formatted(tema, quantidade);

        return enviarParaGemini(prompt);
    }


    // ============================================================
    // CHAT — sempre usa a moldura
    // ============================================================
    public String conversar(String mensagem) throws Exception {

        String promptFinal = PROMPT_MOLDURA + "\n\n" +
                "Agora responda à mensagem do usuário abaixo usando esse formato:\n\n" +
                "Mensagem do Usuário:\n" +
                mensagem;

        return enviarParaGemini(promptFinal);
    }


    // ============================================================
    // FUNÇÃO CENTRAL — envia requisição para API Gemini
    // ============================================================
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
