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
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String cep = request.getParameter("cep");
        String numeroStr = request.getParameter("numero"); // Removido trim() aqui
        String dataNascStr = request.getParameter("data_Nasc");
        String telefoneStr = request.getParameter("telefone"); // Removido trim() aqui
        String senha = request.getParameter("senha");

        // Validação básica
        if (isNullOrEmpty(nome) || isNullOrEmpty(email) || isNullOrEmpty(cep) || isNullOrEmpty(numeroStr) ||
                isNullOrEmpty(dataNascStr) || isNullOrEmpty(telefoneStr) || isNullOrEmpty(senha)) {
            request.setAttribute("errorMessage", "Todos os campos são obrigatórios.");
            request.getRequestDispatcher("cadastro.jsp").forward(request, response);
            return;
        }

        // Verificação se numero e telefone são numéricos
        if (!numeroStr.matches("\\d+") || !telefoneStr.matches("\\d+")) {
            request.setAttribute("errorMessage", "Número e Telefone devem ser numéricos.");
            request.getRequestDispatcher("cadastro.jsp").forward(request, response);
            return;
        }

        // Busca o endereço pelo CEP
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

        // Conversão e validação
        int numero;
        long telefone;
        Calendar dataNasc;
        try {
            numero = Integer.parseInt(numeroStr);
            telefone = Long.parseLong(telefoneStr);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dataNascStr);

            dataNasc = Calendar.getInstance();
            dataNasc.setTime(date);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Erro de conversão em número ou telefone: " + e.getMessage());
            request.getRequestDispatcher("cadastro.jsp").forward(request, response);
            return;
        } catch (ParseException e) {
            request.setAttribute("errorMessage", "Formato de data inválido. Use yyyy-MM-dd.");
            request.getRequestDispatcher("cadastro.jsp").forward(request, response);
            return;
        }

        // Criação do objeto Cadastro
        Cadastro cadastro = new Cadastro(nome, email, enderecoData.getLogradouro(), enderecoData.getCidade(),
                enderecoData.getEstado(), enderecoData.getBairro(), numero, cep,
                dataNasc, telefone, senha);

        try {
            CadastroDao cadastroDao = new CadastroDao();
            cadastroDao.adicionar(cadastro);
            request.setAttribute("successMessage", "Cadastro realizado com sucesso!");
            request.getRequestDispatcher("cadastro.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Erro ao cadastrar: " + e.getMessage());
            request.getRequestDispatcher("cadastro.jsp").forward(request, response);
        }
    }

    // Método auxiliar para verificar se uma String é nula ou vazia
    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
