<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Formulário de Cadastro</title>
</head>
<body>
<h1>Formulário de Cadastro</h1>
<form action="CadastroServlet" method="post">
    <label for="nome">Nome:</label>
    <input type="text" id="nome" name="nome" required><br>
    <label for="email">E-mail:</label>
    <input type="email" id="email" name="email" required><br>
    <label for="cep">CEP:</label>
    <input type="text" id="cep" name="cep" required><br>
    <label for="numero">Número:</label>
    <input type="text" id="numero" name="numero" required><br>
    <label for="dataNascimento">Data de Nascimento:</label>
    <input type="date" id="dataNascimento" name="dataNascimento" required><br>
    <label for="telefone">Telefone:</label>
    <input type="text" id="telefone" name="telefone" required><br>
    <label for="senha">Senha:</label>
    <input type="password" id="senha" name="senha" required><br>
    <label for="logradouro">Logradouro:</label>
    <input type="text" id="logradouro" name="logradouro" required><br>
    <label for="cidade">Cidade:</label>
    <input type="text" id="cidade" name="cidade" required><br>
    <label for="estado">Estado:</label>
    <input type="text" id="estado" name="estado" required><br>
    <label for="bairro">Bairro:</label>
    <input type="text" id="bairro" name="bairro" required><br>
    <button type="submit">Cadastrar</button>
</form>

<c:if test="${not empty successMessage}">
    <p style="color:green;">${successMessage}</p>
</c:if>
<c:if test="${not empty errorMessage}">
    <p style="color:red;">${errorMessage}</p>
</c:if>
</body>
</html>
