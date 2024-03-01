package org.educa.airline.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String nif;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @Email
    @NotNull
    private String email;
    @NotNull
    @NotEmpty
    private List<String> roles;
}
