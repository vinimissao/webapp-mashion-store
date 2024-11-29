<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/login.css">
    <link rel="icon" href="<%= request.getContextPath() %>/img/mashion store.png" type="image/x-icon">
</head>
<body>

<div class="login-container">

    <img src="<%= request.getContextPath() %>/img/mashion store.png" alt="Logo" />
    <h2>Login</h2>
    <p>Digite os seus dados de acesso no campo abaixo.</p>

    <!-- Exibe a mensagem de erro caso exista -->
    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
    %>
    <div class="error-message" style="color: red; text-align: center;">
        <%= errorMessage %>
    </div>
    <%
        }
    %>

    <form action="<%= request.getContextPath() %>/LoginServlet" method="post">
        <input type="email" id="email" name="email" placeholder="Digite seu e-mail" required>
        <div class="password-container">
            <input type="password" id="senha" name="senha" placeholder="Digite sua senha" required>
            <button type="button" id="togglePassword" onclick="toggleSenha()">👁️</button>
        </div>

        <a href="<%= request.getContextPath() %>/recSenha.jsp">Esqueci minha senha</a>
        <button type="submit">Acessar</button>

        <p>Não tem cadastro?</p>
        <button type="button" onclick="location.href='<%= request.getContextPath() %>/cadastro.jsp'">Criar conta</button>
        <span class="psw">Back to <a href="<%= request.getContextPath() %>/home.jsp">Home</a></span>
    </form>

</div>

<script>
    function toggleSenha() {
        var senhaInput = document.getElementById("senha");
        var toggleButton = document.getElementById("togglePassword");
        if (senhaInput.type === "password") {
            senhaInput.type = "text";
            toggleButton.textContent = "🙈";
        } else {
            senhaInput.type = "password";
            toggleButton.textContent = "👁️";
        }
    }
</script>
</body>
</html>
