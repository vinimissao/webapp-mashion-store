package com.example.dao;

import com.example.Conexao.Conexao;
import com.example.Modelo.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDao {

    private Connection connection;

    public ProdutoDao() throws SQLException {
        Conexao cf = new Conexao();
        this.connection = cf.getConnection();
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public void adicionarProduto(Produto produto) throws SQLException {
        String sql = "INSERT INTO produtos (nome, descricao, preco, estoque, imagem, data_cadastro) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getEstoque());
            stmt.setBytes(5, produto.getImagem());  // Armazenando a imagem como BLOB
            stmt.setTimestamp(6, produto.getDataCadastro() != null ? produto.getDataCadastro() : new Timestamp(System.currentTimeMillis()));
            stmt.executeUpdate();
        }
    }

    public void atualizarProduto(Produto produto) throws SQLException {
        String sql = "UPDATE produtos SET nome = ?, descricao = ?, preco = ?, estoque = ?, imagem = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getEstoque());
            stmt.setBytes(5, produto.getImagem());  // Atualizando a imagem como BLOB
            stmt.setInt(6, produto.getId());
            stmt.executeUpdate();
        }
    }

    public void deletarProduto(int id) throws SQLException {
        String sql = "DELETE FROM produtos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }


    public Produto buscarProdutoPorId(int id) throws SQLException {
        String sql = "SELECT * FROM produtos WHERE id = ?";
        Produto produto = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    produto = mapearProduto(rs);
                }
            }
        }
        return produto;
    }

    public List<Produto> listarProdutos() throws SQLException {
        String sql = "SELECT * FROM produtos";
        List<Produto> produtos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                produtos.add(mapearProduto(rs));
            }
        }
        return produtos;
    }

    private Produto mapearProduto(ResultSet rs) throws SQLException {
        Produto produto = new Produto();
        produto.setId(rs.getInt("id"));
        produto.setNome(rs.getString("nome"));
        produto.setDescricao(rs.getString("descricao"));
        produto.setPreco(rs.getDouble("preco"));
        produto.setEstoque(rs.getInt("estoque"));
        produto.setImagem(rs.getBytes("imagem"));  // Lendo o BLOB como byte[]
        produto.setDataCadastro(rs.getTimestamp("data_cadastro"));
        return produto;
    }


    public List<Object[]> relatorioResumidoVendasPorCliente() throws SQLException {
        String sql = """
                SELECT u.nome AS cliente, 
                       COUNT(p.id) AS total_pedidos, 
                       SUM(ip.quantidade * pr.preco) AS total_gasto
                FROM pedidos p
                JOIN clientes c ON p.cliente_id = c.id
                JOIN usuarios u ON c.usuario_id = u.id
                JOIN itens_pedido ip ON p.id = ip.pedido_id
                JOIN produtos pr ON ip.produto_id = pr.id
                WHERE DATE(p.data_pedido) = CURDATE()
                GROUP BY u.id, u.nome
                ORDER BY total_gasto DESC
                """;
        List<Object[]> relatorio = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                relatorio.add(new Object[]{
                        rs.getString("cliente"),
                        rs.getInt("total_pedidos"),
                        rs.getDouble("total_gasto")
                });
            }
        }
        return relatorio;
    }

    public List<Object[]> relatorioDetalhadoPorPedido(int pedidoId) throws SQLException {
        String sql = """
                SELECT p.id AS pedido_id,
                       u.nome AS cliente,
                       pr.nome AS produto,
                       ip.quantidade,
                       pr.preco,
                       (ip.quantidade * pr.preco) AS total_item,
                       p.data_pedido
                FROM pedidos p
                JOIN clientes c ON p.cliente_id = c.id
                JOIN usuarios u ON c.usuario_id = u.cpf
                JOIN itens_pedido ip ON p.id = ip.pedido_id
                JOIN produtos pr ON ip.produto_id = pr.id
                WHERE p.id = ?
                """;
        List<Object[]> relatorio = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pedidoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    relatorio.add(new Object[]{
                            rs.getInt("pedido_id"),
                            rs.getString("cliente"),
                            rs.getString("produto"),
                            rs.getInt("quantidade"),
                            rs.getDouble("preco"),
                            rs.getDouble("total_item"),
                            rs.getTimestamp("data_pedido")
                    });
                }
            }
        }
        return relatorio;
    }
}
