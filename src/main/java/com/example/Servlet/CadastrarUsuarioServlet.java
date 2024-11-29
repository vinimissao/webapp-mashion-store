package com.example.Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/cadastrarUsuario")
public class CadastrarUsuarioServlet extends HttpServlet {

    private Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            // Configure sua conexão com o banco de dados
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sua_base", "usuario", "senha");
        } catch (SQLException e) {
            throw new ServletException("Erro ao conectar ao banco de dados", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nome = req.getParameter("nome");
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        // Verifica se os parâmetros obrigatórios foram fornecidos
        if (nome == null || nome.isEmpty() || email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
            req.setAttribute("mensagemErro", "Todos os campos são obrigatórios!");
            req.getRequestDispatcher("cadastrar-usuario.jsp").forward(req, resp);
            return;
        }

        // Insere o usuário no banco de dados
        String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha); // Idealmente, hashe a senha para maior segurança
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException("Erro ao cadastrar usuário", e);
        }

        // Redireciona para a página de login após o cadastro
        resp.sendRedirect("login.jsp");
    }

    @Override
    public void destroy() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
