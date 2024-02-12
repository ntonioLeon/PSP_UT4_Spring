package org.educa.airline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightDTO {
    private String id;
    private String origin;
    private String destination;
    private Date date;
}
