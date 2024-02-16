package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LuggageDTO {
    private int id;
    private String nif;
    private String flightCod;
    private String description;
}
