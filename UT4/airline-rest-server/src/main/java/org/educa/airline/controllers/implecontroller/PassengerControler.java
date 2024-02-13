package org.educa.airline.controllers.implecontroller;

import org.educa.airline.controllers.IPassengerController;
import org.educa.airline.dto.PassengerDTO;
import org.educa.airline.exceptions.MiValidacionException;
import org.educa.airline.mappers.PassengerMapper;
import org.educa.airline.services.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class PassengerControler implements IPassengerController {

    PassengerService passengerService;

    PassengerMapper passengerMapper;

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
        /*
        Se devolverá un HTTP Status CREATED si ha sido creado.
        Si no existe el vuelo se devolverá un NOT_FOUND
        Si hay algún tipo de error en la validación del pasajero se devolverá un HTTP Status BAD REQUEST
         */
    }
}
