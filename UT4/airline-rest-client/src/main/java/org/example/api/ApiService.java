package org.example.api;

import com.google.gson.Gson;

public abstract class ApiService {
    final String URL = "http://localhost:8080";
    final Connection connection = new Connection();
    final Gson gson = new Gson();
}
