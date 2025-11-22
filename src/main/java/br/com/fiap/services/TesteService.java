package br.com.fiap.services;

import br.com.fiap.beans.Teste;
import br.com.fiap.dao.TesteDAO;

import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Camada de serviço: regra de negócio para Testes.
 * Não acessa HTTP nem JDBC direto, só usa outros serviços/DAOs.
 */
public class TesteService {

    private final GeminiService geminiService = new GeminiService();
    private final TesteDAO testeDAO = new TesteDAO();

    /**
     * Gera um teste usando a Gemini e salva no banco.
     */
    public Teste gerarTeste(String tema, int quantidade) throws Exception {

        // 1) IA gera o texto do teste
        String conteudoGerado = geminiService.gerarConteudoTeste(tema, quantidade);

        // 2) Monta o bean
        Teste teste = new Teste();
        teste.setTitulo("Teste: " + tema);
        teste.setConteudo(conteudoGerado);

        // 3) Salva no banco (o DAO vai preencher o ID)
        testeDAO.inserir(teste);

        return teste;
    }

    /**
     * Lista todos os testes cadastrados.
     */
    public ArrayList<Teste> listarTodos() throws SQLException, ClassNotFoundException {
        return testeDAO.selecionar();
    }

    /**
     * Atualiza um teste existente.
     */
    public String atualizar(Teste teste) throws SQLException, ClassNotFoundException {
        return testeDAO.atualizar(teste);
    }

    /**
     * Deleta um teste pelo ID.
     */
    public String deletar(int id) throws SQLException, ClassNotFoundException {
        return testeDAO.deletar(id);
    }

    public Teste buscarPorId(int id) throws SQLException, ClassNotFoundException {
        return testeDAO.buscarPorId(id);
    }

}
