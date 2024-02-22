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
     * Metodo que lista todos los vuelo con el mismo destino y los devuelve
     * @param origin del vuelo.
     * @param destination del vuelo.
     * @return una lista con los vuelos que compartan ese destino y origen.
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
     * Metodo que busca un vuelo con el id y date y lo devuelve
     * @param cod del vuelo a buscar
     * @param date la fecha de buequeda
     * @return El vuelo correspondiente al codigo y fecha
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
     * metodo que valida los campos de un vuelo, comprueba que no exista y lo agrega
     * @param flightDTO del vuelo
     * @return El status code correspondiente al outcome de la accion (ok, created, not found, badRequest, duplicated...)
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

    /**
     * Metodo que valida los campos de un pasajero, comprueba la existencia del vuelo y crea el pasajero
     * @param cod del vuelo donde crearlo
     * @param passengerDTO pasajero a crear
     * @return El status code correspondiente al outcome de la accion (ok, created, not found, badRequest, duplicated...)
     */
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
     * Metodo que busca un pasajero por el cod de vuelo y el nif y lo devuelve
     * @param cod del vuelo
     * @param nif del pasajero
     * @return pasajero a devolver
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
     * Metodo que actualiza la informacion de un pasajero (pasa las pruebas de creacion)
     * @param cod del vuelo (dato antiguo)
     * @param nif del pasajero (dato antiguo)
     * @param passengerDTO pasajero nuevo para updatear
     * @return El status code correspondiente al outcome de la accion (ok, created, not found, badRequest, duplicated...)
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
     * Metodo que elimina un pasajero y cualquier equipaje que este pudiese tener
     * @param cod del vuelo
     * @param nif del pasajero
     * @return El status code correspondiente al outcome de la accion (ok, created, not found, badRequest, duplicated...)
     */
    @Override
    @DeleteMapping(path = "/{cod}/passenger/{nif}")
    public ResponseEntity<Void> deletePassegerFromAFlight(@PathVariable("cod") String cod, @PathVariable("nif") String nif) {
        if (passengerService.delete(cod, nif)) {
            if (!luggageService.getAllLuggageFromAPassengerOnAFlight(cod, nif).isEmpty()) {
                try {
                    luggageService.deleteAllLuggagesFromAPassenger(cod, nif);
                } catch (LuggageNotFoundException e) {
                    return ResponseEntity.notFound().build();
                }
            }
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * metodo que lista todos los equipajes de un vuelo.
     * @param cod del vuelo
     * @return una lista con los pasajero del vuelo
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

    /**
     * metodo que busca un equipaje de un pasajero en un vuelo
     * @param id del equipaje
     * @param cod del vuelo
     * @param nif del pasajero
     * @return un equipaje
     */
    @Override
    @GetMapping(path = "/{cod}/passengers/{nif}/luggage/{id}")
    public ResponseEntity<LuggageDTO> getALulaggeFromAPassengerOnFlight(@PathVariable("id") int id, @PathVariable("cod") String cod, @PathVariable("nif") String nif) {
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

    /**
     * metodo que lista los equipajes de un pasajero en un vuelo
     * @param cod del vuelo
     * @param nif del pasajero
     * @return Una lista de quipajes
     */
    @Override
    @GetMapping(path = "/{cod}/passengers/{nif}/luggages")
    public ResponseEntity<List<LuggageDTO>> getAllLuggagesFromPassengerOnAFlight(@PathVariable("cod") String cod, @PathVariable("nif") String nif) {
        List<Luggage> luggages = luggageService.getAllLuggageFromAPassengerOnAFlight(cod, nif);
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

    /**
     * Metodo que lista todos los equipajes de un vuelo
     * @param cod del vuelo
     * @return Una lista de equipajes
     */
    @GetMapping(path = "/{cod}/passengers/luggages")
    public ResponseEntity<List<LuggageDTO>> getAllLuggagesFromAFlight(@PathVariable("cod") String cod) {
        List<Luggage> luggages = luggageService.getAllLuggagesOfAFlights(cod);
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

    /**
     * Metodo que crea un equipaje. (pruebas de que un equipaje ya existe, vuelo y pasajero valido..)
     * @param cod del vuelo
     * @param nif del pasajero
     * @param luggageDTO El json correspondiente al equipaje
     * @return El status code correspondiente al outcome de la accion (ok, created, not found, badRequest, duplicated...)
     */
    @Override
    @PostMapping(path = "/{cod}/passengers/{nif}/luggage")
    public ResponseEntity<Void> addALuggageFromAFlight(@PathVariable("cod") String cod, @PathVariable("nif") String nif, @RequestBody LuggageDTO luggageDTO) {
        try {
            if (flightService.getAFlight(cod) && passengerService.existPassenger(cod, nif)) {
                if (luggageService.create(cod, nif, luggageMapper.toEntity(luggageDTO))) {
                    return ResponseEntity.status(201).build();
                } else {
                    return ResponseEntity.status(409).build();
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MiValidacionException e) {
            return ResponseEntity.badRequest().build();
        } catch (LuggageYaExisteException e) {
            return ResponseEntity.status(409).build();
        } catch (FlightNotFoundException | PassengerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * metodo que borra un equipaje
     * @param cod del vuelo
     * @param nif del pasajero
     * @param id del Luggage
     * @return El status code correspondiente al outcome de la accion (ok, created, not found, badRequest, duplicated...)
     */
    @Override
    @DeleteMapping(path = "/{cod}/passengers/{nif}/luggage/{id}")
    public ResponseEntity<Void> deleteALuggageFromAFlight(@PathVariable("cod") String cod, @PathVariable("nif") String nif, @PathVariable("id") String id) {
        try {
            if (flightService.getAFlight(cod) && passengerService.existPassenger(cod, nif)) {
                if (luggageService.deleteLuggage(cod, nif, Integer.parseInt(id))) {
                    return ResponseEntity.ok().build();
                } else {
                    return ResponseEntity.badRequest().build();
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (FlightNotFoundException | PassengerNotFoundException | LuggageNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NumberFormatException  e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
