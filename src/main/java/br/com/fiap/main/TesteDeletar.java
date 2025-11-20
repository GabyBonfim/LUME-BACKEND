package br.com.fiap.main;

import br.com.fiap.beans.Colaborador;
import br.com.fiap.dao.ColaboradorDAO;

import javax.swing.*;
import java.sql.SQLException;

public class TesteDeletar {

    // int
    static int inteiro(String j){
        return  Integer.parseInt(JOptionPane.showInputDialog(j));
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        ColaboradorDAO colaboradorDAO = new ColaboradorDAO();

        Colaborador objColaborador = new Colaborador();

        objColaborador.setId(inteiro("Informe o ID do colaborador para ser deletado"));

        System.out.println(colaboradorDAO.deletar(objColaborador.getId()));



    }
}
