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
    @PostMapping(path = "/flights/{cod}/passengers")
    public ResponseEntity<Void> associatePassengerToFlight(@PathVariable("cod")String cod, @RequestBody PassengerDTO passengerDTO) {
        try {
            if (passengerService.asociarVueloYPasagero(cod, passengerMapper.toEntity(passengerDTO))) {
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
     * @param cod
     * @param nif
     * @return
     */
    @Override
    @GetMapping(path = "/flights/{cod}/passengers/{nif}")
    public ResponseEntity<PassengerDTO> isAPassengerOnAFlight(@PathVariable("cod") String cod, @PathVariable("nif") String nif) {
        Passenger passenger = passengerService.getPassengerByIdAndNif(cod, nif);
        if (passenger != null) {
            try {
                return ResponseEntity.ok(passengerMapper.toDTO(passenger));
            } catch (MiValidacionException e) {
                return ResponseEntity.notFound().build();
            }
        } else {
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
    @PutMapping(path = "/flights/{cod}/passengers/{nif}")
    public ResponseEntity<Void> updatePassengerInAFlight(@PathVariable("cod") String cod, @PathVariable("nif") String nif, @RequestBody PassengerDTO passengerDTO) {
        try {
            if (passengerService.update(cod, nif, passengerMapper.toEntity(passengerDTO))) {
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
     * @param cod
     * @param nif
     * @return
     */
    @Override
    @DeleteMapping(path = "/flights/{cod}/passengers/{nif}")
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
    @GetMapping(path = "/flights/{cod}/passengers")
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
}
