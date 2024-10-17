<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro</title>
</head>
<body>
<h1>Formulário de Cadastro</h1>
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
    Logradouro: <input type="text" name="logradouro" value="<%= request.getParameter("logradouro") != null ? request.getParameter("logradouro") : "" %>" required><br>
    Cidade: <input type="text" name="cidade" value="<%= request.getParameter("cidade") != null ? request.getParameter("cidade") : "" %>" required><br>
    Estado: <input type="text" name="estado" value="<%= request.getParameter("estado") != null ? request.getParameter("estado") : "" %>" required><br>
    Bairro: <input type="text" name="bairro" value="<%= request.getParameter("bairro") != null ? request.getParameter("bairro") : "" %>" required><br>
    <%
        }
    %>

    <input type="submit" value="Cadastrar">
</form>


<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
%>
<p style="color:red;"><%= errorMessage %></p>
<%
    }

    String successMessage = (String) request.getAttribute("successMessage");
    if (successMessage != null) {
%>
<p style="color:green;"><%= successMessage %></p>
<%
    }
%>

</body>
</html>
