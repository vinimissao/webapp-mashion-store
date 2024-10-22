<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
</head>
<body>

<h1>Bem-vindo, Admin!</h1>

<!-- Mensagens de sucesso ou erro -->
<c:if test="${not empty errorMessage}">
    <p style="color: red;">${errorMessage}</p>
</c:if>

<c:if test="${not empty successMessage}">
    <p style="color: green;">${successMessage}</p>
</c:if>

<h2>Cadastrar Produto</h2>

<form action="ProdutoServlet" method="post" enctype="multipart/form-data">
    <label for="nome">Nome do Produto:</label>
    <input type="text" id="nome" name="nome" required>

    <label for="descricao">Descrição:</label>
    <textarea id="descricao" name="descricao"></textarea>

    <label for="preco">Preço:</label>
    <input type="text" id="preco" name="preco" required>

    <label for="estoque">Estoque:</label>
    <input type="number" id="estoque" name="estoque" required>

    <label for="imagem">Imagem:</label>
    <input type="file" id="imagem" name="imagem" required>

    <button type="submit">Cadastrar Produto</button>
</form>

</body>
</html>
