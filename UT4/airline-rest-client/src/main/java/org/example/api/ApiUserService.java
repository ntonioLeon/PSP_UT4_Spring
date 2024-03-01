package org.example.api;

import org.example.dto.UserDTO;

public class ApiUserService extends ApiService {
    private final String URL = super.URL + "/user";
    public void createUser(UserDTO userDTO) throws Exception {
        connection.doPost(super.gson.toJson(userDTO), URL);
    }

    public void deleteUser(String id) throws Exception {
        connection.doDelete(URL+"/"+id);
    }

    public void updateUser(String id, UserDTO userDTO) throws Exception {
        connection.doUpdate(super.gson.toJson(userDTO), URL+"/"+id);
    }

    public UserDTO getUser(String id) throws Exception {
        return super.gson.fromJson(connection.doGet(URL+"/"+id), UserDTO.class);
    }
}
