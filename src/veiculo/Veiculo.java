package veiculo;

import java.io.Serializable;
import cliente.Cliente;

public class Veiculo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idVeiculo;
    private Cliente cliente;
    private String placa;
    private String modelo;
    private String marca;
    private int ano;
    private String cor;

    // Construtor padrão
    public Veiculo() {
    }

    // Construtor com Cliente
    public Veiculo(Cliente cliente, String placa, String modelo, String marca, int ano, String cor) {
        this.cliente = cliente;
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.cor = cor;
    }

    // Construtor alternativo (sem cliente)
    public Veiculo(String placa, String modelo, String marca, int ano, String cor) {
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.cor = cor;
    }

    // Getters e Setters
    public int getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void verVeiculo() {
        System.out.println("=== Dados do Veículo ===");
        System.out.println("ID: " + idVeiculo);
        System.out.println("Placa: " + placa);
        System.out.println("Modelo: " + modelo);
        System.out.println("Marca: " + marca);
        System.out.println("Ano: " + ano);
        System.out.println("Cor: " + cor);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Veiculo{" +
                "idVeiculo=" + idVeiculo +
                ", cliente=" + (cliente != null ? cliente.getCpf() : "null") +
                ", placa='" + placa + '\'' +
                ", modelo='" + modelo + '\'' +
                ", marca='" + marca + '\'' +
                ", ano=" + ano +
                ", cor='" + cor + '\'' +
                '}';
    }
}