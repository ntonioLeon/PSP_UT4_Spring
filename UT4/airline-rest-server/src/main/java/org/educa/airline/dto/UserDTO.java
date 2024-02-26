package org.educa.airline.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.educa.airline.entity.Role;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String username;
    private String password;
    private String nif;
    private String name;
    private String surname;
    private String email;
    private List<Role> roles;
}
