<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MashionStore - Carrinho de Compras</title>
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
                <li><a href="#contato">Contato</a></li>
            </ul>
        </nav>
    </header>

    <!-- Carrinho de Compras -->
    <section id="carrinho">
        <div class="container">
            <h2>Seu Carrinho de Compras</h2>

            <c:if test="${empty carrinho}">
                <p>Seu carrinho está vazio.</p>
            </c:if>

            <c:if test="${not empty carrinho}">
                <table>
                    <thead>
                        <tr>
                            <th>Produto</th>
                            <th>Quantidade</th>
                            <th>Preço</th>
                            <th>Remover</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="produto" items="${carrinho}">
                            <tr>
                                <td>${produto.nome}</td>
                                <td>${produto.quantidade}</td>
                                <td>${produto.preco}</td>
                                <td><a href="removerCarrinho.jsp?id=${produto.id}">Remover</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="total">
                    <p>Total: R$ ${total}</p>
                    <a href="finalizarCompra.jsp" class="btn-finalizar">Finalizar Compra</a>
                </div>
            </c:if>
        </div>
    </section>

    <!-- Rodapé -->
    <footer>
        <p>&copy; 2024 MashionStore - Todos os direitos reservados</p>
    </footer>

</body>
</html>
