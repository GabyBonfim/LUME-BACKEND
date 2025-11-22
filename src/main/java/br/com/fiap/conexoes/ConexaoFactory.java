package br.com.fiap.conexoes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {

    // 🔥 VARIÁVEL GLOBAL: se o BD cair, o sistema inteiro para de tentar conectar
    private static boolean bancoOffline = false;

    public static boolean isBancoOffline() {
        return bancoOffline;
    }

    // método para conexão com o banco de dados
    public Connection conexao() throws ClassNotFoundException, SQLException {

        // 🔥 SE O BANCO JÁ CAIU → NUNCA MAIS TENTA CONECTAR
        if (bancoOffline) {
            throw new SQLException("BANCO_OFFLINE");
        }

        try {
            // Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // tenta conectar normalmente
            return DriverManager.getConnection(
                    "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl",
                    "RM566242",
                    "160506"
            );

        } catch (SQLException erro) {

            // 🔥 SE DER ERRO DE PERMISSÃO OU BANCO FORA DO AR → BLOQUEIA DE VEZ
            bancoOffline = true;
            System.err.println(" BANCO MARCADO COMO OFFLINE: " + erro.getMessage());

            // devolve erro limpo para o serviço
            throw new SQLException("BANCO_OFFLINE");
        }
    }
}
