package br.com.fiap.beans;
import br.com.fiap.api.Endereco;


public class Colaborador {
    private int id;
    private String nome;
    private String dataNascimento;
    private String cpf;
    private String email;
    private int numero;
    private Endereco endereco;
    private String testesAtrelados;

    public Colaborador() {
    }

    public Colaborador(int id, String nome, String dataNascimento, String cpf, String email, int numero, Endereco endereco, String testesAtrelados) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.email = email;
        this.numero = numero;
        this.endereco = endereco;
        this.testesAtrelados = testesAtrelados;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getTestesAtrelados() {
        return testesAtrelados;
    }

    public void setTestesAtrelados(String testesAtrelados) {
        this.testesAtrelados = testesAtrelados;
    }

    @Override
    public String toString() {
        return "Colaborador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", dataNascimento='" + dataNascimento + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", numero=" + numero +
                ", endereco=" + endereco +
                ", testesAtrelados='" + testesAtrelados + '\'' +
                '}';
    }
}
