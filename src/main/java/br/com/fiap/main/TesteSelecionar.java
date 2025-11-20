package br.com.fiap.main;

import br.com.fiap.api.Endereco;
import br.com.fiap.beans.Colaborador;
import br.com.fiap.dao.ColaboradorDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TesteSelecionar {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        // Instanciar DAO
        ColaboradorDAO colaboradorDAO = new ColaboradorDAO();

        // Buscar lista de colaboradores
        List<Colaborador> lista = colaboradorDAO.selecionar();

        if (lista != null && !lista.isEmpty()) {

            for (Colaborador c : lista) {

                System.out.println("----------------------------------------");
                System.out.println("ID: " + c.getId());
                System.out.println("Nome: " + c.getNome());
                System.out.println("Data de Nascimento: " + c.getDataNascimento());
                System.out.println("CPF: " + c.getCpf());
                System.out.println("Email: " + c.getEmail());
                System.out.println("Número para Contato: " + c.getNumero());
                System.out.println("Testes Atrelados: " + c.getTestesAtrelados());

                Endereco end = c.getEndereco();
                if (end != null) {
                    System.out.println("---- Endereço ----");
                    System.out.println("CEP: " + end.getCep());
                    System.out.println("Logradouro: " + end.getLogradouro());
                    System.out.println("Complemento: " + end.getComplemento());
                    System.out.println("Bairro: " + end.getBairro());
                    System.out.println("Cidade: " + end.getLocalidade());
                    System.out.println("Estado: " + end.getEstado());
                    System.out.println("Região: " + end.getRegiao());
                }

                System.out.println("----------------------------------------\n");
            }
        } else {
            System.out.println("Nenhum colaborador encontrado no banco de dados.");
        }
    }
}
