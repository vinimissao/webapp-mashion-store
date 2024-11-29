package com.example.Modelo;
import java.sql.Timestamp;

import java.math.BigDecimal;


public class Pedido {
    private int id;
    private int cliente_id;
    private Timestamp dataPedido;
    private BigDecimal total;
    private String status;

    public Pedido(int id,int cliente_id,java.sql.Timestamp timestamp,BigDecimal total,String status){
        this.id = id;
        this.cliente_id = cliente_id;
        this.dataPedido = dataPedido;
        this.total = total;
        this.status = status;


    }

    public Pedido(int cliente_id,Timestamp dataPedido,BigDecimal total,String status){
        this.cliente_id = cliente_id;
        this.dataPedido = dataPedido;
        this.total = total;
        this.status = status;
    }

    public Pedido() {
        //TODO Auto-generated constructor stub
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return cliente_id;
    }

    public void setClienteId(int clienteId) {
        this.cliente_id = clienteId;
    }

    public java.sql.Timestamp getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Timestamp dataPedido) {
        this.dataPedido = dataPedido;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}