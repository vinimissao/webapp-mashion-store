package com.example.Servlet;

import com.example.Modelo.Cadastro;
import com.example.dao.CadastroDao;
import com.example.Service.CepService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
@WebServlet("/Cadastrar")
public class CadastrarServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tipoCadastro = request.getParameter("tipoCadastro");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String cep = request.getParameter("cep");
        String numeroStr = request.getParameter("numero");
        String dataNascStr = request.getParameter("data_Nasc");
        String telefoneStr = request.getParameter("telefone"); // Telefone como String
        String senha = request.getParameter("senha");

        if (tipoCadastro.equals("admin") && isNullOrEmpty(nome)) {
            request.setAttribute("errorMessage", "O campo nome é obrigatório para Administradores.");
            request.getRequestDispatcher("cadastro.jsp").forward(request, response);
            return;
        }
        if (isNullOrEmpty(email) || isNullOrEmpty(senha)) {
            request.setAttribute("errorMessage", "E-mail e Senha são obrigatórios.");
            request.getRequestDispatcher("cadastro.jsp").forward(request, response);
            return;
        }

        if (!numeroStr.matches("\\d+")) {
            request.setAttribute("errorMessage", "Número deve ser numérico.");
            request.getRequestDispatcher("cadastro.jsp").forward(request, response);
            return;
        }


        Cadastro enderecoData;
        try {
            enderecoData = CepService.buscarCep(cep);
            if (enderecoData == null) {
                request.setAttribute("errorMessage", "Endereço não encontrado para o CEP informado.");
                request.getRequestDispatcher("cadastro.jsp").forward(request, response);
                return;
            }
        } catch (IOException e) {
            request.setAttribute("errorMessage", "Erro ao buscar o CEP.");
            request.getRequestDispatcher("cadastro.jsp").forward(request, response);
            return;
        }

        // Conversão de dados
        int numero;
        Calendar dataNasc;
        try {
            numero = Integer.parseInt(numeroStr);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dataNascStr);

            dataNasc = Calendar.getInstance();
            dataNasc.setTime(date);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Erro de conversão em número: " + e.getMessage());
            request.getRequestDispatcher("cadastro.jsp").forward(request, response);
            return;
        } catch (ParseException e) {
            request.setAttribute("errorMessage", "Formato de data inválido. Use yyyy-MM-dd.");
            request.getRequestDispatcher("cadastro.jsp").forward(request, response);
            return;
        }

        Cadastro cadastro = new Cadastro(nome, email, enderecoData.getLogradouro(), enderecoData.getCidade(),
                enderecoData.getEstado(), enderecoData.getBairro(), numero, cep,
                dataNasc, telefoneStr, senha, tipoCadastro.equals("admin"));

        try {
            CadastroDao cadastroDao = new CadastroDao();
            int usuarioId = cadastroDao.adicionarUsuario(cadastro);

            if (tipoCadastro.equals("admin")) {
                cadastroDao.adicionarAdministrador(usuarioId, cadastro);
            } else {
                cadastroDao.adicionarCliente(usuarioId, cadastro);
            }

            request.setAttribute("successMessage", "Cadastro realizado com sucesso!");
            request.getRequestDispatcher("cadastro.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Erro ao cadastrar: " + e.getMessage());
            request.getRequestDispatcher("cadastro.jsp").forward(request, response);
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
