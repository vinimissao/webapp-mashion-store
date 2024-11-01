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
        maxFileSize = 1024 * 1024 * 2,      // 2 MB para arquivos de imagem
        maxRequestSize = 1024 * 1024 * 10   // 10 MB para a requisição inteira
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
            throw new ServletException("Erro ao listar produtos", e);
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
            throw new ServletException(e);
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
        String precoStr = request.getParameter("preco");
        String estoqueStr = request.getParameter("estoque");
        Part imagemPart = request.getPart("imagem");

        if (nome == null || nome.isEmpty() || descricao == null || descricao.isEmpty() ||
                precoStr == null || precoStr.isEmpty() || estoqueStr == null || estoqueStr.isEmpty() ||
                imagemPart == null || imagemPart.getSize() == 0) {
            request.setAttribute("errorMessage", "Preencha todos os campos obrigatórios.");
            listarProdutos(request, response);  // Redireciona para a listagem de produtos
            return;
        }

        double preco = Double.parseDouble(precoStr);
        int estoque = Integer.parseInt(estoqueStr);
        byte[] imagem = imagemPart.getInputStream().readAllBytes();

        Produto produto = new Produto(0, nome, descricao, preco, estoque, imagem, new Timestamp(System.currentTimeMillis()));
        produtoDao.adicionarProduto(produto);

        request.setAttribute("successMessage", "Produto cadastrado com sucesso!");
        listarProdutos(request, response);  // Redireciona para a listagem de produtos
    }

    private void alterarProduto(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String idStr = request.getParameter("id");
        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        String precoStr = request.getParameter("preco");
        String estoqueStr = request.getParameter("estoque");

        // Verificando se os parâmetros necessários estão preenchidos
        if (idStr == null || nome == null || descricao == null || precoStr == null || estoqueStr == null ||
                idStr.isEmpty() || nome.isEmpty() || descricao.isEmpty() || precoStr.isEmpty() || estoqueStr.isEmpty()) {
            request.setAttribute("errorMessage", "Todos os campos devem ser preenchidos.");
            request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
            return;
        }

        // Converter o ID para inteiro
        int id = Integer.parseInt(idStr);

        // Substituindo a vírgula por ponto para o preço
        precoStr = precoStr.replace(",", ".");

        // Verificando se o preço e estoque não são nulos
        double preco = Double.parseDouble(precoStr);
        int estoque = Integer.parseInt(estoqueStr);
        Part imagemPart = request.getPart("imagem");
        byte[] imagem = imagemPart.getSize() > 0 ? imagemPart.getInputStream().readAllBytes() : null;

        ProdutoDao produtoDao = new ProdutoDao();
        Produto produto = produtoDao.buscarProdutoPorId(id);
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setEstoque(estoque);

        if (imagem != null) {
            produto.setImagem(imagem);
        }

        produtoDao.atualizarProduto(produto);

        request.setAttribute("successMessage", "Produto atualizado com sucesso!");
        request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
    }


    private void excluirProduto(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        produtoDao.deletarProduto(id);

        request.setAttribute("successMessage", "Produto excluído com sucesso!");
        listarProdutos(request, response);  // Redireciona para a listagem de produtos
    }

    private void consultarProduto(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        Produto produto = produtoDao.buscarProdutoPorId(id);

        if (produto != null) {
            request.setAttribute("produto", produto);
            request.getRequestDispatcher("produtoDetalhes.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Produto não encontrado.");
            listarProdutos(request, response);  // Redireciona para a listagem de produtos
        }
    }

    private void listarProdutos(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Produto> produtos = produtoDao.listarProdutos();
        request.setAttribute("produtos", produtos);
        request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
    }
}
