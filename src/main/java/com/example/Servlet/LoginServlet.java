package com.example.Servlet;

import com.example.Modelo.Cadastro;
import com.example.dao.CadastroDao;
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
            usuarioLogado = cadastroDao.validarUsuario(email, senha);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Erro ao autenticar: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }


        if (usuarioLogado != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogado", usuarioLogado);
            session.setAttribute("isAdmin", usuarioLogado.isAdmin());

            if (usuarioLogado.isAdmin()) {
                response.sendRedirect("ProdutoServlet");
            } else {
                response.sendRedirect("clientDashboard.jsp");
            }
        } else {
            request.setAttribute("errorMessage", "Email ou senha inválidos.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
