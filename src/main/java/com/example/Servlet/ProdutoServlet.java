package com.example.Servlet;

import com.example.Modelo.Produto;
import com.example.dao.ProdutoDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/ProdutoServlet")
@MultipartConfig(
        maxFileSize = 1024 * 1024 * 2,
        maxRequestSize = 1024 * 1024 * 10
)
public class ProdutoServlet extends HttpServlet {

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
        try {
            listarProdutos(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "incluir":
                    incluirProduto(request, response);
                    break;
                case "alterar":
                    alterarProduto(request, response);
                    break;
                case "excluir":
                    excluirProduto(request, response);
                    break;
                case "consultar":
                    consultarProduto(request, response);
                    break;
                default:
                    listarProdutos(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Erro ao processar a solicitação", e);
        }
    }

    private void incluirProduto(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        HttpSession session = request.getSession();
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");

        if (isAdmin == null || !isAdmin) {
            response.sendRedirect("acesso-negado.jsp");
            return;
        }

        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        String precoStr = request.getParameter("preco").replace(",", ".");
        String estoqueStr = request.getParameter("estoque");
        Part imagemPart = request.getPart("imagem");

        if (nome.isEmpty() || descricao.isEmpty() || precoStr.isEmpty() || estoqueStr.isEmpty() || imagemPart == null || imagemPart.getSize() == 0) {
            forwardWithError(request, response, "Preencha todos os campos obrigatórios.");
            return;
        }

        try {
            double preco = Double.parseDouble(precoStr);
            int estoque = Integer.parseInt(estoqueStr);
            byte[] imagem = imagemPart.getInputStream().readAllBytes();

            Produto produto = new Produto(0, nome, descricao, preco, estoque, imagem, new Timestamp(System.currentTimeMillis()));
            produtoDao.adicionarProduto(produto);

            request.setAttribute("successMessage", "Produto cadastrado com sucesso!");
            listarProdutos(request, response);

        } catch (NumberFormatException e) {
            forwardWithError(request, response, "Erro ao converter valores numéricos.");
        }
    }

    private void alterarProduto(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String idStr = request.getParameter("id");
        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        String precoStr = request.getParameter("preco").replace(",", ".");
        String estoqueStr = request.getParameter("estoque");

        if (idStr.isEmpty() || nome.isEmpty() || descricao.isEmpty() || precoStr.isEmpty() || estoqueStr.isEmpty()) {
            forwardWithError(request, response, "Todos os campos devem ser preenchidos.");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            double preco = Double.parseDouble(precoStr);
            int estoque = Integer.parseInt(estoqueStr);
            Part imagemPart = request.getPart("imagem");
            byte[] imagem = imagemPart.getSize() > 0 ? imagemPart.getInputStream().readAllBytes() : null;

            Produto produto = produtoDao.buscarProdutoPorId(id);
            if (produto == null) {
                forwardWithError(request, response, "Produto não encontrado.");
                return;
            }

            produto.setNome(nome);
            produto.setDescricao(descricao);
            produto.setPreco(preco);
            produto.setEstoque(estoque);

            if (imagem != null) {
                produto.setImagem(imagem);
            }

            produtoDao.atualizarProduto(produto);
            request.setAttribute("successMessage", "Produto atualizado com sucesso!");
            listarProdutos(request, response);

        } catch (NumberFormatException e) {
            forwardWithError(request, response, "Erro ao converter valores numéricos.");
        }
    }

    private void excluirProduto(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            produtoDao.deletarProduto(id);

            request.setAttribute("successMessage", "Produto excluído com sucesso!");
        } catch (NumberFormatException e) {
            forwardWithError(request, response, "ID do produto inválido para exclusão.");
        }
        listarProdutos(request, response);
    }

    private void consultarProduto(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        Produto produto = produtoDao.buscarProdutoPorId(id);

        if (produto != null) {
            request.setAttribute("produto", produto);
            request.getRequestDispatcher("produtoDetalhes.jsp").forward(request, response);
        } else {
            forwardWithError(request, response, "Produto não encontrado.");
        }
    }

    private void listarProdutos(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Produto> produtos = produtoDao.listarProdutos();
        request.setAttribute("produtos", produtos);
        request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
    }

    private void forwardWithError(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws ServletException, IOException {
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
    }
}
