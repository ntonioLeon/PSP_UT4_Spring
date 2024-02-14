package org.educa.airline.controllers.implecontroller;

import jakarta.validation.Valid;
import org.educa.airline.controllers.IFlightController;
import org.educa.airline.dto.FlightDTO;
import org.educa.airline.dto.PassengerDTO;
import org.educa.airline.entity.Flight;
import org.educa.airline.exceptions.MiValidacionException;
import org.educa.airline.mappers.FlightMapper;
import org.educa.airline.mappers.PassengerMapper;
import org.educa.airline.services.FlightService;
import org.educa.airline.services.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/flights")
public class FlightController implements IFlightController {
    private FlightService flightService;
    private FlightMapper flightMapper;

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
    @GetMapping(path = "/flights{origin}{destination}")
    public ResponseEntity<List<FlightDTO>> getFlights(@PathVariable("origin") String origin, @PathVariable("destination") String destination) {
        List<Flight> vuelos = flightService.obtenerVuelosPorOriYDes(origin, destination);
        if (!vuelos.isEmpty()) {
            return ResponseEntity.ok(flightMapper.toDTOs(vuelos));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET
     * @param id_vuelo
     * @param date
     * @return
     */
    @Override
    @GetMapping(path = "/flights/{id_vuelo}{date}")
    public ResponseEntity<FlightDTO> getOneFlight(@PathVariable("id_vuelo") String id_vuelo, @PathVariable("date") Date date) {
        Flight vuelo = flightService.UnVueloPorFecha(id_vuelo, date);
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
    @PostMapping(path = "/flights/add")
    public ResponseEntity<Void> addAFlight(@Valid @RequestBody FlightDTO flightDTO) {
        if (flightService.agregar(flightMapper.toEntity(flightDTO))) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET
     * @param id_vuelo
     * @return
     */
    @Override
    @GetMapping(path = "/flights/get/{id_vuelo}")
    public ResponseEntity<FlightDTO> getOneFlightbyId(@PathVariable("id_vuelo") String id_vuelo) {
        Flight vuelo = flightService.getAFlight(id_vuelo);
        if (vuelo != null) {
            return ResponseEntity.ok(flightMapper.toDTO(vuelo));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //PUT
    @Override
    @PutMapping(path = "/flights/delete/{id_vuelo}")
    public ResponseEntity<Void> updateFlight(@PathVariable("id_vuelo") String id_vuelo, @RequestBody FlightDTO flightDTO){
        if (flightService.update(id_vuelo, flightMapper.toEntity(flightDTO))) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //DELETE
    @Override
    @DeleteMapping(path = "/flights/update/{id_vuelo}")
    public ResponseEntity<Void> deleteFlight(@PathVariable("id_vuelo") String id_vuelo){
        if (flightService.delete(id_vuelo)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
