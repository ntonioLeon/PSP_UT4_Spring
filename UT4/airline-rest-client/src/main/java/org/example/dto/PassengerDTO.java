package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDTO {
    private String nif;
    private String flightCod;
    private String name;
    private String surname;
    private String email;
    private String seatNumber;
}
