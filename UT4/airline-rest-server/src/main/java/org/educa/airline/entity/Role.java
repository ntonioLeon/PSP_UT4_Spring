package org.educa.airline.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {
    private String rol;
    private String name;
    private String description;

    @Override
    public String getAuthority() {
        return rol;
    }

    public Role (Role role) {
        this.rol = role.getRol();
        this.name = role.getName();
        this.description = role.getDescription();
    }
}
