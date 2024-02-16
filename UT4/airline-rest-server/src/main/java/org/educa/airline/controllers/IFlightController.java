package org.educa.airline.controllers;

import org.educa.airline.dto.FlightDTO;
import org.educa.airline.dto.PassengerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public interface IFlightController {


    //GET
    public ResponseEntity<List<FlightDTO>> getFlights(@PathVariable("origin") String origin, @PathVariable("destination") String destination);

    //GET
    public ResponseEntity<FlightDTO> getOneFlight(@PathVariable("cod") String cod, @PathVariable("date") String date);

    //GET
    public ResponseEntity<FlightDTO> getOneFlightbyId(@PathVariable("cod") String cod);

    //POST
    public ResponseEntity<Void> addAFlight(@RequestBody FlightDTO flightDTO);

    //PUT
    public ResponseEntity<Void> updateFlight(@PathVariable("cod") String cod, @RequestBody FlightDTO flightDTO);

    //DELETE
    public ResponseEntity<Void> deleteFlight(@PathVariable("cod") String cod);
}
