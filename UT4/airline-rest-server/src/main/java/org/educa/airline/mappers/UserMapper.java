package org.educa.airline.mappers;

import org.educa.airline.dto.UserDTO;
import org.educa.airline.entity.User;
import org.educa.airline.exceptions.MiValidacionException;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper extends Mapper {

    public User toEntity(UserDTO userDTO) throws MiValidacionException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, InvalidKeyException {
        return new User(userDTO.getUsername(), securityUtil.createHash(userDTO.getPassword()), securityUtil.crypt(validadorDeCampos.checkDni(userDTO.getNif())), userDTO.getName(), userDTO.getSurname(), securityUtil.crypt(userDTO.getEmail()), userDTO.getRoles());
    }

    public UserDTO toDTO(User user) {
        return new UserDTO(user.getUsername(), user.getPassword(), user.getNif(), user.getName(), user.getSurname(), user.getEmail(), user.getRoles());
    }
}
