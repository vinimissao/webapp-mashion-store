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

    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        String successMessage = (String) request.getAttribute("successMessage");
        if (errorMessage != null) {
    %>
    <p class="error-message"><%= errorMessage %></p>
    <%
        }
        if (successMessage != null) {
    %>
    <p class="success-message"><%= successMessage %></p>
    <%
        }
    %>

    <form action="Cadastrar" method="post">
        <div class="tipo-cadastro">
            <label>
                <input type="checkbox" name="tipoCadastro" value="admin" <%= "admin".equals(request.getParameter("tipoCadastro")) ? "checked" : "" %>> Administrador
            </label>
            <label>
                <input type="checkbox" name="tipoCadastro" value="cliente" <%= "cliente".equals(request.getParameter("tipoCadastro")) ? "checked" : "" %>> Cliente
            </label>
        </div>

        <input type="text" name="nome" placeholder="Nome" required value="<%= request.getParameter("nome") != null ? request.getParameter("nome") : "" %>">
        <input type="email" name="email" placeholder="E-mail" required value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>">
        <input type="text" name="cpf" placeholder="cpf" required value="<%= request.getParameter("cpf") != null ? request.getParameter("cpf") : "" %>">
        <input type="text" id="cep" name="cep" placeholder="CEP" required value="<%= request.getParameter("cep") != null ? request.getParameter("cep") : "" %>">
        <input type="text" id="logradouro" name="logradouro" placeholder="Logradouro" required value="<%= request.getParameter("logradouro") != null ? request.getParameter("logradouro") : "" %>">

        <div class="linha-campos">
            <input type="text" id="bairro" name="bairro" placeholder="Bairro" required value="<%= request.getParameter("bairro") != null ? request.getParameter("bairro") : "" %>" style="width: 31%;">
            <input type="text" id="cidade" name="cidade" placeholder="Cidade" required value="<%= request.getParameter("cidade") != null ? request.getParameter("cidade") : "" %>" style="width: 31%;">
            <input type="text" id="estado" name="estado" placeholder="Estado" required value="<%= request.getParameter("estado") != null ? request.getParameter("estado") : "" %>" style="width: 31%;">
        </div>

        <div class="linha-campos">
            <input type="text" id="numero" name="numero" placeholder="Número" required value="<%= request.getParameter("numero") != null ? request.getParameter("numero") : "" %>" style="width: 31%;">
        </div>

        <input type="date" name="data_Nasc" placeholder="Data de Nascimento" required value="<%= request.getParameter("data_Nasc") != null ? request.getParameter("data_Nasc") : "" %>">
        <input type="text" name="telefone" placeholder="Telefone" required value="<%= request.getParameter("telefone") != null ? request.getParameter("telefone") : "" %>">
        <input type="password" name="senha" placeholder="Senha" required>

        <input type="submit" value="Cadastrar">
    </form>

    <span class="psw">Back to <a href="login.jsp">login?</a></span>
</div>
</body>
</html>
