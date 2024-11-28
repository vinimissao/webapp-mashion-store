<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrinho de Compras</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Carrinho.css">
</head>
<body>
<div class="container">
    <h1>Carrinho de Compras</h1>

    <c:choose>
        <c:when test="${empty itens}">
            <p>Seu carrinho está vazio. <a href="${pageContext.request.contextPath}/produtos">Ver produtos</a></p>
        </c:when>
        <c:otherwise>
            <table>
                <thead>
                <tr>
                    <th>Produto</th>
                    <th>Preço Unitário</th>
                    <th>Quantidade</th>
                    <th>Total</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${itens}">
                    <tr>
                        <td>${item.produtoNome}</td>
                        <td>${item.preco}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/carrinho" method="post">
                                <input type="hidden" name="id" value="${item.id}">
                                <input type="number" name="quantidade" value="${item.quantidade}" min="1">
                                <button type="submit" name="action" value="atualizar">Atualizar</button>
                            </form>
                        </td>
                        <td>${item.quantidade * item.preco}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/carrinho" method="post">
                                <input type="hidden" name="id" value="${item.id}">
                                <button type="submit" name="action" value="remover">Remover</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="3"><strong>Total Geral:</strong></td>
                    <td colspan="2"><strong>${total}</strong></td>
                </tr>
                </tfoot>
            </table>
            <div class="actions">
                <a href="${pageContext.request.contextPath}/produtos" class="btn">Continuar Comprando</a>
                <form action="${pageContext.request.contextPath}/pedido" method="post">
                    <button type="submit" class="btn">Finalizar Pedido</button>
                </form>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
