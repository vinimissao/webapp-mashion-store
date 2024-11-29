package com.example.Servlet;

import com.example.Modelo.Carrinho;
import com.example.Modelo.ItemCarrinho;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/pedido")
public class PedidoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");

        if (carrinho == null || carrinho.getItens().isEmpty()) {
            req.setAttribute("mensagem", "O carrinho está vazio!");
            req.getRequestDispatcher("carrinho-view.jsp").forward(req, resp);
            return;
        }

        // Aqui você processaria o pedido, como salvar no banco de dados
        session.removeAttribute("carrinho");
        req.setAttribute("mensagem", "Pedido realizado com sucesso!");
        req.getRequestDispatcher("pedido-confirmado.jsp").forward(req, resp);
    }
}
