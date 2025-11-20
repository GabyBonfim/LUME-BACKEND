package br.com.fiap.dao;

import br.com.fiap.beans.FeedbackTeste;
import br.com.fiap.conexoes.ConexaoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {

    public String registrar(FeedbackTeste fb) throws SQLException, ClassNotFoundException {

        Connection conn = new ConexaoFactory().conexao();

        String sql = """
            INSERT INTO FEEDBACK_TESTE (ID_COLABORADOR, ID_TESTE, FEEDBACK, DATA)
            VALUES (?, ?, ?, CURRENT_TIMESTAMP)
        """;

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, fb.getIdColaborador());
        stmt.setInt(2, fb.getIdTeste());
        stmt.setString(3, fb.getFeedback());

        stmt.executeUpdate();
        stmt.close();
        conn.close();

        return "Feedback registrado.";
    }

    public List<FeedbackTeste> listarPorColaborador(int id)
            throws SQLException, ClassNotFoundException {

        Connection conn = new ConexaoFactory().conexao();
        List<FeedbackTeste> lista = new ArrayList<>();

        String sql = """
            SELECT * FROM FEEDBACK_TESTE
            WHERE ID_COLABORADOR = ?
        """;

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            FeedbackTeste f = new FeedbackTeste();
            f.setId(rs.getInt("ID"));
            f.setIdColaborador(rs.getInt("ID_COLABORADOR"));
            f.setIdTeste(rs.getInt("ID_TESTE"));
            f.setFeedback(rs.getString("FEEDBACK"));
            f.setData(rs.getString("DATA"));
            lista.add(f);
        }

        return lista;
    }
}
