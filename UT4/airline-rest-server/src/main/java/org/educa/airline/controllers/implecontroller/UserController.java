package org.educa.airline.controllers.implecontroller;

import org.educa.airline.controllers.IUserController;
import org.educa.airline.dto.UserDTO;
import org.educa.airline.exceptions.MiValidacionException;
import org.educa.airline.exceptions.NoTenesPoderAquiException;
import org.educa.airline.mappers.UserMapper;
import org.educa.airline.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserController implements IUserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    @Autowired
    UserController(UserServiceImpl userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    //POST
    @Override
    @PostMapping(path = "")
    public ResponseEntity<Void> createUser(@RequestBody UserDTO userDTO) {
        try {
            if (userService.create(userMapper.toEntity(userDTO))) {
                return ResponseEntity.status(201).build();
            } else {
                return ResponseEntity.status(409).build();
            }
        } catch (MiValidacionException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    //PUT
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable("id") String id, @RequestBody UserDTO userDTO) {
        try {
            if (userService.update(id, userMapper.toEntity(userDTO))) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MiValidacionException ex) {
            return ResponseEntity.badRequest().build();
        } catch (NoTenesPoderAquiException ex) {
            return ResponseEntity.status(403).build();
        }
    }

    //DELETE
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUse(@PathVariable("id") String id) {
        try {
            if (userService.delete(id)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NoTenesPoderAquiException ex) {
            return ResponseEntity.status(403).build();
        }
    }

    //GET
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") String id) {
        try {
            UserDTO userDTO = userMapper.toDTO(userService.getUser(id));
            if (userDTO != null) {
                return ResponseEntity.ok(userDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NoTenesPoderAquiException ex) {
            return ResponseEntity.status(403).build();
        }
    }
}
