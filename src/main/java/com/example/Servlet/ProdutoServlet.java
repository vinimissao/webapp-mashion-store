package com.example.Servlet;

import com.example.Modelo.Produto;
import com.example.dao.ProdutoDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

@WebServlet("/produtos")
public class ProdutoServlet extends HttpServlet {

    private ProdutoDao produtoDao;

    @Override
    public void init() throws ServletException {
        try {
            // Configurando a conexão com o banco de dados
            String url = "jdbc:mysql://localhost:3306/sua_base"; // Ajuste com o seu banco de dados
            String usuario = "usuario";  // Substitua pelo seu usuário
            String senha = "senha";  // Substitua pela sua senha
            Connection connection = DriverManager.getConnection(url, usuario, senha);
            produtoDao = new ProdutoDao(connection);
        } catch (SQLException e) {
            throw new ServletException("Erro ao conectar com o banco de dados", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String action = req.getParameter("action");
            if (action == null) action = "list";

            switch (action) {
                case "new":
                    req.getRequestDispatcher("produto-form.jsp").forward(req, resp);
                    break;
                case "edit":
                    int id = Integer.parseInt(req.getParameter("id"));
                    Produto produto = produtoDao.buscarProdutoPorId(id);
                    if (produto != null) {
                        req.setAttribute("produto", produto);
                        req.getRequestDispatcher("produto-form.jsp").forward(req, resp);
                    } else {
                        resp.sendRedirect("produtos"); // Redireciona se o produto não for encontrado
                    }
                    break;
                case "delete":
                    id = Integer.parseInt(req.getParameter("id"));
                    produtoDao.deletarProduto(id);
                    resp.sendRedirect("produtos"); // Redireciona para a lista de produtos
                    break;
                default: // Listar produtos
                    List<Produto> produtos = produtoDao.listarProdutos();
                    req.setAttribute("produtos", produtos);
                    req.getRequestDispatcher("produto-list.jsp").forward(req, resp);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException("Erro ao processar a solicitação", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Obtendo os parâmetros do formulário
            String nome = req.getParameter("nome");
            String descricao = req.getParameter("descricao");
            BigDecimal preco = new BigDecimal(req.getParameter("preco"));
            int estoque = Integer.parseInt(req.getParameter("estoque"));
            int id = req.getParameter("id") != null && !req.getParameter("id").isEmpty() 
                    ? Integer.parseInt(req.getParameter("id")) : 0;

            Produto produto = new Produto(id, nome, descricao, preco, estoque);

            if (id == 0) {
                // Se o ID for 0, adiciona o produto
                produtoDao.adicionarProduto(produto);
            } else {
                // Caso contrário, atualiza o produto
                produtoDao.atualizarProduto(produto);
            }

            resp.sendRedirect("produtos"); // Redireciona para a lista de produtos
        } catch (Exception e) {
            throw new ServletException("Erro ao processar o formulário", e);
        }
    }
}
