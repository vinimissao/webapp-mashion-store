<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <style>
        /* Estilos básicos */
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            padding: 20px;
            background-color: #f4f4f4;
        }
        h1, h2 {
            color: #333;
        }
        form {
            background: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        label {
            display: block;
            margin: 10px 0 5px;
        }
        input[type="text"], input[type="number"], textarea, input[type="file"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        button {
            background-color: #5cb85c;
            color: white;
            border: none;
            padding: 10px 15px;
            cursor: pointer;
            border-radius: 5px;
        }
        button:hover {
            background-color: #4cae4c;
        }
        .message {
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
    </style>
</head>
<body>

<h1>Bem-vindo, Admin!</h1>

<!-- Mensagens de sucesso ou erro -->
<div class="message">
    <c:if test="${not empty errorMessage}">
        <p style="color: red;">${errorMessage}</p>
    </c:if>

    <c:if test="${not empty successMessage}">
        <p style="color: green;">${successMessage}</p>
    </c:if>
</div>

<h2>Cadastrar Produto</h2>

<form action="ProdutoServlet" method="post" enctype="multipart/form-data">
    <input type="hidden" name="action" value="incluir">

    <label for="nome">Nome do Produto:</label>
    <input type="text" id="nome" name="nome" required>

    <label for="descricao">Descrição:</label>
    <textarea id="descricao" name="descricao"></textarea>

    <label for="preco">Preço:</label>
    <input type="number" step="0.01" name="preco" id="preco" required>

    <label for="estoque">Estoque:</label>
    <input type="number" id="estoque" name="estoque" required>

    <label for="imagem">Imagem:</label>
    <input type="file" id="imagem" name="imagem" accept=".png, .jpeg, .jpg" required>

    <button type="submit">Cadastrar Produto</button>
</form>

<!-- Listagem de produtos cadastrados -->
<h2>Produtos Cadastrados</h2>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Nome</th>
        <th>Descrição</th>
        <th>Preço</th>
        <th>Estoque</th>
        <th>Ações</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="produto" items="${produtos}">
        <tr>
            <td>${produto.id}</td>
            <td>${produto.nome}</td>
            <td>${produto.descricao}</td>
            <td>${produto.preco}</td>
            <td>${produto.estoque}</td>
            <td>
                <!-- Links para editar ou deletar o produto -->
                <form action="ProdutoServlet" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="action" value="alterar">
                    <input type="text" name="id" required placeholder="ID do Produto">
                    <input type="text" name="nome" required placeholder="Nome do Produto">
                    <input type="text" name="descricao" required placeholder="Descrição do Produto">
                    <input type="number" name="preco" required placeholder="Preço do Produto">
                    <input type="text" name="estoque" required placeholder="Estoque do Produto">
                    <input type="file" name="imagem" accept="image/*">
                    <input type="submit" value="Alterar Produto">
                </form>

                <form action="ProdutoServlet" method="post" style="display: inline;">
                    <input type="hidden" name="action" value="deletar">
                    <input type="hidden" name="id" value="${produto.id}">
                    <button type="submit">Excluir</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
