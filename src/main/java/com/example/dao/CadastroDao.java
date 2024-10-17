package com.example.dao;

import com.example.Conexao.Conexao;
import com.example.Modelo.Cadastro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class CadastroDao {

    private Connection connection;

    public CadastroDao() throws SQLException {
        Conexao cf = new Conexao();
        this.connection = cf.getConnection();
    }

    public int adicionarUsuario(Cadastro cadastro) throws SQLException {
        String sql = "INSERT INTO usuarios (nome, email, telefone, data_nascimento, senha, is_admin) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cadastro.getNome());
            stmt.setString(2, cadastro.getEmail());
            stmt.setString(3, cadastro.getTelefone());
            stmt.setDate(4, new java.sql.Date(cadastro.getDataNasc().getTimeInMillis()));
            stmt.setString(5, cadastro.getSenha());
            stmt.setBoolean(6, cadastro.isAdmin());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Retorna o ID gerado
            } else {
                throw new SQLException("Falha ao obter o ID gerado.");
            }
        }
    }

    public void adicionarAdministrador(int usuarioId, Cadastro cadastro) throws SQLException {
        String sql = "INSERT INTO administradores (usuario_id) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.executeUpdate();
        }
    }

    public void adicionarCliente(int usuarioId, Cadastro cadastro) throws SQLException {
        String sql = "INSERT INTO clientes (usuario_id, logradouro, numero, bairro, cidade, estado, cep) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setString(2, cadastro.getLogradouro());
            stmt.setInt(3, cadastro.getNumero());
            stmt.setString(4, cadastro.getBairro());
            stmt.setString(5, cadastro.getCidade());
            stmt.setString(6, cadastro.getEstado());
            stmt.setString(7, cadastro.getCep());
            stmt.executeUpdate();
        }
    }


    public Cadastro buscarUsuarioPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Cadastro WHERE email = ?";
        Cadastro cadastro = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cadastro = new Cadastro(
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("logradouro"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("bairro"),
                        rs.getInt("numero"),
                        rs.getString("cep"),
                        null, // dataNasc será setada depois
                        rs.getString("telefone"),
                        rs.getString("senha"),
                        rs.getBoolean("isAdmin")
                );

                Calendar dataNasc = Calendar.getInstance();
                dataNasc.setTime(rs.getDate("data_Nasc"));
                cadastro.setDataNasc(dataNasc);
            }
        }
        return cadastro;
    }

    public void atualizarUsuario(Cadastro cadastro) throws SQLException {
        String sql = "UPDATE Cadastro SET nome = ?, logradouro = ?, cidade = ?, estado = ?, bairro = ?, numero = ?, cep = ?, data_Nasc = ?, telefone = ?, senha = ?, isAdmin = ? WHERE email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cadastro.getNome());
            stmt.setString(2, cadastro.getLogradouro());
            stmt.setString(3, cadastro.getCidade());
            stmt.setString(4, cadastro.getEstado());
            stmt.setString(5, cadastro.getBairro());
            stmt.setInt(6, cadastro.getNumero());
            stmt.setString(7, cadastro.getCep());
            stmt.setDate(8, new java.sql.Date(cadastro.getDataNasc().getTimeInMillis()));
            stmt.setString(9, cadastro.getTelefone());
            stmt.setString(10, cadastro.getSenha());
            stmt.setBoolean(11, cadastro.isAdmin());
            stmt.setString(12, cadastro.getEmail());

            stmt.executeUpdate();
        }
    }

    public void deletarUsuario(String email) throws SQLException {
        String sql = "DELETE FROM Cadastro WHERE email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.executeUpdate();
        }
    }

    public boolean validarUsuario(String email, String senha) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE email = ? AND senha = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public boolean isAdmin(String email) throws SQLException {
        String sql = "SELECT is_admin FROM usuarios WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("is_admin");
            }
            return false;
        }
    }

}
