package org.example.api;

import org.example.core.Cliente;
import org.example.exception.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class Connection {

    public String doGet(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Authorization", getBasicAuthenticationHeader(Cliente.userName, Cliente.password))
                .build();

        try (HttpClient client = HttpClient.newHttpClient()){
            HttpResponse<String> respuesta = client.send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println(respuesta.body());

            if (respuesta.statusCode() == 200) {
                return respuesta.body();
            } else if (respuesta.statusCode() == 404) {
                throw new NotFoundException();
            } else if (respuesta.statusCode() == 400) {
                throw new BadRequestException();
            } else if (respuesta.statusCode() == 403) {
                throw new NoTienesPermisoException();
            } else if (respuesta.statusCode() == 401) {
                throw new NoAutenticatedException();
            } else {
                throw new Exception("Error: " + respuesta.statusCode());
            }
        }
    }

    public void doPost(String body, String url) throws Exception {
        HttpRequest request;
        if ("".equals(Cliente.userName)  || "".equals(Cliente.password)) {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .header("Content-Type", "application/json")
                    .build();
        } else {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .header("Content-Type", "application/json")
                    .header("Authorization", getBasicAuthenticationHeader(Cliente.userName, Cliente.password))
                    .build();
        }
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> respuesta = client.send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println(respuesta.body());

            if (respuesta.statusCode() == 201) {

            }else if (respuesta.statusCode() == 400) {
                throw new BadRequestException();
            } else if (respuesta.statusCode() == 404) {
                throw new NotFoundException();
            }else if (respuesta.statusCode() == 409) {
                throw new YaExisteException();
            } else if (respuesta.statusCode() == 403) {
                throw new NoTienesPermisoException();
            } else if (respuesta.statusCode() == 401) {
                throw new NoAutenticatedException();
            } else {
                throw new Exception();
            }
        }
    }

    public void doPostUser(String body, String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .header("Content-Type", "application/json")
                .build();

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> respuesta = client.send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println(respuesta.body());

            if (respuesta.statusCode() == 201) {

            }else if (respuesta.statusCode() == 400) {
                throw new BadRequestException();
            } else if (respuesta.statusCode() == 404) {
                throw new NotFoundException();
            }else if (respuesta.statusCode() == 409) {
                throw new YaExisteException();
            } else if (respuesta.statusCode() == 403) {
                throw new NoTienesPermisoException();
            } else if (respuesta.statusCode() == 401) {
                throw new NoAutenticatedException();
            } else {
                throw new Exception();
            }
        }
    }

    public void doUpdate(String body, String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .PUT(HttpRequest.BodyPublishers.ofString(body))
                .header("Content-Type", "application/json")
                .header("Authorization", getBasicAuthenticationHeader(Cliente.userName, Cliente.password))
                .build();

        try (HttpClient client = HttpClient.newHttpClient()){
            HttpResponse<String> respuesta = client.send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println(respuesta.body());

            if (respuesta.statusCode() == 200) {

            } else if (respuesta.statusCode() == 409) {
                throw new YaExisteException();
            } else if (respuesta.statusCode() == 404) {
                throw new NotFoundException();
            } else if (respuesta.statusCode() == 400) {
                throw new BadRequestException();
            } else if (respuesta.statusCode() == 403) {
                throw new NoTienesPermisoException();
            } else if (respuesta.statusCode() == 401) {
                throw new NoAutenticatedException();
            }  else {
                throw new Exception("Error: " + respuesta.statusCode());
            }
        }
    }

    public void doDelete(String url) throws Exception  {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE()
                .header("Authorization", getBasicAuthenticationHeader(Cliente.userName, Cliente.password))
                .build();

        try (HttpClient client = HttpClient.newHttpClient()){
            HttpResponse<String> respuesta = client.send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println(respuesta.body());

            if (respuesta.statusCode() == 200) {

            } else if (respuesta.statusCode() == 404) {
                throw new NotFoundException();
            } else if (respuesta.statusCode() == 400) {
                throw new BadRequestException();
            } else if (respuesta.statusCode() == 403) {
                throw new NoTienesPermisoException();
            } else if (respuesta.statusCode() == 401) {
                throw new NoAutenticatedException();
            }  else {
                throw new Exception();
            }
        }
    }

    private static String getBasicAuthenticationHeader(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }
}
