<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MashionStore - Finalizar Compra</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
   
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

  
    <section id="finalizar-compra">
        <div class="container">
            <h2>Resumo da Compra</h2>

           
            <c:if test="${not empty carrinho}">
                <table>
                    <thead>
                        <tr>
                            <th>Produto</th>
                            <th>Quantidade</th>
                            <th>Preço</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="produto" items="${carrinho}">
                            <tr>
                                <td>${produto.nome}</td>
                                <td>${produto.quantidade}</td>
                                <td>R$ ${produto.preco}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

           
            <h3>Dados de Entrega</h3>
            <form action="processarCompra.jsp" method="post">
                <label for="endereco">Endereço de Entrega</label>
                <input type="text" id="endereco" name="endereco" value="${cliente.endereco}" required>
                
                <label for="cidade">Cidade</label>
                <input type="text" id="cidade" name="cidade" value="${cliente.cidade}" required>

                <label for="cep">CEP</label>
                <input type="text" id="cep" name="cep" value="${cliente.cep}" required>

                <label for="telefone">Telefone</label>
                <input type="text" id="telefone" name="telefone" value="${cliente.telefone}" required>

                
                <div class="total">
                    <p><strong>Total: R$ ${total}</strong></p>
                    <button type="submit" class="btn-finalizar">Confirmar Compra</button>
                </div>
            </form>
        </div>
    </section>

    <!-- Rodapé -->
    <footer>
        <p>&copy; 2024 MashionStore - Todos os direitos reservados</p>
    </footer>

</body>
</html>
