package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.example.Modelo.ItensPedido;

public class ItensPedidoDAO {
    private Connection connection;

    public ItensPedidoDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean salvar(ItensPedido item) {
        String sql = "INSERT INTO itens_pedido (pedido_id, produto_id, quantidade, preco) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, item.getPedidoId());
            stmt.setInt(2, item.getProdutoId());
            stmt.setInt(3, item.getQuantidade());
            stmt.setBigDecimal(4, item.getPreco());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao salvar item do pedido: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizar(ItensPedido item) {
        String sql = "UPDATE itens_pedido SET pedido_id = ?, produto_id = ?, quantidade = ?, preco = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, item.getPedidoId());
            stmt.setInt(2, item.getProdutoId());
            stmt.setInt(3, item.getQuantidade());
            stmt.setBigDecimal(4, item.getPreco());
            stmt.setInt(5, item.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar item do pedido: " + e.getMessage());
            return false;
        }
    }

    public ItensPedido buscarItem(int id) {
        String sql = "SELECT * FROM itens_pedido WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ItensPedido(
                        rs.getInt("id"),
                        rs.getInt("pedido_id"),
                        rs.getInt("produto_id"),
                        rs.getInt("quantidade"),
                        rs.getBigDecimal("preco"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar item do pedido: " + e.getMessage());
        }
        return null;
    }

    public List<ItensPedido> listarTodos() {
        List<ItensPedido> itens = new ArrayList<>();
        String sql = "SELECT * FROM itens_pedido";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ItensPedido item = new ItensPedido(
                        rs.getInt("id"),
                        rs.getInt("pedido_id"),
                        rs.getInt("produto_id"),
                        rs.getInt("quantidade"),
                        rs.getBigDecimal("preco"));
                itens.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar itens do pedido: " + e.getMessage());
        }
        return itens;
    }

    public boolean deletar(int id){
        String sql = "DELETE FROM itens_pedido WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("Erro ao deletar item pedido: " + e.getMessage());
            return false;
        }
    }
}
