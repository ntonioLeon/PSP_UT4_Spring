package org.educa.airline.mappers;

import org.educa.airline.dto.UserDTO;
import org.educa.airline.entity.Role;
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
        return new User(userDTO.getUsername(), securityUtil.createHash(userDTO.getPassword()), securityUtil.crypt(validadorDeCampos.checkDni(userDTO.getNif())), userDTO.getName(), userDTO.getSurname(), securityUtil.crypt(userDTO.getEmail()), fromStringToRole(userDTO.getRoles()));
    }

    public UserDTO toDTO(User user) throws IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return new UserDTO(user.getUsername(), user.getPassword(), securityUtil.decrypt(user.getNif()), user.getName(), user.getSurname(), securityUtil.decrypt(user.getEmail()), fromRoleToString(user.getRoles()));
    }

    private List<Role> fromStringToRole(List<String> strRoles) {
        List<Role> roles = new ArrayList<>();
        for (String rol : strRoles) {
            switch (rol) {
                case "ROLE_admin":
                    roles.add(new Role(rol, "Administrador", "El que administra"));
                    break;
                case "ROLE_personal":
                    roles.add(new Role(rol, "Personal","El que personalea"));
                    break;
                case "ROLE_usuario":
                    roles.add(new Role(rol, "Usuario", "Es un usuario"));
                    break;
            }
        }
        return roles;
    }

    private List<String> fromRoleToString(List<Role> roles) {
        List<String> strRoles = new ArrayList<>();
        for (Role role : roles) {
            strRoles.add(role.getAuthority());
        }
        return strRoles;
    }
}
