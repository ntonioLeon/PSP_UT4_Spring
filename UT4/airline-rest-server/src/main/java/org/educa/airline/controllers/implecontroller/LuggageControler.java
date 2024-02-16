package org.educa.airline.controllers.implecontroller;

import org.educa.airline.controllers.ILuggageController;
import org.educa.airline.dto.LuggageDTO;
import org.educa.airline.mappers.LuggageMapper;
import org.educa.airline.services.LuggageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LuggageControler implements ILuggageController {

    private final LuggageService luggageService;
    private final LuggageMapper luggageMapper;

    @Autowired
    public LuggageControler(LuggageService luggageService, LuggageMapper luggageMapper) {
        this.luggageService = luggageService;
        this.luggageMapper = luggageMapper;
    }

    //GET
    @Override
    @GetMapping(path = "/fights/passengerslugage/one/{id}{cod}{nif}")
    public ResponseEntity<LuggageDTO> getALulaggeFromAFlight(@PathVariable("id") int id, @PathVariable("cod") String cod, @PathVariable("nif") String nif) {
        return null;
    }

    //GET
    @Override
    @GetMapping(path = "/fights/passengerslugage/all/{cod}")
    public ResponseEntity<List<LuggageDTO>> getAllLulaggesFromAFlight(@PathVariable("cod") String cod) {
        return null;
    }

    //POST
    @Override
    @PostMapping(path = "/fights/passengerslugage/add")
    public ResponseEntity<Void> addALuggageFromAFlight(@RequestBody LuggageDTO luggageDTO) {
        return null;
    }

    //PUT
    @Override
    @PutMapping(path = "/fights/passengerslugage/update")
    public ResponseEntity<Void> updateALuggageFromAFlight(@RequestBody LuggageDTO luggageDTO) {
        return null;
    }

    //DELETE
    @Override
    @DeleteMapping(path = "/fights/passengerslugage/delete/{id}{flightCod}")
    public ResponseEntity<Void> deleteLuggageFromAFlight(@PathVariable("id") int id, @PathVariable("flightCod") String flightCod) {
        return null;
    }
}
