<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro</title>
    <link rel="stylesheet" type="text/css" href="css/cadastro.css">
    <link rel="icon" href="img/mashion_store.png" type="image/x-icon">
</head>
<body>
<div class="cadastro-container">
    <img src="img/mashion_store.png" class="logo" alt="Logo" />
    <h2>Cadastro</h2>
    <p>Preencha os campos abaixo para criar uma nova conta.</p>

    <!-- Display success or error messages -->
    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        String successMessage = (String) request.getAttribute("successMessage");
        if (errorMessage != null) {
    %>
    <p style="color:red;"><%= errorMessage %></p>
    <%
        }
        if (successMessage != null) {
    %>
    <p style="color:green;"><%= successMessage %></p>
    <%
        }
    %>

    <!-- Formulário de Cadastro -->
    <form action="Cadastrar" method="post">
        <label>
            <input type="radio" name="tipoCadastro" value="admin" <%= "admin".equals(request.getParameter("tipoCadastro")) ? "checked" : "" %>> Administrador
        </label>
        <label>
            <input type="radio" name="tipoCadastro" value="cliente" <%= "cliente".equals(request.getParameter("tipoCadastro")) ? "checked" : "" %>> Cliente
        </label>
        <br><br>

        <!-- Campos obrigatórios para ambos os tipos -->
        Nome: <input type="text" name="nome" required value="<%= request.getParameter("nome") != null ? request.getParameter("nome") : "" %>"><br>
        E-mail: <input type="email" name="email" required value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>"><br>
        CEP: <input type="text" name="cep" required value="<%= request.getParameter("cep") != null ? request.getParameter("cep") : "" %>"><br>
        Número: <input type="text" name="numero" required value="<%= request.getParameter("numero") != null ? request.getParameter("numero") : "" %>"><br>
        Data de Nascimento: <input type="date" name="data_Nasc" required value="<%= request.getParameter("data_Nasc") != null ? request.getParameter("data_Nasc") : "" %>"><br>
        Telefone: <input type="text" name="telefone" required value="<%= request.getParameter("telefone") != null ? request.getParameter("telefone") : "" %>"><br>
        Senha: <input type="password" name="senha" required><br>

        <%
            String tipoCadastro = request.getParameter("tipoCadastro");
            if ("cliente".equals(tipoCadastro)) {
        %>
        Logradouro: <input type="text" name="logradouro" required value="<%= request.getParameter("logradouro") != null ? request.getParameter("logradouro") : "" %>"><br>
        Cidade: <input type="text" name="cidade" required value="<%= request.getParameter("cidade") != null ? request.getParameter("cidade") : "" %>"><br>
        Estado: <input type="text" name="estado" required value="<%= request.getParameter("estado") != null ? request.getParameter("estado") : "" %>"><br>
        Bairro: <input type="text" name="bairro" required value="<%= request.getParameter("bairro") != null ? request.getParameter("bairro") : "" %>"><br>
        <%
            }
        %>

        <input type="submit" value="Cadastrar">
    </form>

    <span class="psw">Back to <a href="login.jsp">login?</a></span>
</div>

<script>
    function toggleSenha() {
        var senhaInput = document.getElementById("senha");
        var toggleButton = document.getElementById("togglePassword");
        if (senhaInput.type === "password") {
            senhaInput.type = "text";
            toggleButton.textContent = "🙈"; // Troca o ícone ao mostrar a senha
        } else {
            senhaInput.type = "password";
            toggleButton.textContent = "👁️"; // Troca o ícone ao ocultar a senha
        }
    }
</script>
</body>
</html>