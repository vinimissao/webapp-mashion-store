package com.example.Servlet;

import com.example.Modelo.Produto;
import com.example.dao.ProdutoDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

@WebServlet("/ImagemProdutoServlet")
public class ImagemProdutoServlet extends HttpServlet {
    private ProdutoDao produtoDao;

    @Override
    public void init() {
        try {
            produtoDao = new ProdutoDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int produtoId = Integer.parseInt(request.getParameter("id"));

        try {
            Produto produto = produtoDao.buscarProdutoPorId(produtoId);
            if (produto != null && produto.getImagem() != null) {
                byte[] imagem = produto.getImagem();

                response.setContentType("image/jpeg");
                response.setContentLength(imagem.length);

                OutputStream os = response.getOutputStream();
                os.write(imagem);
                os.flush();
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao carregar imagem");
        }
    }
}
