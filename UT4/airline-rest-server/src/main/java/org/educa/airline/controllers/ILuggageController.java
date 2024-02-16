package org.educa.airline.controllers;

import org.educa.airline.dto.LuggageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ILuggageController {

    //GET
    public ResponseEntity<LuggageDTO> getALulaggeFromAFlight(@PathVariable("id") int id, @PathVariable("cod") String cod, @PathVariable("nif") String nif);

    //GET
    public ResponseEntity<List<LuggageDTO>> getAllLulaggesFromAFlight(@PathVariable("cod") String cod);

    //POST
    public ResponseEntity<Void> addALuggageFromAFlight(@RequestBody LuggageDTO luggageDTO); //Sacare el id de vuelo y el nif del propio objeto

    //PUT
    public ResponseEntity<Void> updateALuggageFromAFlight(@RequestBody LuggageDTO luggageDTO); //Sacare el id de vuelo y el nif del propio objeto

    //DELETE
    public ResponseEntity<Void> deleteLuggageFromAFlight(@PathVariable("id") int id, @PathVariable("cod") String cod);
}
