package org.educa.airline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightDTO {
    private String cod;
    private String id;
    private String origin;
    private String destination;
    private String date;
}
