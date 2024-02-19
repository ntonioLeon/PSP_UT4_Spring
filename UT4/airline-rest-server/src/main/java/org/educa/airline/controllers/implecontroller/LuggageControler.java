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
    @GetMapping(path = "/fights/{cod}/passenger/{nif}/luggage/{id}")
    public ResponseEntity<LuggageDTO> getALulaggeFromAFlight(@PathVariable("id") int id, @PathVariable("cod") String cod, @PathVariable("nif") String nif) {
        return null;
    }

    //GET
    @Override
    @GetMapping(path = "/fights/{cod}/passengers/lugage")
    public ResponseEntity<List<LuggageDTO>> getAllLulaggesFromAFlight(@PathVariable("cod") String cod) {
        return null;
    }

    //POST
    @Override
    @PostMapping(path = "/fights/{cod}/passenger/{nif}/lugage")
    public ResponseEntity<Void> addALuggageFromAFlight(@PathVariable("cod") String cod, @PathVariable("nif") String nif, @RequestBody LuggageDTO luggageDTO) {
        return null;
    }

    //DELETE
    @Override
    @DeleteMapping(path = "/fights/{cod}/passenger/{nif}/luggage/{id}")
    public ResponseEntity<Void> deleteLuggageFromAFlight(@PathVariable("cod") String cod, @PathVariable("nif") String nif, @PathVariable("id") String id) {
        return null;
    }
}
