package org.educa.airline.controllers.implecontroller;

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

    //POST
    @Override
    @PostMapping(path = "/flights/add")
    public ResponseEntity<Void> addAFlight(@RequestBody FlightDTO flightDTO) {
        if (flightService.agregar(flightMapper.toEntity(flightDTO))) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @GetMapping(path = "/flight/get/{id_vuelo}")
    public ResponseEntity<FlightDTO> getOneFlightbyId(@PathVariable("id_vuelo") String id_vuelo) {
        Flight vuelo = flightService.getAFlight(id_vuelo);
        if (vuelo != null) {
            return ResponseEntity.ok(flightMapper.toDTO(vuelo));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @GetMapping(path = "/flights/{id_vuelo}/passenger/{nif}")
    public ResponseEntity<FlightDTO> isAPassengerOnAFlight(@PathVariable("id_vuelo") String id_vuelo, @PathVariable("nif") String nif) {
        return null;
    }

    @Override
    @PutMapping(path = "/flights/{id_vuelo}/passenger/{nif}")
    public ResponseEntity<Void> updatePassengerInAFlight(@PathVariable("id_vuelo") String id_vuelo, @PathVariable("nif") String nif, @RequestBody PassengerDTO passengerDTO) {
        return null;
    }

    @Override
    @DeleteMapping(path = "/flights/{id_vuelo}/passenger/{nif}")
    public ResponseEntity<Void> deletePassegerFromAFlight(@PathVariable("id_vuelo") String id_vuelo, @PathVariable("nif") String nif) {
        return null;
    }

    @Override
    @GetMapping(path = "/flights/{id_vuelo}/passenger")
    public ResponseEntity<List<PassengerDTO>> getAllPassengerOnAFlight(@PathVariable("id_vuelo") String id_vuelo) {
        return null;
    }
}
