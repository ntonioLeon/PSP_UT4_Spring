package org.educa.airline.controllers.implecontroller;

import org.educa.airline.controllers.IPassengerController;
import org.educa.airline.dto.PassengerDTO;
import org.educa.airline.entity.Passenger;
import org.educa.airline.exceptions.MiValidacionException;
import org.educa.airline.mappers.PassengerMapper;
import org.educa.airline.services.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PassengerControler implements IPassengerController {

    private final PassengerService passengerService;

    private final PassengerMapper passengerMapper;

    @Autowired
    PassengerControler(PassengerService passengerService, PassengerMapper passengerMapper) {
        this.passengerService = passengerService;
        this.passengerMapper = passengerMapper;
    }

    //POST
    @Override
    @PostMapping(path = "/flights/{id_vuelo}/passenger")
    public ResponseEntity<Void> associatePassengerToFlight(@PathVariable("id_vuelo")String id_vuelo, @RequestBody PassengerDTO passengerDTO) {
        try {
            if (passengerService.asociarVueloYPasagero(id_vuelo, passengerMapper.toEntity(passengerDTO))) {
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MiValidacionException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET
     * @param id_vuelo
     * @param nif
     * @return
     */
    @Override
    @GetMapping(path = "/flights/{id_vuelo}/passenger/{nif}")
    public ResponseEntity<PassengerDTO> isAPassengerOnAFlight(@PathVariable("id_vuelo") String id_vuelo, @PathVariable("nif") String nif) {
        Passenger passenger = passengerService.getPassengerByIdAndNif(id_vuelo, nif);
        if (passenger != null) {
            return ResponseEntity.ok(passengerMapper.toDTO(passenger));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * PUT
     * @param id_vuelo
     * @param nif
     * @param passengerDTO
     * @return
     */
    @Override
    @PutMapping(path = "/flights/{id_vuelo}/passenger/{nif}")
    public ResponseEntity<Void> updatePassengerInAFlight(@PathVariable("id_vuelo") String id_vuelo, @PathVariable("nif") String nif, @RequestBody PassengerDTO passengerDTO) {
        try {
            if (passengerService.update(id_vuelo, nif, passengerMapper.toEntity(passengerDTO))) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MiValidacionException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * DELETE
     * @param id_vuelo
     * @param nif
     * @return
     */
    @Override
    @DeleteMapping(path = "/flights/{id_vuelo}/passenger/{nif}")
    public ResponseEntity<Void> deletePassegerFromAFlight(@PathVariable("id_vuelo") String id_vuelo, @PathVariable("nif") String nif) {
        if (passengerService.delete(id_vuelo, nif)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET
     * @param id_vuelo
     * @return
     */
    @Override
    @GetMapping(path = "/flights/{id_vuelo}/passenger")
    public ResponseEntity<List<PassengerDTO>> getAllPassengerOnAFlight(@PathVariable("id_vuelo") String id_vuelo) {
        List<Passenger> passengers = passengerService.getAllPassengersOfAFlight(id_vuelo);
        if (!passengers.isEmpty()) {
            return ResponseEntity.ok(passengerMapper.toDTOs(passengers));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @GetMapping(path = "/flights/passenger/all")
    public ResponseEntity<List<PassengerDTO>> getAllPassenger() {
        List<Passenger> passengers = passengerService.getAllPassengers();
        if (!passengers.isEmpty()) {
            return ResponseEntity.ok(passengerMapper.toDTOs(passengers));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
