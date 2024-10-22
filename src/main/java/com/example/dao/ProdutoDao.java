package com.example.dao;
import com.example.Conexao.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.example.Modelo.Produto;
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

    public void adicionarProduto(Produto produto) throws SQLException {
        String sql = "INSERT INTO produtos (nome, descricao, preco, estoque, imagem, data_cadastro) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, produto.getNome());
        stmt.setString(2, produto.getDescricao());
        stmt.setDouble(3, produto.getPreco());
        stmt.setInt(4, produto.getEstoque());
        stmt.setBytes(5, produto.getImagem());  // Armazenando a imagem como BLOB
        stmt.setTimestamp(6, produto.getDataCadastro());
        stmt.executeUpdate();
        stmt.close();
    }

    public void atualizarProduto(Produto produto) throws SQLException {
        String sql = "UPDATE produtos SET nome = ?, descricao = ?, preco = ?, estoque = ?, imagem = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, produto.getNome());
        stmt.setString(2, produto.getDescricao());
        stmt.setDouble(3, produto.getPreco());
        stmt.setInt(4, produto.getEstoque());
        stmt.setBytes(5, produto.getImagem());  // Atualizando a imagem como BLOB
        stmt.setInt(6, produto.getId());
        stmt.executeUpdate();
        stmt.close();
    }

    public void deletarProduto(int id) throws SQLException {
        String sql = "DELETE FROM produtos WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
    }

    public Produto buscarProdutoPorId(int id) throws SQLException {
        String sql = "SELECT * FROM produtos WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        Produto produto = null;
        if (rs.next()) {
            produto = new Produto();
            produto.setId(rs.getInt("id"));
            produto.setNome(rs.getString("nome"));
            produto.setDescricao(rs.getString("descricao"));
            produto.setPreco(rs.getDouble("preco"));
            produto.setEstoque(rs.getInt("estoque"));
            produto.setImagem(rs.getBytes("imagem"));  // Lendo o BLOB como byte[]
            produto.setDataCadastro(rs.getTimestamp("data_cadastro"));
        }
        rs.close();
        stmt.close();
        return produto;
    }

    public List<Produto> listarProdutos() throws SQLException {
        String sql = "SELECT * FROM produtos";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        List<Produto> produtos = new ArrayList<>();
        while (rs.next()) {
            Produto produto = new Produto();
            produto.setId(rs.getInt("id"));
            produto.setNome(rs.getString("nome"));
            produto.setDescricao(rs.getString("descricao"));
            produto.setPreco(rs.getDouble("preco"));
            produto.setEstoque(rs.getInt("estoque"));
            produto.setImagem(rs.getBytes("imagem"));  // Lendo o BLOB como byte[]
            produto.setDataCadastro(rs.getTimestamp("data_cadastro"));
            produtos.add(produto);
        }
        rs.close();
        stmt.close();
        return produtos;
    }
}
