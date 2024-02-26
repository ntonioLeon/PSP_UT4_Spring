package org.educa.airline.controllers;

import org.educa.airline.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IUserController {

    //POST
    public ResponseEntity<Void> createUser(@RequestBody UserDTO userDTO);

    //PUT
    public ResponseEntity<Void> updateUser(@PathVariable("id") String id, @RequestBody UserDTO userDTO);

    //DELETE
    public ResponseEntity<Void> deleteUse(@PathVariable("id") String id);

    //GET
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") String id);
}
