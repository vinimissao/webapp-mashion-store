package com.example.dao;

import com.example.Modelo.Cadastro;
import java.sql.*;

public class CadastroDao {

    private Connection connection;

    public CadastroDao(Connection connection) {
        this.connection = connection;
    }

    // Valida o login do usuário
    public Cadastro validarUsuario(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Cadastro(rs.getInt("id"), rs.getString("email"),rs.getString("senha"),sql, rs.getBoolean("is_admin"));
            }
        }
        return null;
    }

    // Cadastra um novo usuário
    public void cadastrarUsuario(Cadastro usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (email, senha, is_admin) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getSenha());
            stmt.setBoolean(3, usuario.isAdmin());
            stmt.executeUpdate();
        }
    }

    // Atualiza um usuário
    public void atualizarUsuario(Cadastro usuario) throws SQLException {
        String sql = "UPDATE usuarios SET email = ?, senha = ?, is_admin = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getSenha());
            stmt.setBoolean(3, usuario.isAdmin());
            stmt.setInt(4, usuario.getId());
            stmt.executeUpdate();
        }
    }

    // Exclui um usuário
    public void excluirUsuario(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
