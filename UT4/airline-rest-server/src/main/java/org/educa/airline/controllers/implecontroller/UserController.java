package org.educa.airline.controllers.implecontroller;

import jakarta.validation.Valid;
import org.educa.airline.controllers.IUserController;
import org.educa.airline.dto.UserDTO;
import org.educa.airline.exceptions.MiValidacionException;
import org.educa.airline.exceptions.NoAutenticadoException;
import org.educa.airline.exceptions.NoTenesPoderAquiException;
import org.educa.airline.exceptions.user.UserDuplicatedException;
import org.educa.airline.exceptions.user.UserNotFoundException;
import org.educa.airline.mappers.UserMapper;
import org.educa.airline.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(path = "/user")
public class UserController implements IUserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    /**
     * Metodo que crea usuarios. Controla los duplicados por medio del servuce.
     * @param userDTO a agregar en el repositotio
     * @return Un response entity con la respuesta que se desea dar al cliente.
     */
    @Override
    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserDTO userDTO) {
        try {
            if (userService.create(userMapper.toEntity(userDTO))) {
                return ResponseEntity.status(201).build();
            } else {
                return ResponseEntity.status(409).build();
            }
        } catch (MiValidacionException | NoSuchAlgorithmException | IllegalBlockSizeException | NoSuchPaddingException |
                 BadPaddingException | InvalidKeyException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Metodo que actualiza la informacion de un usuario
     * @param id del user a remplazar
     * @param userDTO los nuevos datos del user.
     * @return Un response entity con la respuesta que se desea dar al cliente.
     */
    @Override
    @PutMapping(path = "/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable("id") String id, @Valid @RequestBody UserDTO userDTO) {
        try {
            if (userService.update(id, userMapper. toEntity(userDTO))) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MiValidacionException | NoSuchAlgorithmException | IllegalBlockSizeException | NoSuchPaddingException |
                 BadPaddingException | InvalidKeyException ex) {
            return ResponseEntity.badRequest().build();
        } catch (NoTenesPoderAquiException ex) {
            return ResponseEntity.status(403).build();
        } catch (UserDuplicatedException e) {
            return ResponseEntity.status(409).build();
        } catch (NoAutenticadoException ex) {
            return ResponseEntity.status(401).build();
        }
    }

    /**
     * Metodo que borra a un usuario del repositorio
     * @param id del user
     * @return Un response entity con la respuesta que se desea dar al cliente.
     */
    @Override
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUse(@PathVariable("id") String id) {
        try {
            if (userService.delete(id)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NoTenesPoderAquiException ex) {
            return ResponseEntity.status(403).build();
        } catch (NoAutenticadoException ex) {
            return ResponseEntity.status(401).build();
        }
    }

    /**
     * Metodo que busca un usuario en el repositorio
     * @param id del user
     * @return Un response entity con el DTO del user al que se busca.
     */
    @Override
    @GetMapping(path = "/{id}")
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
        } catch (IllegalBlockSizeException | NoSuchPaddingException | BadPaddingException | NoSuchAlgorithmException |
                 InvalidKeyException e) {
            return ResponseEntity.status(400).build();
        } catch (UserNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (NoAutenticadoException ex) {
            return ResponseEntity.status(401).build();
        }
    }
}
