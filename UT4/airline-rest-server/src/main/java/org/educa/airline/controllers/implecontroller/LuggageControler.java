package org.educa.airline.controllers.implecontroller;

import org.educa.airline.controllers.ILuggageController;
import org.educa.airline.dto.LuggageDTO;
import org.educa.airline.entity.Luggage;
import org.educa.airline.exceptions.FlightNotFoundException;
import org.educa.airline.exceptions.LuggageYaExisteException;
import org.educa.airline.exceptions.MiValidacionException;
import org.educa.airline.exceptions.PassengerNotFoundException;
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
    @GetMapping(path = "/fights/{cod}/passengers/{nif}/luggages/{id}")
    public ResponseEntity<LuggageDTO> getALulaggeFromAFlight(@PathVariable("id") int id, @PathVariable("cod") String cod, @PathVariable("nif") String nif) {
        Luggage luggage = luggageService.getALuggageFromAFlight(id, cod, nif);
        try {
            if (luggage != null) {
                return ResponseEntity.ok(luggageMapper.toDTO(luggage));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MiValidacionException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //GET
    @Override
    @GetMapping(path = "/fights/{cod}/passengers/{nif}/luggages")
    public ResponseEntity<List<LuggageDTO>> getAllLulaggesFromAFlight(@PathVariable("cod") String cod, @PathVariable String nif) {
        List<Luggage> luggages = luggageService.getAllLuggageFromAFlight(cod, nif);
        try {
            if (!luggages.isEmpty()) {
                return ResponseEntity.ok(luggageMapper.toDTOs(luggages));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MiValidacionException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //POST
    @Override
    @PostMapping(path = "/fights/{cod}/passengers/{nif}/luggages")
    public ResponseEntity<Void> addALuggageFromAFlight(@PathVariable("cod") String cod, @PathVariable("nif") String nif, @RequestBody LuggageDTO luggageDTO) {
        try {
            if (luggageService.create(cod, nif, luggageMapper.toEntity(luggageDTO))) {
                return ResponseEntity.status(201).build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (FlightNotFoundException e) {
            return ResponseEntity.notFound().build();
        }catch (PassengerNotFoundException e) {
            return ResponseEntity.status(420).build();
        } catch (MiValidacionException e) {
            return ResponseEntity.badRequest().build();
        } catch (LuggageYaExisteException e) {
            return ResponseEntity.status(412).build();
        }
    }

    //DELETE
    @Override
    @DeleteMapping(path = "/fights/{cod}/passenger/{nif}/luggage/{id}")
    public ResponseEntity<Void> deleteLuggageFromAFlight(@PathVariable("cod") String cod, @PathVariable("nif") String nif, @PathVariable("id") String id) {
        return null;
    }
}
