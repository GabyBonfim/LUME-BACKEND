package br.com.fiap.beans;

public class FeedbackTeste {

    private int id;
    private int idColaborador;
    private int idTeste;
    private String feedback;
    private String data;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdColaborador() { return idColaborador; }
    public void setIdColaborador(int idColaborador) { this.idColaborador = idColaborador; }

    public int getIdTeste() { return idTeste; }
    public void setIdTeste(int idTeste) { this.idTeste = idTeste; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
}
