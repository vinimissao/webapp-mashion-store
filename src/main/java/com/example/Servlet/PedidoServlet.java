package com.example.Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.example.Modelo.Pedido;
import com.example.dao.PedidosDAO;
import com.example.Conexao.Conexao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/PedidoServlet")
public class PedidoServlet extends HttpServlet {
    private PedidosDAO pedidosDAO;

    @Override
    public void init() throws ServletException {
       
        Conexao conexao = new Conexao();
        try {
            Connection connection = conexao.getConnection();
            pedidosDAO = new PedidosDAO(connection);
        } catch (SQLException e) {
            throw new ServletException("Erro ao conectar ao banco de dados", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "listar";
        }
        
        switch (action) {
            case "listar":
                listarPedidos(request, response);
                break;
            case "buscar":
                buscarPedido(request, response);
                break;
            case "deletar":
                deletarPedido(request, response);
                break;
            default:
                listarPedidos(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("cadastrar".equals(action)) {
            cadastrarPedido(request, response);
        } else if ("atualizar".equals(action)) {
            atualizarPedido(request, response);
        }
    }

    private void listarPedidos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Pedido> pedidos = pedidosDAO.listarTodos();
        request.setAttribute("pedidos", pedidos);
        request.getRequestDispatcher("/jsp/listaPedidos.jsp").forward(request, response);
    }

    private void buscarPedido(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Pedido pedido = pedidosDAO.buscarPedido(id);
        request.setAttribute("pedido", pedido);
        request.getRequestDispatcher("/jsp/detalhePedido.jsp").forward(request, response);
    }

    private void cadastrarPedido(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Pedido pedido = new Pedido();
            pedido.setClienteId(Integer.parseInt(request.getParameter("clienteId")));
            pedido.setDataPedido(java.sql.Timestamp.valueOf(request.getParameter("dataPedido")));
            pedido.setTotal(new java.math.BigDecimal(request.getParameter("total")));
            pedido.setStatus(request.getParameter("status"));

            pedidosDAO.salvar(pedido);
            response.sendRedirect("PedidoServlet?action=listar");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("erro.jsp");
        }
    }

    private void atualizarPedido(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Pedido pedido = new Pedido();
            pedido.setId(Integer.parseInt(request.getParameter("id")));
            pedido.setClienteId(Integer.parseInt(request.getParameter("clienteId")));
            pedido.setDataPedido(java.sql.Timestamp.valueOf(request.getParameter("dataPedido")));
            pedido.setTotal(new java.math.BigDecimal(request.getParameter("total")));
            pedido.setStatus(request.getParameter("status"));

            pedidosDAO.atualizar(pedido);
            response.sendRedirect("PedidoServlet?action=listar");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("erro.jsp");
        }
    }

    private void deletarPedido(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        pedidosDAO.deletar(id);
        response.sendRedirect("PedidoServlet?action=listar");
    }
}
