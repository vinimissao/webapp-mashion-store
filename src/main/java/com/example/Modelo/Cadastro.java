package com.example.Modelo;

import java.util.Calendar;

public class Cadastro {
    private String nome;
    private String email;
    private String logradouro;
    private String cidade;
    private String estado;
    private String bairro;
    private int numero;
    private String cep;
    private Calendar dataNasc;
    private long telefone;
    private String senha; // Alterado para String

    // Construtor completo com todos os campos
    public Cadastro(String nome, String email, String logradouro, String cidade, String estado,
                    String bairro, int numero, String cep, Calendar dataNasc, long telefone, String senha) {
        this.nome = nome;
        this.email = email;
        this.logradouro = logradouro;
        this.cidade = cidade;
        this.estado = estado;
        this.bairro = bairro;
        this.numero = numero;
        this.cep = cep;
        this.dataNasc = dataNasc;
        this.telefone = telefone;
        this.senha = senha;
    }

    // Construtor apenas para Endereço (usado no CepService)
    public Cadastro(String logradouro, String cidade, String estado, String bairro, String cep) {
        this.logradouro = logradouro;
        this.cidade = cidade;
        this.estado = estado;
        this.bairro = bairro;
        this.cep = cep;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Calendar getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Calendar dataNasc) {
        this.dataNasc = dataNasc;
    }

    public long getTelefone() {
        return telefone;
    }

    public void setTelefone(long telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
