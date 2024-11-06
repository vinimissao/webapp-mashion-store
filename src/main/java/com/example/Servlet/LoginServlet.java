package com.example.Servlet;

import com.example.Modelo.Cadastro; // Importar seu modelo de Cadastro
import com.example.dao.CadastroDao; // Importar seu DAO de Cadastro
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        CadastroDao cadastroDao;
        Cadastro usuarioLogado;

        try {
            cadastroDao = new CadastroDao();
            // Chama o método que valida o login e retorna um objeto Cadastro
            usuarioLogado = cadastroDao.validarUsuario(email, senha);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Erro ao autenticar: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        if (usuarioLogado != null) {
            // Se o usuário for encontrado e as credenciais estiverem corretas
            HttpSession session = request.getSession();
            // Armazena o objeto do usuário na sessão
            session.setAttribute("usuarioLogado", usuarioLogado);
            // Define isAdmin com base no perfil
            session.setAttribute("isAdmin", usuarioLogado.isAdmin());

            // Redireciona para a página correta dependendo do tipo de usuário
            if (usuarioLogado.isAdmin()) {
                response.sendRedirect("ProdutoServlet"); // Para administradores
            } else {
                response.sendRedirect("clientDashboard.jsp"); // Para clientes
            }
        } else {
            request.setAttribute("errorMessage", "Email ou senha inválidos.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
