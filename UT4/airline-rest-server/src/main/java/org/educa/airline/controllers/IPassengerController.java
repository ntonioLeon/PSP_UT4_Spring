package org.educa.airline.controllers;

import org.educa.airline.dto.PassengerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IPassengerController {

    //POST
    public ResponseEntity<Void> associatePassengerToFlight(@PathVariable("cod")String cod, @RequestBody PassengerDTO passengerDTO);

    //GET
    public ResponseEntity<PassengerDTO> getAPassengerOfAFlight(@PathVariable("cod") String cod, @PathVariable("nif") String nif);

    //PUT
    public ResponseEntity<Void> updatePassengerInAFlight(@PathVariable("cod") String cod, @PathVariable("nif") String nif, @RequestBody PassengerDTO passengerDTO);

    //DELETE
    public ResponseEntity<Void> deletePassegerFromAFlight(@PathVariable("cod") String cod, @PathVariable("nif") String nif);

    //GET
    public ResponseEntity<List<PassengerDTO>> getAllPassengerOnAFlight(@PathVariable("cod") String cod);
}
