package org.educa.airline.controllers;

import org.educa.airline.dto.UserDTO;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IUserController {

    //POST
    public RequestEntity<Void> createUser(@RequestBody UserDTO userDTO);

    //PUT
    public RequestEntity<Void> updateUser(@PathVariable("id") String id, @RequestBody UserDTO userDTO);

    //DELETE
    public RequestEntity<Void> deleteUse(@PathVariable("id") String id);

    //GET
    public RequestEntity<UserDTO> getUser(@PathVariable("id") String id);
}
