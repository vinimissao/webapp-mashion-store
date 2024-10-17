package com.example.Servlet;

import com.example.dao.CadastroDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        try {
            CadastroDao cadastroDao = new CadastroDao();
            boolean isValidUser = cadastroDao.validarUsuario(email, senha);

            if (isValidUser) {
                if (cadastroDao.isAdmin(email)) {
                    response.sendRedirect("adminDashboard.jsp");
                } else {
                    response.sendRedirect("clientDashboard.jsp");
                }
            } else {
                request.setAttribute("errorMessage", "E-mail ou senha inválidos.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Erro ao acessar o banco de dados: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
