package com.example.Modelo;
import java.sql.Timestamp;
public class Produto {
    private int id;
    private String nome;
    private String descricao;
    private double preco;
    private int estoque;
    private byte[] imagem;
    private Timestamp dataCadastro;

    // Construtor que aceita todos os parâmetros
    public Produto(int id, String nome, String descricao, double preco, int estoque, byte[] imagem, Timestamp dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.imagem = imagem;
        this.dataCadastro = dataCadastro;
    }

    public Produto() {

    }

    // Getters e setters
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public Timestamp getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Timestamp dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
