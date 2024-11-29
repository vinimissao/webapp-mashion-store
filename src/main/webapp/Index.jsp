<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MashionStore - Home</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<!-- Cabeçalho -->
<header>
    <div class="logo">
        <img src="images/logo.png" alt="MashionStore Logo">
    </div>
    <nav>
        <ul>
            <li><a href="cadastro.jsp">Cadastro</a></li>
            <li><a href="carrinho.jsp">Carrinho</a></li>
            <li><a href="login.jsp">Login</a></li>
        </ul>
    </nav>
</header>

<!-- Seção de Produtos -->
<section id="produtos">
    <div class="container">
        <h2>Produtos em Destaque</h2>
        <div class="produtos-lista">
            <c:forEach var="produto" items="${produtos}">
                <div class="produto-card">
                    <img src="data:image/png;base64,${produto.imagem}" alt="${produto.nome}" class="produto-imagem">
                    <div class="produto-info">
                        <h3 class="produto-nome">${produto.nome}</h3>
                        <p class="produto-preco">R$ ${produto.preco}</p>
                        <a href="/ProdutoServlet?id=${produto.id}" class="btn-comprar">Ver Detalhes</a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</section>


<!-- Rodapé -->
<footer>
    <p>&copy; 2024 MashionStore - Todos os direitos reservados</p>
</footer>

</body>
</html>
