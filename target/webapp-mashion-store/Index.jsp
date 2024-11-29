<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MashionStore - Home</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<!-- Cabeçalho -->
<header>
    <div class="logo">
        <img src="images/logo.png" alt="MashionStore Logo">
    </div>
    <nav>
        <ul>
            <li><a href="index.jsp">Home</a></li>
            <li><a href="carrinho.jsp">Carrinho</a></li>
            <li><a href="login.jsp">Login</a></li>
        </ul>
    </nav>
</header>

<!-- Seção de Produtos -->
<section id="produtos">
    <div class="container">
        <h2>Produtos em Destaque</h2>
        <div class="produto">
            <img src="images/produto1.jpg" alt="Vestido Elegante">
            <div class="produto-info">
                <h3>Vestido Elegante</h3>
                <p class="preco">R$ 199,99</p>
                <a href="produto.jsp?id=1" class="btn-comprar">Ver Detalhes</a>
            </div>
        </div>
    </div>
</section>

<!-- Rodapé -->
<footer>
    <p>&copy; 2024 MashionStore - Todos os direitos reservados</p>
</footer>

</body>
</html>