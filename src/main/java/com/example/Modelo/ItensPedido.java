package com.example.Modelo;

import java.math.BigDecimal;

public class ItensPedido {
    private int id;
    private int pedidoId;
    private int produtoId;
    private int quantidade;
    private BigDecimal preco;

    public ItensPedido(int id, int pedidoId, int produtoId, int quantidade, BigDecimal preco) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public ItensPedido(int pedidoId2, int produtoId2, int quantidade2, BigDecimal preco2) {
        //TODO Auto-generated constructor stub
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }

    public int getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
