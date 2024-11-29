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

    public String adicionarUsuario(Cadastro cadastro) throws SQLException {
        // SQL para inserir na tabela usuarios
        String sqlUsuario = "INSERT INTO usuarios (cpf, nome, email, logradouro, cidade, estado, bairro, numero, cep, telefone, data_nasc, senha, is_admin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // SQL para inserir na tabela clientes (caso não seja admin)
        String sqlCliente = "INSERT INTO clientes (usuario_id) VALUES (?)";

        // SQL para inserir na tabela administradores (caso seja admin)
        String sqlAdministrador = "INSERT INTO administradores (usuario_id) VALUES (?)";

        try (PreparedStatement stmtUsuario = connection.prepareStatement(sqlUsuario, PreparedStatement.RETURN_GENERATED_KEYS)) {
            // Inserir dados na tabela usuarios
            stmtUsuario.setString(1, cadastro.getCpf());
            stmtUsuario.setString(2, cadastro.getNome());
            stmtUsuario.setString(3, cadastro.getEmail());
            stmtUsuario.setString(4, cadastro.getLogradouro());
            stmtUsuario.setString(5, cadastro.getCidade());
            stmtUsuario.setString(6, cadastro.getEstado());
            stmtUsuario.setString(7, cadastro.getBairro());
            stmtUsuario.setInt(8, cadastro.getNumero());
            stmtUsuario.setString(9, cadastro.getCep());
            stmtUsuario.setString(10, cadastro.getTelefone());
            stmtUsuario.setDate(11, new java.sql.Date(cadastro.getDataNasc().getTimeInMillis()));
            stmtUsuario.setString(12, cadastro.getSenha());
            stmtUsuario.setBoolean(13, cadastro.isAdmin());
            stmtUsuario.executeUpdate();

            String cpfInserido = cadastro.getCpf();

            if (cadastro.isAdmin()) {
                try (PreparedStatement stmtAdmin = connection.prepareStatement(sqlAdministrador)) {
                    stmtAdmin.setString(1, cpfInserido);
                    stmtAdmin.executeUpdate();
                }
            } else {
                try (PreparedStatement stmtCli = connection.prepareStatement(sqlCliente)) {
                    stmtCli.setString(1, cpfInserido);
                    stmtCli.executeUpdate();
                }
            }

            return cpfInserido;
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir o usuário: " + e.getMessage(), e);
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
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("logradouro"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("bairro"),
                        rs.getInt("numero"),
                        rs.getString("cep"),
                        null,
                        rs.getString("telefone"),
                        rs.getString("senha"),
                        rs.getBoolean("is_admin")
                );

                Calendar dataNasc = Calendar.getInstance();
                dataNasc.setTime(rs.getDate("data_nasc"));
                cadastro.setDataNasc(dataNasc);
            }
        }
        return cadastro;
    }

    public void atualizarUsuario(Cadastro cadastro) throws SQLException {
        String sql = "UPDATE usuarios SET nome = ?, logradouro = ?, cidade = ?, estado = ?, bairro = ?, numero = ?, cep = ?, data_nasc = ?, telefone = ?, senha = ?, is_admin = ? WHERE email = ?";

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
                        rs.getString("cpf"),  // Adicionando CPF
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
                dataNasc.setTime(rs.getDate("data_nasc"));
                cadastro.setDataNasc(dataNasc);
            }
        }
        return cadastro;
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
