package br.com.fiap.bo;

import br.com.fiap.beans.Colaborador;
import br.com.fiap.dao.ColaboradorDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ColaboradorBO {

    private ColaboradorDAO colaboradorDAO = new ColaboradorDAO();

    public ArrayList<Colaborador> selecionarBo() throws SQLException, ClassNotFoundException {
        return (ArrayList<Colaborador>) colaboradorDAO.selecionar();
    }

    public void inserirBO(Colaborador colaborador) throws SQLException, ClassNotFoundException {
        colaboradorDAO.inserir(colaborador);
    }

    public void attBo(Colaborador colaborador) throws SQLException, ClassNotFoundException {
        colaboradorDAO.atualizar(colaborador);
    }

    public void deletarBo(int id) throws SQLException, ClassNotFoundException {
        colaboradorDAO.deletar(id);
    }
}
