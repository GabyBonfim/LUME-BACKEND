package br.com.fiap.main;

import br.com.fiap.api.Endereco;
import br.com.fiap.beans.Colaborador;
import br.com.fiap.dao.ColaboradorDAO;

import javax.swing.*;
import java.sql.SQLException;

public class TesteAtualizar {

    // Entrada de texto
    static String texto(String j) {
        return JOptionPane.showInputDialog(j);
    }

    // Entrada de número inteiro
    static int inteiro(String j) {
        return Integer.parseInt(JOptionPane.showInputDialog(j));
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        // OBJETO COLABORADOR
        Colaborador colaborador = new Colaborador();

        // DAO
        ColaboradorDAO colaboradorDAO = new ColaboradorDAO();

        // --- CAPTURA DE DADOS ---

        colaborador.setId(inteiro("Informe o ID do colaborador a ser atualizado:"));
        colaborador.setNome(texto("Atualize o nome:"));
        colaborador.setDataNascimento(texto("Data de nascimento (DD/MM/AAAA):"));
        colaborador.setCpf(texto("CPF:"));
        colaborador.setEmail(texto("Email:"));
        colaborador.setNumero(inteiro("Número de contato:"));
        colaborador.setTestesAtrelados(texto("Testes atrelados (IDs separados por vírgula):"));

        // --- ENDEREÇO ---
        Endereco endereco = new Endereco();
        endereco.setCep(texto("CEP:"));
        endereco.setLogradouro(texto("Logradouro:"));
        endereco.setComplemento(texto("Complemento:"));
        endereco.setBairro(texto("Bairro:"));
        endereco.setLocalidade(texto("Cidade:"));
        endereco.setEstado(texto("Estado (UF):"));
        endereco.setRegiao(texto("Região:"));

        colaborador.setEndereco(endereco);

        // --- ATUALIZAÇÃO ---
        System.out.println(colaboradorDAO.atualizar(colaborador));
    }
}
