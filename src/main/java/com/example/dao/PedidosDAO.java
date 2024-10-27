package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.example.Modelo.Pedido;

public class PedidosDAO {
    private Connection connection;

    
    public PedidosDAO(Connection connection) {
        this.connection = connection;
    }

   
    public boolean salvar(Pedido pedido) {
        String sql = "INSERT INTO pedidos (cliente_id, data_pedido, total, status_pedido) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, pedido.getClienteId());
            stmt.setTimestamp(2, pedido.getDataPedido());
            stmt.setBigDecimal(3, pedido.getTotal());
            stmt.setString(4, pedido.getStatus());
            stmt.executeUpdate();

        
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                pedido.setId(generatedKeys.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao salvar pedido: " + e.getMessage());
            return false;
        }
    }

   
    public boolean atualizar(Pedido pedido) {
        String sql = "UPDATE pedidos SET cliente_id = ?, data_pedido = ?, total = ?, status_pedido = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getClienteId());
            stmt.setTimestamp(2, pedido.getDataPedido());
            stmt.setBigDecimal(3, pedido.getTotal());
            stmt.setString(4, pedido.getStatus());
            stmt.setInt(5, pedido.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o pedido: " + e.getMessage());
            return false;
        }
    }

    
    public Pedido buscarPedido(int id) {
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
                    rs.getString("status_pedido")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar o pedido: " + e.getMessage());
        }
        return null;
    }

  
    public List<Pedido> listarTodos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Pedido pedido = new Pedido(
                    rs.getInt("id"),
                    rs.getInt("cliente_id"),
                    rs.getTimestamp("data_pedido"),
                    rs.getBigDecimal("total"),
                    rs.getString("status_pedido")
                );
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar pedidos: " + e.getMessage());
        }
        return pedidos;
    }

  
    public boolean deletar(int id) {
        String sql = "DELETE FROM pedidos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar o pedido: " + e.getMessage());
            return false;
        }
    }
}
