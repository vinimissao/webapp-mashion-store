package com.example.dao;

import com.example.Conexao.Conexao;
import com.example.Modelo.Cadastro;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CadastroDao {
    private final Connection connection;

    public CadastroDao() throws SQLException {
        Conexao cf = new Conexao();
        this.connection = cf.getConnection();
        criarTabela();
        verificarEstruturaTabela();
        testarConexao();
    }

    public CadastroDao(Connection connection) {
        this.connection = connection;
        criarTabela();
        verificarEstruturaTabela();
        testarConexao();
    }

    private void criarTabela() {
        String sql = """
        CREATE TABLE IF NOT EXISTS Cadastro (
            id INT AUTO_INCREMENT PRIMARY KEY,
            nome VARCHAR(255) NOT NULL,
            email VARCHAR(255) NOT NULL,
            logradouro VARCHAR(255),
            cidade VARCHAR(255),
            estado VARCHAR(50),
            bairro VARCHAR(255),
            numero INT,
            cep VARCHAR(20),
            data_Nasc DATE,
            telefone BIGINT,
            senha VARCHAR(255)
        );
        """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela 'Cadastro' criada ou verificada com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar tabela 'Cadastro': " + e.getMessage(), e);
        }
    }

    private void verificarEstruturaTabela() {
        adicionarColuna("logradouro", "VARCHAR(255)");
        adicionarColuna("cidade", "VARCHAR(255)");
        adicionarColuna("estado", "VARCHAR(50)");
        adicionarColuna("bairro", "VARCHAR(255)");
        adicionarColuna("numero", "INT");
        adicionarColuna("cep", "VARCHAR(20)");
    }

    private void adicionarColuna(String nomeColuna, String tipoColuna) {
        String sql = "ALTER TABLE Cadastro ADD COLUMN " + nomeColuna + " " + tipoColuna;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Coluna '" + nomeColuna + "' adicionada à tabela 'Cadastro'.");
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate column name")) {
                System.out.println("A coluna '" + nomeColuna + "' já existe.");
            } else {
                throw new RuntimeException("Erro ao adicionar coluna '" + nomeColuna + "': " + e.getMessage(), e);
            }
        }
    }

    public void adicionar(Cadastro cadastro) {
        String sql = "INSERT INTO cadastro (nome, email, logradouro, cidade, estado, bairro, numero, cep, data_nasc, telefone, senha) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cadastro.getNome());
            stmt.setString(2, cadastro.getEmail());
            stmt.setString(3, cadastro.getLogradouro());
            stmt.setString(4, cadastro.getCidade());
            stmt.setString(5, cadastro.getEstado());
            stmt.setString(6, cadastro.getBairro());
            stmt.setInt(7, cadastro.getNumero());
            stmt.setString(8, cadastro.getCep());
            stmt.setDate(9, new java.sql.Date(cadastro.getDataNasc().getTimeInMillis()));
            stmt.setLong(10, cadastro.getTelefone());
            stmt.setString(11, cadastro.getSenha());

            stmt.execute();
            System.out.println("Cadastro adicionado com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar cadastro: " + e.getMessage(), e);
        }
    }

    public void testarConexao() {
        try {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Conexão bem-sucedida!");
            } else {
                System.out.println("Falha na conexão.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao testar conexão: " + e.getMessage());
        }
    }
}
