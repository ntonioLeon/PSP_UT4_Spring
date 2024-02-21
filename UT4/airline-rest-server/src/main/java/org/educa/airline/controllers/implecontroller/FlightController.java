package org.educa.airline.controllers.implecontroller;

import jakarta.validation.Valid;
import org.educa.airline.controllers.IFlightController;
import org.educa.airline.controllers.ILuggageController;
import org.educa.airline.controllers.IPassengerController;
import org.educa.airline.dto.FlightDTO;
import org.educa.airline.dto.LuggageDTO;
import org.educa.airline.dto.PassengerDTO;
import org.educa.airline.entity.Flight;
import org.educa.airline.entity.Luggage;
import org.educa.airline.entity.Passenger;
import org.educa.airline.exceptions.*;
import org.educa.airline.exceptions.flight.FlightNotFoundException;
import org.educa.airline.exceptions.flight.FilightYaExistenteException;
import org.educa.airline.exceptions.luggage.LuggageNotFoundException;
import org.educa.airline.exceptions.luggage.LuggageYaExisteException;
import org.educa.airline.exceptions.passenger.PassengerNotFoundException;
import org.educa.airline.mappers.FlightMapper;
import org.educa.airline.mappers.LuggageMapper;
import org.educa.airline.mappers.PassengerMapper;
import org.educa.airline.services.FlightService;
import org.educa.airline.services.LuggageService;
import org.educa.airline.services.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/flights")
public class FlightController implements IFlightController, IPassengerController, ILuggageController {
    private final FlightService flightService;
    private final FlightMapper flightMapper;
    private final PassengerService passengerService;
    private final PassengerMapper passengerMapper;
    private final LuggageService luggageService;
    private final LuggageMapper luggageMapper;

    @Autowired
    public FlightController(FlightService flightService, FlightMapper flightMapper, PassengerService passengerService, PassengerMapper passengerMapper, LuggageService luggageService, LuggageMapper luggageMapper) {
        this.flightService = flightService;
        this.flightMapper = flightMapper;
        this.passengerService = passengerService;
        this.passengerMapper = passengerMapper;
        this.luggageService = luggageService;
        this.luggageMapper = luggageMapper;
    }

    /**
     * GET
     * @param origin
     * @param destination
     * @return
     */
    @Override
    @GetMapping(path = "")
    public ResponseEntity<List<FlightDTO>> getFlightsByOriAndDes(@RequestParam(value = "ori") String origin, @RequestParam(value = "des") String destination) {
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
    public ResponseEntity<FlightDTO> getOneFlightByDate(@PathVariable("cod") String cod, @RequestParam(value = "date") String date) {
        try {
            return ResponseEntity.ok(flightMapper.toDTO(flightService.UnVueloPorFecha(cod, date)));
        } catch (MiValidacionException  e) {
            return ResponseEntity.badRequest().build();
        } catch (FlightNotFoundException e) {
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
                return ResponseEntity.status(409).build();  //Duplicado
            }
        } catch (MiValidacionException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //----------------------------------PASSENGERS------------------------------------------------------------

    //POST
    @Override
    @PostMapping(path = "/{cod}/passenger")
    public ResponseEntity<Void> associatePassengerToFlight(@PathVariable("cod")String cod, @RequestBody PassengerDTO passengerDTO) {
        try {
            if (flightService.getAFlight(cod)) {
                if (passengerService.asociarVueloYPasagero(cod, passengerMapper.toEntity(passengerDTO))) {
                    return ResponseEntity.status(201).build();
                } else {
                    return ResponseEntity.status(409).build();  //Duplicado
                }
            }  //El else es un throw.
        } catch (MiValidacionException ex) {
            return ResponseEntity.badRequest().build();
        } catch (FlightNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * GET
     * @param cod
     * @param nif
     * @return
     */
    @Override
    @GetMapping(path = "/{cod}/passenger/{nif}")
    public ResponseEntity<PassengerDTO> getAPassengerOfAFlight(@PathVariable("cod") String cod, @PathVariable("nif") String nif) {
        try {
            return ResponseEntity.ok(passengerMapper.toDTO(passengerService.getPassengerByIdAndNif(cod, nif)));
        } catch (MiValidacionException e) {
            return ResponseEntity.badRequest().build();
        } catch (PassengerNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * PUT
     * @param cod
     * @param nif
     * @param passengerDTO
     * @return
     */
    @Override
    @PutMapping(path = "/{cod}/passenger/{nif}")
    public ResponseEntity<Void> updatePassengerInAFlight(@PathVariable("cod") String cod, @PathVariable("nif") String nif, @RequestBody PassengerDTO passengerDTO) {
        try {
            if (passengerService.update(cod, nif, passengerMapper.toEntity(passengerDTO))) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MiValidacionException  ex) {
            return ResponseEntity.badRequest().build();
        } catch (PassengerNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE
     * @param cod
     * @param nif
     * @return
     */
    @Override
    @DeleteMapping(path = "/{cod}/passenger/{nif}")
    public ResponseEntity<Void> deletePassegerFromAFlight(@PathVariable("cod") String cod, @PathVariable("nif") String nif) {
        if (passengerService.delete(cod, nif)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET
     * @param cod
     * @return
     */
    @Override
    @GetMapping(path = "/{cod}/passengers")
    public ResponseEntity<List<PassengerDTO>> getAllPassengerOnAFlight(@PathVariable("cod") String cod) {
        List<Passenger> passengers = passengerService.getAllPassengersOfAFlight(cod);
        if (!passengers.isEmpty()) {
            try {
                return ResponseEntity.ok(passengerMapper.toDTOs(passengers));
            } catch (MiValidacionException e) {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //---------------------------------Luggage-------------------------------------------------------------

    @Override
    @GetMapping(path = "/{cod}/passengers/{nif}/luggage/{id}")
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
    @GetMapping(path = "/{cod}/passengers/{nif}/luggages")
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
    @PostMapping(path = "/{cod}/passengers/{nif}/luggage")
    public ResponseEntity<Void> addALuggageFromAFlight(@PathVariable("cod") String cod, @PathVariable("nif") String nif, @RequestBody LuggageDTO luggageDTO) {
        try {
            if (luggageService.create(cod, nif, luggageMapper.toEntity(luggageDTO))) {
                return ResponseEntity.status(201).build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (MiValidacionException e) {
            return ResponseEntity.badRequest().build();
        } catch (LuggageYaExisteException e) {
            return ResponseEntity.status(409).build();
        }
    }

    //DELETE
    @Override
    @DeleteMapping(path = "/{cod}/passengers/{nif}/luggage/{id}")
    public ResponseEntity<Void> deleteALuggageFromAFlight(@PathVariable("cod") String cod, @PathVariable("nif") String nif, @PathVariable("id") String id) {
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
