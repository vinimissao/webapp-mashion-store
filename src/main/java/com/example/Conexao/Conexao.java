package com.example.Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public Connection getConnection() throws SQLException {
        System.out.println("Conectando ao banco de dados...");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/mashionstore", "root", "Coruja07");
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
    }

    public static void main(String[] args) {
        Conexao conexao = new Conexao();
        try (Connection conn = conexao.getConnection()) {
            System.out.println("Conexão estabelecida com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
        }
    }
}