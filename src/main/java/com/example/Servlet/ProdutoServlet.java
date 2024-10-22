package com.example.Servlet;

import com.example.Modelo.Produto;
import com.example.dao.ProdutoDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

@WebServlet("/ProdutoServlet")
public class ProdutoServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");

        // Verifica se é administrador
        if (isAdmin == null || !isAdmin) {
            response.sendRedirect("acesso-negado.jsp");
            return;
        }

        // Pega os dados do formulário
        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        String precoStr = request.getParameter("preco");
        String estoqueStr = request.getParameter("estoque");
        byte[] imagem = request.getPart("imagem").getInputStream().readAllBytes();

        // Validação de dados
        if (nome == null || nome.isEmpty() || precoStr == null || estoqueStr == null) {
            request.setAttribute("errorMessage", "Preencha todos os campos obrigatórios.");
            request.getRequestDispatcher("admin/dashboard-adm.jsp").forward(request, response);
            return;
        }

        try {
            double preco = Double.parseDouble(precoStr);
            int estoque = Integer.parseInt(estoqueStr);
            // Para o id, você pode definir como 0 e deixá o banco de dados gerar automaticamente
            Produto produto = new Produto(0, nome, descricao, preco, estoque, imagem, new Timestamp(System.currentTimeMillis()));
            ProdutoDao produtoDao = new ProdutoDao();
            produtoDao.adicionarProduto(produto);

            request.setAttribute("successMessage", "Produto cadastrado com sucesso!");
            request.getRequestDispatcher("admin/dashboard-adm.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Erro nos campos numéricos.");
            request.getRequestDispatcher("admin/dashboard-adm.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Erro ao salvar produto: " + e.getMessage());
            request.getRequestDispatcher("admin/dashboard-adm.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
