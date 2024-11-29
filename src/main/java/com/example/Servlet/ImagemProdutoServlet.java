package com.example.Servlet;

import com.example.Modelo.Produto;
import com.example.dao.ProdutoDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.http.Part;

@WebServlet("/imagemProduto")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // Limite de 5MB
public class ImagemProdutoServlet extends HttpServlet {

    private ProdutoDao produtoDao;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sua_base", "usuario", "senha");
            produtoDao = new ProdutoDao(connection);
        } catch (SQLException e) {
            throw new ServletException("Erro ao inicializar o servlet: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Recupera o ID do produto
            int id = Integer.parseInt(req.getParameter("produtoId"));
            
            // Recupera a imagem
            Part imagem = req.getPart("imagem");

            // Verifica se o arquivo de imagem foi enviado e possui tamanho válido
            if (imagem != null && imagem.getSize() > 0) {
                // Lê os bytes da imagem
                byte[] imagemBytes = imagem.getInputStream().readAllBytes();

                // Busca o produto no banco de dados
                Produto produto = produtoDao.buscarProdutoPorId(id);
                if (produto != null) {
                    // Atualiza a imagem do produto
                    produto.setImagem(imagemBytes);
                    produtoDao.atualizarProduto(produto);
                }
            } else {
                req.setAttribute("mensagemErro", "Nenhuma imagem foi selecionada.");
            }
            resp.sendRedirect("produtos");
        } catch (Exception e) {
            throw new ServletException("Erro ao processar a imagem: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Recupera o ID do produto
            int id = Integer.parseInt(req.getParameter("produtoId"));

            // Busca o produto pelo ID
            Produto produto = produtoDao.buscarProdutoPorId(id);

            // Se o produto e a imagem existirem, envia a imagem para o cliente
            if (produto != null && produto.getImagem() != null) {
                resp.setContentType("image/jpeg");
                resp.getOutputStream().write(produto.getImagem());
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Imagem não encontrada para este produto.");
            }
        } catch (Exception e) {
            throw new ServletException("Erro ao recuperar a imagem: " + e.getMessage(), e);
        }
    }
}
