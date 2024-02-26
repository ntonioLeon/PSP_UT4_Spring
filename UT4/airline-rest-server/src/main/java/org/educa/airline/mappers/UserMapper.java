package org.educa.airline.mappers;

import org.educa.airline.dto.UserDTO;
import org.educa.airline.entity.User;
import org.educa.airline.exceptions.MiValidacionException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper extends Mapper {

    public User toEntity(UserDTO userDTO) throws MiValidacionException {
        return new User(userDTO.getUsername(), userDTO.getPassword(), userDTO.getNif(), userDTO.getName(), userDTO.getSurname(), userDTO.getEmail(), userDTO.getRoles());
    }

    public UserDTO toDTO(User user) {
        return new UserDTO(user.getUsername(), user.getPassword(), user.getNif(), user.getName(), user.getSurname(), user.getEmail(), user.getRoles());
    }
}
