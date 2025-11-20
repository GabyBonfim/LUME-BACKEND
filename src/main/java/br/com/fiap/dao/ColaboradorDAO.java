package br.com.fiap.dao;

import br.com.fiap.api.Endereco;
import br.com.fiap.beans.Colaborador;
import br.com.fiap.conexoes.ConexaoFactory;

import java.sql.*;
import java.util.ArrayList;

public class ColaboradorDAO {

    // =============================
    // INSERT
    // =============================
    public String inserir(Colaborador c) throws SQLException, ClassNotFoundException {
        Endereco e = c.getEndereco();
        int enderecoId = 0;

        try (Connection conn = new ConexaoFactory().conexao()) {

            // Inserir endereço primeiro
            if (e != null) {
                String sqlEnd = """
                    INSERT INTO ENDERECO (CEP, LOGRADOURO, COMPLEMENTO, BAIRRO, LOCALIDADE, ESTADO, REGIAO)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

                try (PreparedStatement stmtEnd =
                             conn.prepareStatement(sqlEnd, new String[]{"ID"})) {

                    stmtEnd.setString(1, e.getCep());
                    stmtEnd.setString(2, e.getLogradouro());
                    stmtEnd.setString(3, e.getComplemento());
                    stmtEnd.setString(4, e.getBairro());
                    stmtEnd.setString(5, e.getLocalidade());
                    stmtEnd.setString(6, e.getEstado());
                    stmtEnd.setString(7, e.getRegiao());
                    stmtEnd.executeUpdate();

                    try (ResultSet rs = stmtEnd.getGeneratedKeys()) {
                        if (rs.next()) {
                            enderecoId = rs.getInt(1);
                        }
                    }
                }
            }

            // Inserir colaborador vinculado ao endereço
            String sqlCol = """
                INSERT INTO COLABORADOR
                (NOME, DATANASCIMENTO, CPF, EMAIL, NUMERO, TESTES_ATRELADOS, ENDERECO_ID)
                VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

            try (PreparedStatement stmtCol = conn.prepareStatement(sqlCol)) {
                stmtCol.setString(1, c.getNome());
                stmtCol.setString(2, c.getDataNascimento());
                stmtCol.setString(3, c.getCpf());
                stmtCol.setString(4, c.getEmail());
                stmtCol.setInt(5, c.getNumero());
                stmtCol.setString(6, c.getTestesAtrelados());
                stmtCol.setInt(7, enderecoId);
                stmtCol.executeUpdate();
            }
        }

        return "Colaborador cadastrado com sucesso!";
    }

    // =============================
    // SELECT
    // =============================
    public ArrayList<Colaborador> selecionar() throws SQLException, ClassNotFoundException {
        ArrayList<Colaborador> lista = new ArrayList<>();

        String sql = """
            SELECT c.ID, c.NOME, c.DATANASCIMENTO, c.CPF, c.EMAIL, c.NUMERO, c.TESTES_ATRELADOS,
                   e.ID AS ENDERECO_ID, e.CEP, e.LOGRADOURO, e.COMPLEMENTO,
                   e.BAIRRO, e.LOCALIDADE, e.ESTADO, e.REGIAO
            FROM COLABORADOR c
            LEFT JOIN ENDERECO e ON c.ENDERECO_ID = e.ID
        """;

        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Colaborador c = new Colaborador();

                c.setId(rs.getInt("ID"));
                c.setNome(rs.getString("NOME"));
                c.setDataNascimento(rs.getString("DATANASCIMENTO"));
                c.setCpf(rs.getString("CPF"));
                c.setEmail(rs.getString("EMAIL"));
                c.setNumero(rs.getInt("NUMERO"));
                c.setTestesAtrelados(rs.getString("TESTES_ATRELADOS"));

                Endereco e = new Endereco();
                e.setCep(rs.getString("CEP"));
                e.setLogradouro(rs.getString("LOGRADOURO"));
                e.setComplemento(rs.getString("COMPLEMENTO"));
                e.setBairro(rs.getString("BAIRRO"));
                e.setLocalidade(rs.getString("LOCALIDADE"));
                e.setEstado(rs.getString("ESTADO"));
                e.setRegiao(rs.getString("REGIAO"));

                c.setEndereco(e);
                lista.add(c);
            }
        }

        return lista;
    }

    // =============================
    // UPDATE
    // =============================
    public String atualizar(Colaborador c) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao()) {

            // Atualizar colaborador
            String sqlCol = """
                UPDATE COLABORADOR
                SET NOME=?, DATANASCIMENTO=?, CPF=?, EMAIL=?, NUMERO=?, TESTES_ATRELADOS=?
                WHERE ID=?
            """;

            try (PreparedStatement stmt = conn.prepareStatement(sqlCol)) {
                stmt.setString(1, c.getNome());
                stmt.setString(2, c.getDataNascimento());
                stmt.setString(3, c.getCpf());
                stmt.setString(4, c.getEmail());
                stmt.setInt(5, c.getNumero());
                stmt.setString(6, c.getTestesAtrelados());
                stmt.setInt(7, c.getId());
                stmt.executeUpdate();
            }

            // Atualizar endereço
            if (c.getEndereco() != null) {

                String sqlEnd = """
                    UPDATE ENDERECO SET
                    CEP=?, LOGRADOURO=?, COMPLEMENTO=?, BAIRRO=?, LOCALIDADE=?, ESTADO=?, REGIAO=?
                    WHERE ID = (SELECT ENDERECO_ID FROM COLABORADOR WHERE ID=?)
                """;

                Endereco e = c.getEndereco();

                try (PreparedStatement stmtEnd = conn.prepareStatement(sqlEnd)) {
                    stmtEnd.setString(1, e.getCep());
                    stmtEnd.setString(2, e.getLogradouro());
                    stmtEnd.setString(3, e.getComplemento());
                    stmtEnd.setString(4, e.getBairro());
                    stmtEnd.setString(5, e.getLocalidade());
                    stmtEnd.setString(6, e.getEstado());
                    stmtEnd.setString(7, e.getRegiao());
                    stmtEnd.setInt(8, c.getId());
                    stmtEnd.executeUpdate();
                }
            }
        }

        return "Colaborador atualizado!";
    }

    // =============================
    // DELETE
    // =============================
    public String deletar(int id) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao()) {

            int enderecoId = 0;

            // pegar endereço vinculado
            String sqlEnd = "SELECT ENDERECO_ID FROM COLABORADOR WHERE ID=?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlEnd)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) enderecoId = rs.getInt(1);
            }

            // deletar colaborador
            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM COLABORADOR WHERE ID=?")) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            // deletar endereço também
            if (enderecoId > 0) {
                try (PreparedStatement stmt =
                             conn.prepareStatement("DELETE FROM ENDERECO WHERE ID=?")) {
                    stmt.setInt(1, enderecoId);
                    stmt.executeUpdate();
                }
            }
        }

        return "Colaborador e endereço deletados.";
    }
}
