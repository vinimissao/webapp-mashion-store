package com.example.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.Modelo.Cadastro;
import org.json.JSONObject;

public class CepService {

    public static Cadastro buscarCep(String cep) throws IOException {
        String apiUrl = "https://viacep.com.br/ws/" + cep + "/json/";
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // Lê a resposta da API
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String linha;
        while ((linha = reader.readLine()) != null) {
            response.append(linha);
        }
        reader.close();

        // Parseia a resposta JSON
        JSONObject json = new JSONObject(response.toString());

        // Extrai os campos necessários do JSON
        String logradouro = json.optString("logradouro", ""); // Usa optString para evitar erros caso o campo não exista
        String bairro = json.optString("bairro", "");
        String cidade = json.optString("localidade", "");
        String estado = json.optString("uf", "");

        // Retorna um novo objeto Cadastro com os dados obtidos
        Cadastro cadastro = new Cadastro(logradouro, bairro, cidade, estado, cep);

        return cadastro;
    }
}
