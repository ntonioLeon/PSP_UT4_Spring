package org.educa.airline.controllers.implecontroller;

import org.educa.airline.controllers.IUserController;
import org.educa.airline.dto.UserDTO;
import org.educa.airline.services.UserServiceImpl;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserController implements IUserController {

    private final UserServiceImpl userService;

    UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    //POST
    @Override
    @PostMapping(path = "")
    public RequestEntity<Void> createUser(@RequestBody UserDTO userDTO) {
        return null;
    }

    //PUT
    @Override
    @PutMapping("/{id}")
    public RequestEntity<Void> updateUser(@PathVariable("id") String id, @RequestBody UserDTO userDTO) {
        return null;
    }

    //DELETE
    @Override
    @DeleteMapping("/{id}")
    public RequestEntity<Void> deleteUse(@PathVariable("id") String id) {
        return null;
    }

    //GET
    @Override
    @GetMapping("/{id}")
    public RequestEntity<UserDTO> getUser(@PathVariable("id") String id) {
        return null;
    }
}
