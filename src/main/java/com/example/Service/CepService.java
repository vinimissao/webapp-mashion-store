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

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String linha;
        while ((linha = reader.readLine()) != null) {
            response.append(linha);
        }
        reader.close();

        JSONObject json = new JSONObject(response.toString());

        String logradouro = json.optString("logradouro", "");
        String bairro = json.optString("bairro", "");
        String cidade = json.optString("localidade", "");
        String estado = json.optString("uf", "");

        Cadastro cadastro = new Cadastro(logradouro, cidade, cidade, bairro, cep);

        return cadastro;
    }
}
