package com.example.dao;

import com.example.Modelo.Pedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDao {

    private Connection connection;

    public PedidoDao(Connection connection) {
        this.connection = connection;
    }

    // Cadastra um novo pedido
    public void salvar(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO pedidos (cliente_id, data_pedido, total, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getClienteId());
            stmt.setTimestamp(2, pedido.getDataPedido());
            stmt.setBigDecimal(3, pedido.getTotal());
            stmt.setString(4, pedido.getStatus());
            stmt.executeUpdate();
        }
    }

    // Lista todos os pedidos
    public List<Pedido> listarTodos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pedido pedido = new Pedido(
                        rs.getInt("id"),
                        rs.getInt("cliente_id"),
                        rs.getTimestamp("data_pedido"),
                        rs.getBigDecimal("total"),
                        rs.getString("status")
                );
                pedidos.add(pedido);
            }
        }
        return pedidos;
    }

    // Busca um pedido pelo ID
    public Pedido buscarPedido(int id) throws SQLException {
        String sql = "SELECT * FROM pedidos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Pedido(
                        rs.getInt("id"),
                        rs.getInt("cliente_id"),
                        rs.getTimestamp("data_pedido"),
                        rs.getBigDecimal("total"),
                        rs.getString("status")
                );
            }
        }
        return null;
    }

    // Atualiza um pedido
    public void atualizar(Pedido pedido) throws SQLException {
        String sql = "UPDATE pedidos SET cliente_id = ?, data_pedido = ?, total = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getClienteId());
            stmt.setTimestamp(2, pedido.getDataPedido());
            stmt.setBigDecimal(3, pedido.getTotal());
            stmt.setString(4, pedido.getStatus());
            stmt.setInt(5, pedido.getId());
            stmt.executeUpdate();
        }
    }

    // Deleta um pedido
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM pedidos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
