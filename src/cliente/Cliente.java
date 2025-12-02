package com.oficina.model;
import java.util.List;

public class Cliente {

    private int idCliente;
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;
    private String email;

    // Construtor
    public Cliente(int idCliente, String nome, String cpf, String telefone, String endereco, String email) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
    }

    // Getters e Setters
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Métodos
    public void criarCliente(String nome, String cpf, String telefone, String endereco, String email) {
       
    }

    public void verCliente() {
       
    }

    public void editarCliente(String nome, String cpf, String telefone, String endereco, String email) {
       
    }

    public void excluirCliente(Cliente cliente) {
       
    }

    public List<Cliente> listarClientes() {
       
        return null; 
    }

    public List<OrdemServico> consultarHistorico() {
        
        return null; 
    }

    public Cliente buscarCliente(String cpf) {
       
        return null;
    }
}