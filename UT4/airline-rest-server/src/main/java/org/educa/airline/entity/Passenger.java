package org.educa.airline.entity;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {
    private String nif;
    private String flightCod;
    private String name;
    private String surname;
    @Email
    private String email;
    private int seatNumber;
}
