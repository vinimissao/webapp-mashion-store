<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.Modelo.Produto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Base64" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Painel de Administração</title>
    <link rel="stylesheet" href="css/admin_styles.css">
    <script>
        function mostrarSecao(secaoId) {
            document.querySelectorAll('.conteudo').forEach(secao => {
                secao.classList.remove('ativo');
            });
            document.getElementById(secaoId).classList.add('ativo');
        }
    </script>
</head>
<body>
<!-- Menu Lateral -->
<div class="menu-lateral">
    <h1>Painel de Administração</h1>
    <a onclick="mostrarSecao('listarProdutos')">Listar Produtos</a>
    <a onclick="mostrarSecao('incluirProduto')">Incluir Produto</a>
    <a onclick="mostrarSecao('gerarRelatorioResumido')">Relatório Resumido</a>
    <a onclick="mostrarSecao('gerarRelatorioDetalhado')">Relatório Detalhado</a>
    <nav>
        <ul>
            <li><a href="Index.jsp">Home</a></li>
        </ul>
    </nav>
</div>

<!-- Conteúdo Principal -->
<div id="listarProdutos" class="conteudo ativo">
    <h2>Listagem de Produtos</h2>
    <!-- Exibir mensagens de sucesso ou erro -->
    <c:if test="${not empty successMessage}">
        <div class="success">${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="error">${errorMessage}</div>
    </c:if>

    <!-- Tabela de produtos -->
    <table border="1" cellspacing="0" cellpadding="10">
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Descrição</th>
            <th>Preço</th>
            <th>Estoque</th>
            <th>Imagem</th>
            <th>Ações</th>
        </tr>
        <c:forEach var="produto" items="${produtos}">
            <tr>
                <td>${produto.id}</td>
                <td>${produto.nome}</td>
                <td>${produto.descricao}</td>
                <td>
                    <fmt:formatNumber value="${produto.preco}" type="currency" currencySymbol="R$" minFractionDigits="2" />
                </td>
                <td>${produto.estoque}</td>
                <td>
                    <c:if test="${produto.imagem != null}">
                        <img src="data:image/jpeg;base64,${produto.imagemBase64}" alt="Imagem do Produto" width="100">
                    </c:if>
                </td>
                <td>
                    <form action="ProdutoServlet" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="action" value="alterar">
                        <input type="hidden" name="id" value="${produto.id}" required><br>
                        <label>Nome:</label>
                        <input type="text" name="nome" value="${produto.nome}" required><br>

                        <label>Descrição:</label>
                        <textarea name="descricao" required>${produto.descricao}</textarea><br>

                        <label>Preço:</label>
                        <input type="text" name="preco" value="${produto.preco}" required><br>

                        <label>Estoque:</label>
                        <input type="number" name="estoque" value="${produto.estoque}" required><br>

                        <label>Imagem:</label>
                        <input type="file" name="imagem" accept="image/*"><br>

                        <button type="submit">Salvar Produto</button>
                    </form>
                    <form action="ProdutoServlet" method="post" style="display: inline;">
                        <input type="hidden" name="action" value="excluir">
                        <input type="hidden" name="id" value="${produto.id}">
                        <button type="submit" onclick="return confirm('Tem certeza que deseja excluir este produto?');">Excluir</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div id="incluirProduto" class="conteudo">
    <h2>Incluir Produto</h2>
    <form action="ProdutoServlet" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="incluir">
        <label>Nome:</label>
        <input type="text" name="nome" required><br>

        <label>Descrição:</label>
        <textarea name="descricao" required></textarea><br>

        <label>Preço:</label>
        <input type="text" name="preco" required><br>

        <label>Estoque:</label>
        <input type="number" name="estoque" required><br>

        <label>Imagem:</label>
        <input type="file" name="imagem" accept="image/*" required><br>

        <button type="submit">Salvar Produto</button>
    </form>
</div>

<div id="gerarRelatorioResumido" class="conteudo">
    <h2>Relatório Resumido</h2>
    <table border="1" cellspacing="0" cellpadding="10">
        <tr>
            <th>Cliente</th>
            <th>Total de Vendas</th>
        </tr>
        <c:forEach var="linha" items="${relatorioResumido}">
            <tr>
                <td>${linha.cliente}</td>
                <td>
                    <fmt:formatNumber value="${linha.totalVendas}" type="currency" currencySymbol="R$" />
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div id="gerarRelatorioDetalhado" class="conteudo">
    <h2>Relatório Detalhado</h2>
    <form action="relatorio" method="get">
        <input type="hidden" name="tipo" value="detalhado">
        <label for="pedidoId">ID do Pedido:</label>
        <input type="text" id="pedidoId" name="pedidoId" required>
        <button type="submit">Gerar Relatório</button>
    </form>
    <table border="1" cellspacing="0" cellpadding="10">
        <tr>
            <th>Produto</th>
            <th>Quantidade</th>
            <th>Preço Unitário</th>
            <th>Subtotal</th>
        </tr>
        <c:forEach var="item" items="${relatorioDetalhado}">
            <tr>
                <td>${item[2]}</td> <!-- Produto -->
                <td>${item[3]}</td> <!-- Quantidade -->
                <td>
                    <fmt:formatNumber value="${item[4]}" type="currency" currencySymbol="R$" />
                </td>
                <td>
                    <fmt:formatNumber value="${item[5]}" type="currency" currencySymbol="R$" />
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
