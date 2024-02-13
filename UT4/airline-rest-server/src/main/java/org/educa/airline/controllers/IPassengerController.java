package org.educa.airline.controllers;

import org.educa.airline.dto.PassengerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IPassengerController {

    //POST
    public ResponseEntity<Void> associatePassengerToFlight(@PathVariable("id_vuelo")String id_vuelo, @RequestBody PassengerDTO passengerDTO);
}
