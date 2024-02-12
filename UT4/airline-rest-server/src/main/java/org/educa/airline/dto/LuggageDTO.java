package org.educa.airline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LuggageDTO {
    private int id;
    private String nif;
    private String flightId;
    private String description;
}
