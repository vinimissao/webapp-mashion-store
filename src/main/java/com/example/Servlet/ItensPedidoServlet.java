package com.example.Servlet;

import com.example.dao.ItensPedidoDAO;
import com.example.Modelo.ItensPedido;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.math.BigDecimal;

@WebServlet("/itenspedido")
public class ItensPedidoServlet extends HttpServlet {

    private ItensPedidoDAO itensPedidoDAO;

    @Override
    public void init() throws ServletException {
       
        try {
           
            String url = "jdbc:mysql://localhost:3306/mashionstore";
            String user = "root";
            String password = "root";

            
            Connection connection = DriverManager.getConnection(url, user, password);
            itensPedidoDAO = new ItensPedidoDAO(connection); 
        } catch (SQLException e) {
            throw new ServletException("Erro ao conectar com o banco de dados", e);
        }
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("listar".equals(action)) {
            List<ItensPedido> itens = itensPedidoDAO.listarTodos();
            request.setAttribute("itens", itens);
            request.getRequestDispatcher("/WEB-INF/jsp/listarItensPedido.jsp").forward(request, response);
        } else if ("buscar".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            ItensPedido item = itensPedidoDAO.buscarItem(id);
            request.setAttribute("item", item);
            request.getRequestDispatcher("/WEB-INF/jsp/editarItemPedido.jsp").forward(request, response);
        }
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("salvar".equals(action)) {
            int pedidoId = Integer.parseInt(request.getParameter("pedido_id"));
            int produtoId = Integer.parseInt(request.getParameter("produto_id"));
            int quantidade = Integer.parseInt(request.getParameter("quantidade"));
            BigDecimal preco = new BigDecimal(request.getParameter("preco"));

            ItensPedido item = new ItensPedido(pedidoId, produtoId, quantidade, preco);
            if (itensPedidoDAO.salvar(item)) {
                response.sendRedirect(request.getContextPath() + "/itenspedido?action=listar");
            } else {
                response.getWriter().println("Erro ao salvar item do pedido.");
            }
        } else if ("atualizar".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            int pedidoId = Integer.parseInt(request.getParameter("pedido_id"));
            int produtoId = Integer.parseInt(request.getParameter("produto_id"));
            int quantidade = Integer.parseInt(request.getParameter("quantidade"));
            BigDecimal preco = new BigDecimal(request.getParameter("preco"));

            ItensPedido item = new ItensPedido(id, pedidoId, produtoId, quantidade, preco);
            if (itensPedidoDAO.atualizar(item)) {
                response.sendRedirect(request.getContextPath() + "/itenspedido?action=listar");
            } else {
                response.getWriter().println("Erro ao atualizar item do pedido.");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        if (itensPedidoDAO.deletar(id)) {
            response.getWriter().write("Item deletado com sucesso.");
        } else {
            response.getWriter().write("Erro ao deletar item do pedido.");}}}
 
