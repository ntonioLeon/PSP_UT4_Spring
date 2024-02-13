package org.educa.airline.controllers;

import org.educa.airline.dto.FlightDTO;
import org.educa.airline.dto.PassengerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IPassengerController {

    //POST
    public ResponseEntity<Void> associatePassengerToFlight(@PathVariable("id_vuelo")String id_vuelo, @RequestBody PassengerDTO passengerDTO);

    //GET
    public ResponseEntity<FlightDTO> isAPassengerOnAFlight(@PathVariable("id_vuelo") String id_vuelo, @PathVariable("nif") String nif);

    //PUT
    public ResponseEntity<Void> updatePassengerInAFlight(@PathVariable("id_vuelo") String id_vuelo, @PathVariable("nif") String nif, @RequestBody PassengerDTO passengerDTO);

    //DELETE
    public ResponseEntity<Void> deletePassegerFromAFlight(@PathVariable("id_vuelo") String id_vuelo, @PathVariable("nif") String nif);

    //GET
    public ResponseEntity<List<PassengerDTO>> getAllPassengerOnAFlight(@PathVariable("id_vuelo") String id_vuelo);
}
