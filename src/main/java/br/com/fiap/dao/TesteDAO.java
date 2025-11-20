package br.com.fiap.dao;

import br.com.fiap.beans.Teste;
import br.com.fiap.conexoes.ConexaoFactory;

import java.sql.*;
import java.util.ArrayList;

public class TesteDAO {

    // =============================
    // INSERT
    // =============================
    public String inserir(Teste teste) throws SQLException, ClassNotFoundException {

        try (Connection conn = new ConexaoFactory().conexao()) {

            String sql = """
                INSERT INTO TESTE (TITULO, CONTEUDO, CRIADO_EM)
                VALUES (?, ?, CURRENT_TIMESTAMP)
            """;

            try (PreparedStatement stmt =
                         conn.prepareStatement(sql, new String[]{"ID"})) {

                stmt.setString(1, teste.getTitulo());
                stmt.setString(2, teste.getConteudo());

                stmt.executeUpdate();

                // pegar ID gerado
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        teste.setId(rs.getInt(1));
                    }
                }
            }
        }

        return "✅ Teste gerado e salvo com sucesso!";
    }


    // =============================
    // DELETE
    // =============================
    public String deletar(int id) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao()) {

            // 1) remover relações entre colaborador e teste
            String sqlRelacionamento = "DELETE FROM TESTE_ATRIBUIDO WHERE ID_TESTE = ?";
            try (PreparedStatement stmtRel = conn.prepareStatement(sqlRelacionamento)) {
                stmtRel.setInt(1, id);
                stmtRel.executeUpdate();
            }

            // 2) deletar teste
            String sql = "DELETE FROM TESTE WHERE ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        }

        return "🗑️ Teste deletado com sucesso!";
    }


    // =============================
    // UPDATE
    // =============================
    public String atualizar(Teste teste) throws SQLException, ClassNotFoundException {

        try (Connection conn = new ConexaoFactory().conexao()) {

            String sql = """
                UPDATE TESTE
                SET TITULO = ?, CONTEUDO = ?
                WHERE ID = ?
            """;

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, teste.getTitulo());
                stmt.setString(2, teste.getConteudo());
                stmt.setInt(3, teste.getId());
                stmt.executeUpdate();
            }
        }

        return "♻️ Teste atualizado com sucesso!";
    }


    // =============================
    // SELECT - listar todos testes
    // =============================
    public ArrayList<Teste> selecionar() throws SQLException, ClassNotFoundException {

        ArrayList<Teste> lista = new ArrayList<>();

        String sql = """
            SELECT ID, TITULO, CONTEUDO, CRIADO_EM
            FROM TESTE
            ORDER BY ID DESC
        """;

        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Teste t = new Teste();
                t.setId(rs.getInt("ID"));
                t.setTitulo(rs.getString("TITULO"));
                t.setConteudo(rs.getString("CONTEUDO"));
                t.setCriadoEm(rs.getString("CRIADO_EM"));

                lista.add(t);
            }
        }

        return lista;
    }


    // =======================================================
    // 🔵 NOVO: ADMIN — ATRIBUIR TESTE A UM COLABORADOR
    // =======================================================
    public String atribuirTeste(int idColaborador, int idTeste)
            throws SQLException, ClassNotFoundException {

        try (Connection conn = new ConexaoFactory().conexao()) {

            String sql = """
                INSERT INTO TESTE_ATRIBUIDO (ID_COLABORADOR, ID_TESTE, STATUS, DATA_ATRIBUIDO)
                VALUES (?, ?, 'PENDENTE', CURRENT_TIMESTAMP)
            """;

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idColaborador);
                stmt.setInt(2, idTeste);
                stmt.executeUpdate();
            }
        }

        return "📌 Teste atribuído ao colaborador!";
    }


    // =======================================================
    // 🔵 NOVO: ADMIN — LISTAR TESTES ATRIBUÍDOS A UM COLABORADOR
    // =======================================================
    public ArrayList<Teste> listarTestesDoColaborador(int idColaborador)
            throws SQLException, ClassNotFoundException {

        ArrayList<Teste> lista = new ArrayList<>();

        String sql = """
            SELECT t.ID, t.TITULO, t.CONTEUDO, t.CRIADO_EM
            FROM TESTE_ATRIBUIDO ta
            JOIN TESTE t ON t.ID = ta.ID_TESTE
            WHERE ta.ID_COLABORADOR = ?
            ORDER BY ta.DATA_ATRIBUIDO DESC
        """;

        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idColaborador);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Teste t = new Teste();
                    t.setId(rs.getInt("ID"));
                    t.setTitulo(rs.getString("TITULO"));
                    t.setConteudo(rs.getString("CONTEUDO"));
                    t.setCriadoEm(rs.getString("CRIADO_EM"));

                    lista.add(t);
                }
            }
        }

        return lista;
    }
}
