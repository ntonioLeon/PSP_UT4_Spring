package org.educa.airline.controllers;

import org.educa.airline.dto.LuggageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ILuggageController {

    //GET
    public ResponseEntity<LuggageDTO> getALulaggeFromAPassengerOnFlight(@PathVariable("id") int id, @PathVariable("cod") String cod, @PathVariable("nif") String nif);

    //GET
    public ResponseEntity<List<LuggageDTO>> getAllLuggagesFromPassengerOnAFlight(@PathVariable("cod") String cod, @PathVariable String nif);

    //GET
    public ResponseEntity<List<LuggageDTO>> getAllLuggagesFromAFlight(@PathVariable("cod") String cod);

    //POST
    public ResponseEntity<Void> addALuggageFromAFlight(@PathVariable("cod") String cod, @PathVariable("nif") String nif, @RequestBody LuggageDTO luggageDTO);

    //DELETE
    public ResponseEntity<Void> deleteALuggageFromAFlight(@PathVariable("cod") String cod, @PathVariable("nif") String nif, @PathVariable("id") String id);
}
