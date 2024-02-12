package org.educa.airline.controllers.implecontroller;

import org.educa.airline.controllers.IController;
import org.educa.airline.dto.FlightDTO;
import org.educa.airline.dto.PassengerDTO;
import org.educa.airline.mappers.FlightMapper;
import org.educa.airline.mappers.LuggageMapper;
import org.educa.airline.mappers.PassengerMapper;
import org.educa.airline.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/flights")
public class FlightController implements IController {


    private FlightService flightService;
    private FlightMapper flightMapper;
    private LuggageMapper luggageMapper;
    private PassengerMapper passengerMapper;

    @Autowired
    public FlightController(FlightService flightService, FlightMapper flightMapper, LuggageMapper luggageMapper, PassengerMapper passengerMapper) {
        this.flightService = flightService;
        this.flightMapper = flightMapper;
        this.luggageMapper = luggageMapper;
        this.passengerMapper = passengerMapper;
    }

    @Override
    @GetMapping(path = "{origin}?{destination}")
    public ResponseEntity<List<FlightDTO>> getFlights(@PathVariable("origin") String origin, @PathVariable("destination") String destination) {
        return null;
    }

    @Override
    @GetMapping(path = "/{id_vuelo}?{date}")
    public ResponseEntity<FlightDTO> getOneFlight(@PathVariable("id_vuelo") String id_vuelo, @PathVariable("date") Date date) {
        return null;
    }

    @Override
    @PostMapping(path = "/{id_vuelo}/passenger")
    public ResponseEntity<Void> associatePassengerToFlight(@PathVariable("id_vuelo")String id_vuelo, @RequestBody PassengerDTO passengerDTO) {
        return null;
    }

    @Override
    @GetMapping(path = "/{id_vuelo}/passenger/{nif}")
    public ResponseEntity<FlightDTO> isAPassengerOnAFlight(@PathVariable("id_vuelo") String id_vuelo, @PathVariable("nif") String nif) {
        return null;
    }

    @Override
    @PutMapping(path = "/{id_vuelo}/passenger/{nif}")
    public ResponseEntity<Void> updatePassengerInAFlight(@PathVariable("id_vuelo") String id_vuelo, @PathVariable("nif") String nif, @RequestBody PassengerDTO passengerDTO) {
        return null;
    }

    @Override
    @DeleteMapping(path = "/{id_vuelo}/passenger/{nif}")
    public ResponseEntity<Void> deletePassegerFromAFlight(@PathVariable("id_vuelo") String id_vuelo, @PathVariable("nif") String nif) {
        return null;
    }

    @Override
    @GetMapping(path = "/{id_vuelo}/passenger")
    public ResponseEntity<List<PassengerDTO>> getAllPassengerOnAFlight(@PathVariable("id_vuelo") String id_vuelo) {
        return null;
    }
}
