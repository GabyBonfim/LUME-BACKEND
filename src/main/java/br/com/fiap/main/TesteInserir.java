package br.com.fiap.main;

import br.com.fiap.api.Endereco;
import br.com.fiap.beans.Colaborador;
import br.com.fiap.dao.ColaboradorDAO;

import javax.swing.*;
import java.sql.SQLException;

public class TesteInserir {

    // Entrada de texto
    static String texto(String j) {
        return JOptionPane.showInputDialog(j);
    }

    // Entrada de número
    static int inteiro(String j) {
        return Integer.parseInt(JOptionPane.showInputDialog(j));
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        // DAO e BEAN
        ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
        Colaborador colaborador = new Colaborador();

        // Dados do colaborador
        colaborador.setNome(texto("Nome do colaborador:"));
        colaborador.setDataNascimento(texto("Data de nascimento (DD/MM/AAAA):"));
        colaborador.setCpf(texto("CPF:"));
        colaborador.setEmail(texto("Email:"));
        colaborador.setNumero(inteiro("Número para contato:"));
        colaborador.setTestesAtrelados(texto("Testes atrelados (IDs separados por vírgula):"));

        // Endereço
        Endereco endereco = new Endereco();
        endereco.setCep(texto("CEP:"));
        endereco.setLogradouro(texto("Logradouro:"));
        endereco.setComplemento(texto("Complemento:"));
        endereco.setBairro(texto("Bairro:"));
        endereco.setLocalidade(texto("Cidade:"));
        endereco.setEstado(texto("Estado (UF):"));
        endereco.setRegiao(texto("Região:"));

        colaborador.setEndereco(endereco);

        // Inserir no banco
        System.out.println(colaboradorDAO.inserir(colaborador));
    }
}
