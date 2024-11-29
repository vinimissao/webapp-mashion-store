package com.example.Servlet;

import com.example.Modelo.Carrinho;
import com.example.Modelo.ItemCarrinho;
import com.example.Modelo.Produto;
import com.example.dao.CarrinhoDao;
import com.example.dao.ProdutoDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

@WebServlet("/carrinho")
public class CarrinhoServlet extends HttpServlet {

    private CarrinhoDao carrinhoDao;
    private ProdutoDao produtoDao;

    @Override
    public void init() throws ServletException {
        try {
            // Ajuste para sua URL de banco, usuário e senha
            String url = "jdbc:mysql://localhost:3306/sua_base";
            String usuario = "usuario";
            String senha = "senha";
            Connection connection = DriverManager.getConnection(url, usuario, senha);
            carrinhoDao = new CarrinhoDao(connection);
            produtoDao = new ProdutoDao(connection);
        } catch (SQLException e) {
            throw new ServletException("Erro ao conectar com o banco de dados", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int carrinhoId = Integer.parseInt(req.getParameter("carrinhoId"));
            Carrinho carrinho = carrinhoDao.getCarrinho(carrinhoId);
            req.setAttribute("carrinho", carrinho);
            req.getRequestDispatcher("carrinho.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Erro ao processar o carrinho", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int carrinhoId = Integer.parseInt(req.getParameter("carrinhoId"));
            int produtoId = Integer.parseInt(req.getParameter("produtoId"));
            int quantidade = Integer.parseInt(req.getParameter("quantidade"));

            Produto produto = produtoDao.buscarProdutoPorId(produtoId);
            Carrinho carrinho = new Carrinho(carrinhoId);
            ItemCarrinho itemCarrinho = new ItemCarrinho(produto, quantidade);

            carrinhoDao.adicionarItemCarrinho(carrinho, itemCarrinho);
            resp.sendRedirect("carrinho?carrinhoId=" + carrinhoId);
        } catch (Exception e) {
            throw new ServletException("Erro ao adicionar item ao carrinho", e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int itemId = Integer.parseInt(req.getParameter("itemId"));
            carrinhoDao.removerItemCarrinho(itemId);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            throw new ServletException("Erro ao remover item do carrinho", e);
        }
    }
}
