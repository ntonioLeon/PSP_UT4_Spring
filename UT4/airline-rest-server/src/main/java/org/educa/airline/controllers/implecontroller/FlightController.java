package org.educa.airline.controllers.implecontroller;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.educa.airline.controllers.IFlightController;
import org.educa.airline.dto.FlightDTO;
import org.educa.airline.entity.Flight;
import org.educa.airline.exceptions.MiValidacionException;
import org.educa.airline.exceptions.VueloYaExistente;
import org.educa.airline.mappers.FlightMapper;
import org.educa.airline.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/flights")
public class FlightController implements IFlightController {
    private final FlightService flightService;
    private final FlightMapper flightMapper;

    @Autowired
    public FlightController(FlightService flightService, FlightMapper flightMapper) {
        this.flightService = flightService;
        this.flightMapper = flightMapper;
    }

    /**
     * GET
     * @param origin
     * @param destination
     * @return
     */
    @Override
    @GetMapping(path = "")
    public ResponseEntity<List<FlightDTO>> getFlights(@RequestParam(value = "ori") String origin, @RequestParam(value = "des") String destination) {
        List<Flight> vuelos = flightService.obtenerVuelosPorOriYDes(origin, destination);
        if (!vuelos.isEmpty()) {
            return ResponseEntity.ok(flightMapper.toDTOs(vuelos));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET
     * @param cod
     * @param date
     * @return
     */
    @Override
    @GetMapping(path = "/{cod}")
    public ResponseEntity<FlightDTO> getOneFlight(@PathVariable("cod") String cod, @RequestParam(value = "date") String date) {
        Flight vuelo = null;
        try {
            vuelo = flightService.UnVueloPorFecha(cod, date);
        } catch (MiValidacionException e) {
            return ResponseEntity.notFound().build();
        }
        if (vuelo != null) {
            return ResponseEntity.ok(flightMapper.toDTO(vuelo));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST
     * @param flightDTO
     * @return
     */
    @Override
    @PostMapping(path = "/add")
    public ResponseEntity<Void> addAFlight(@Valid @RequestBody FlightDTO flightDTO) {
        try {
            if (flightService.agregar(flightMapper.toEntity(flightDTO))) {
                return ResponseEntity.status(201).build();
            } else {
                return ResponseEntity.status(412).build();
            }
        } catch (VueloYaExistente e) {
            return ResponseEntity.status(412).build();
        } catch (MiValidacionException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET
     * @param cod
     * @return
     */
    @Override
    @GetMapping(path = "/get/{cod}")
    public ResponseEntity<FlightDTO> getOneFlightbyId(@PathVariable("cod") String cod) {
        Flight vuelo = flightService.getAFlight(cod);
        if (vuelo != null) {
            return ResponseEntity.ok(flightMapper.toDTO(vuelo));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //PUT
    @Override
    @PutMapping(path = "/update/{cod}")
    public ResponseEntity<Void> updateFlight(@PathVariable("cod") String cod, @RequestBody FlightDTO flightDTO){
        try {
            if (flightService.update(cod, flightMapper.toEntity(flightDTO))) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MiValidacionException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //DELETE
    @Override
    @DeleteMapping(path = "/delete/{cod}")
    public ResponseEntity<Void> deleteFlight(@PathVariable("cod") String cod){
        if (flightService.delete(cod)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
