package com.example.dao;

import com.example.Modelo.Carrinho;
import com.example.Modelo.ItemCarrinho;
import com.example.Modelo.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoDao {
    private Connection connection;

    public CarrinhoDao(Connection connection) {
        this.connection = connection;
    }

    // Adiciona um item ao carrinho
    public void adicionarItemCarrinho(Carrinho carrinho, ItemCarrinho itemCarrinho) throws SQLException {
        String sql = "INSERT INTO itens_carrinho (carrinho_id, produto_id, quantidade) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, carrinho.getId());
            stmt.setInt(2, itemCarrinho.getProduto().getId());
            stmt.setInt(3, itemCarrinho.getQuantidade());
            stmt.executeUpdate();
        }
    }

    // Lista os itens de um carrinho
    public List<ItemCarrinho> listarItensCarrinho(int carrinhoId) throws SQLException {
        List<ItemCarrinho> itens = new ArrayList<>();
        String sql = "SELECT * FROM itens_carrinho WHERE carrinho_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, carrinhoId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int produtoId = rs.getInt("produto_id");
                Produto produto = new Produto(produtoId, sql, sql, null, produtoId); // Supondo que você tem um método para buscar o produto
                ItemCarrinho item = new ItemCarrinho(produto, rs.getInt("quantidade"));
                itens.add(item);
            }
        }
        return itens;
    }

    // Remove um item do carrinho
    public void removerItemCarrinho(int itemId) throws SQLException {
        String sql = "DELETE FROM itens_carrinho WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, itemId);
            stmt.executeUpdate();
        }
    }

    // Cria ou recupera o carrinho
    public Carrinho getCarrinho(int carrinhoId) throws SQLException {
        Carrinho carrinho = new Carrinho(carrinhoId);
        carrinho.setItens(listarItensCarrinho(carrinhoId));
        return carrinho;
    }
}
