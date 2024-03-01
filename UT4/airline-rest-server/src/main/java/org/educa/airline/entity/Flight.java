package org.educa.airline.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    private String cod; //Identifica el vuelo
    private String id; //Identifica el avion
    private String origin;
    private String destination;
    private Date date;
}
