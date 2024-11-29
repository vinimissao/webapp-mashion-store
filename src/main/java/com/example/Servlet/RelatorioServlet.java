package com.example.Servlet;

import com.example.dao.ProdutoDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/relatorio")
public class RelatorioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tipoRelatorio = request.getParameter("tipo");
        ProdutoDao produtoDao = null;
        try {
            produtoDao = new ProdutoDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            if ("resumido".equalsIgnoreCase(tipoRelatorio)) {
                gerarRelatorioResumido(request, response, produtoDao);
            } else if ("detalhado".equalsIgnoreCase(tipoRelatorio)) {
                gerarRelatorioDetalhado(request, response, produtoDao);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo de relatório inválido!");
            }
        } finally {
            try {
                produtoDao.close();
            } catch (SQLException e) {
                throw new ServletException("Erro ao fechar a conexão com o banco de dados: " + e.getMessage(), e);
            }
        }
    }

    private void gerarRelatorioResumido(HttpServletRequest request, HttpServletResponse response, ProdutoDao produtoDao)
            throws ServletException, IOException {
        try {
            List<Object[]> relatorioResumido = produtoDao.relatorioResumidoVendasPorCliente();

            if (relatorioResumido.isEmpty()) {
                request.setAttribute("mensagemErro", "Nenhum dado encontrado para o relatório resumido.");
            } else {
                request.setAttribute("relatorioResumido", relatorioResumido);
            }

            request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Erro ao gerar o relatório resumido: " + e.getMessage(), e);
        }
    }

    private void gerarRelatorioDetalhado(HttpServletRequest request, HttpServletResponse response, ProdutoDao produtoDao)
            throws ServletException, IOException {
        try {
            String pedidoIdParam = request.getParameter("pedidoId");
            if (pedidoIdParam == null || pedidoIdParam.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do pedido não fornecido!");
                return;
            }

            int pedidoId = Integer.parseInt(pedidoIdParam);
            List<Object[]> relatorioDetalhado = produtoDao.relatorioDetalhadoPorPedido(pedidoId);

            if (relatorioDetalhado.isEmpty()) {
                request.setAttribute("mensagemErro", "Nenhum dado encontrado para o relatório detalhado.");
            } else {
                request.setAttribute("relatorioDetalhado", relatorioDetalhado);
            }

            request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do pedido inválido!");
        } catch (SQLException e) {
            throw new ServletException("Erro ao gerar o relatório detalhado: " + e.getMessage(), e);
        }
    }
}
