package org.educa.airline.controllers.implecontroller;

import org.educa.airline.controllers.ILuggageController;
import org.educa.airline.dto.LuggageDTO;
import org.educa.airline.entity.Luggage;
import org.educa.airline.exceptions.*;
import org.educa.airline.mappers.LuggageMapper;
import org.educa.airline.services.LuggageService;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(path = "/flights/{cod}/passengers/{nif}")
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
    @GetMapping(path = "/luggage/{id}")
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
    @GetMapping(path = "/luggages")
    public ResponseEntity<List<LuggageDTO>> getAllLulaggesFromAFlight(@PathVariable("cod") String cod, @PathVariable("nif") String nif) {
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
    @PostMapping(path = "/luggage")
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
    @DeleteMapping(path = "/luggage/{id}")
    public ResponseEntity<Void> deleteLuggageFromAFlight(@PathVariable("cod") String cod, @PathVariable("nif") String nif, @PathVariable("id") String id) {
        try {
            if (luggageService.deleteLuggage(cod, nif, Integer.parseInt(id))) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (LuggageNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NumberFormatException  e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
