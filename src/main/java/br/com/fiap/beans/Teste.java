package br.com.fiap.beans;

public class Teste {

    private int id;
    private String titulo;
    private String conteudo;
    private String criadoEm;

    public Teste() {
    }

    public Teste(int id, String titulo, String conteudo, String criadoEm) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.criadoEm = criadoEm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(String criadoEm) {
        this.criadoEm = criadoEm;
    }

    @Override
    public String toString() {
        return "Teste{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", conteudo='" + conteudo + '\'' +
                ", criadoEm='" + criadoEm + '\'' +
                '}';
    }
}
