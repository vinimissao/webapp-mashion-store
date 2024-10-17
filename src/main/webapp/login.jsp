<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Tela de Login</title>
</head>
<body>
<h1>Login</h1>
<form action="LoginServlet" method="post">
    <label for="email">E-mail:</label>
    <input type="email" id="email" name="email" required><br>
    <label for="senha">Senha:</label>
    <input type="password" id="senha" name="senha" required><br>
    <button type="submit">Entrar</button>
</form>
<c:if test="${not empty errorMessage}">
    <p style="color:red;">${errorMessage}</p>
</c:if>
</body>
</html>
