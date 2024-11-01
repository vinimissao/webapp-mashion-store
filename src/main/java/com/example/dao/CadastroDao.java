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
        String sql = "INSERT INTO usuarios (nome, email, logradouro, cidade, estado, bairro, numero, cep, telefone, data_nascimento, senha, is_admin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cadastro.getNome());
            stmt.setString(2, cadastro.getEmail());
            stmt.setString(3, cadastro.getLogradouro());
            stmt.setString(4, cadastro.getCidade());
            stmt.setString(5, cadastro.getEstado());
            stmt.setString(6, cadastro.getBairro());
            stmt.setInt(7, cadastro.getNumero());
            stmt.setString(8, cadastro.getCep());
            stmt.setString(9, cadastro.getTelefone());
            stmt.setDate(10, new java.sql.Date(cadastro.getDataNasc().getTimeInMillis()));
            stmt.setString(11, cadastro.getSenha());
            stmt.setBoolean(12, cadastro.isAdmin());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Retorna o ID gerado
            } else {
                throw new SQLException("Falha ao obter o ID gerado.");
            }
        }
    }

    public Cadastro buscarUsuarioPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE email = ?";
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
                        rs.getBoolean("is_admin")
                );

                Calendar dataNasc = Calendar.getInstance();
                dataNasc.setTime(rs.getDate("data_nascimento"));
                cadastro.setDataNasc(dataNasc);
            }
        }
        return cadastro; // Retorna o objeto Cadastro ou null se não encontrar
    }

    public void atualizarUsuario(Cadastro cadastro) throws SQLException {
        String sql = "UPDATE usuarios SET nome = ?, logradouro = ?, cidade = ?, estado = ?, bairro = ?, numero = ?, cep = ?, data_nascimento = ?, telefone = ?, senha = ?, is_admin = ? WHERE email = ?";

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
        String sql = "DELETE FROM usuarios WHERE email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.executeUpdate();
        }
    }

    public Cadastro validarUsuario(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
        Cadastro cadastro = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);
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
                        rs.getBoolean("is_admin")
                );

                Calendar dataNasc = Calendar.getInstance();
                dataNasc.setTime(rs.getDate("data_nascimento"));
                cadastro.setDataNasc(dataNasc);
            }
        }
        return cadastro; // Retorna o objeto Cadastro ou null
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
