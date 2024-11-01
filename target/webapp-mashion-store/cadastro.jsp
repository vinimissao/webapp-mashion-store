<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro</title>
    <link rel="stylesheet" type="text/css" href="css/cadastro.css">
    <link rel="icon" href="img/mashion_store.png" type="image/x-icon">
    <style>
        /* Estilos adicionais para o formulário */
        .tipo-cadastro {
            display: flex;
            justify-content: space-around;
            margin-bottom: 15px;
        }
        .linha-campos {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
        }
        .linha-campos input {
            flex: 1;
            margin-right: 5px;
        }
        .linha-campos input:last-child {
            margin-right: 0;
        }
        input[type="text"], input[type="email"], input[type="password"], input[type="date"] {
            font-size: 14px;
        }
        input[type="checkbox"] {
            margin-right: 5px;
        }
    </style>
    <script>
        function preencherEndereco() {
            const cep = document.getElementById('cep').value.replace(/\D/g, ''); // Remove caracteres não numéricos

            if (cep.length === 8) {
                fetch(`https://viacep.com.br/ws/${cep}/json/`)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Network response was not ok');
                        }
                        return response.json();
                    })
                    .then(data => {
                        if (!data.erro) {
                            document.getElementById('logradouro').value = data.logradouro;
                            document.getElementById('bairro').value = data.bairro;
                            document.getElementById('cidade').value = data.localidade;
                            document.getElementById('estado').value = data.uf;
                        } else {
                            alert('CEP não encontrado.');
                        }
                    })
                    .catch(error => {
                        console.error('Erro ao buscar o CEP:', error);
                        alert('Erro ao buscar o CEP. Tente novamente mais tarde.');
                    });
            } else {
                alert('CEP inválido. O CEP deve conter 8 dígitos.');
            }
        }
    </script>
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
    <p class="error-message"><%= errorMessage %></p>
    <%
        }
        if (successMessage != null) {
    %>
    <p class="success-message"><%= successMessage %></p>
    <%
        }
    %>

    <!-- Formulário de Cadastro -->
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
        <input type="text" id="cep" name="cep" placeholder="CEP" required value="<%= request.getParameter("cep") != null ? request.getParameter("cep") : "" %>" onblur="preencherEndereco()">
        <input type="text" id="logradouro" name="logradouro" placeholder="Logradouro" required value="<%= request.getParameter("logradouro") != null ? request.getParameter("logradouro") : "" %>">

        <div class="linha-campos">
            <input type="text" id="bairro" name="bairro" placeholder="Bairro" required value="<%= request.getParameter("bairro") != null ? request.getParameter("bairro") : "" %>" style="width: 32%;">
            <input type="text" id="cidade" name="cidade" placeholder="Cidade" required value="<%= request.getParameter("cidade") != null ? request.getParameter("cidade") : "" %>" style="width: 32%;">
            <input type="text" id="estado" name="estado" placeholder="Estado" required value="<%= request.getParameter("estado") != null ? request.getParameter("estado") : "" %>" style="width: 32%;">
        </div>

        <div class="linha-campos">
            <input type="text" id="numero" name="numero" placeholder="Número" required value="<%= request.getParameter("numero") != null ? request.getParameter("numero") : "" %>" style="width: 32%;">
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
