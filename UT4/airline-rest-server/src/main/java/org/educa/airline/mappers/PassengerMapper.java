package org.educa.airline.mappers;

import org.educa.airline.dto.PassengerDTO;
import org.educa.airline.entity.Passenger;
import org.springframework.stereotype.Component;

@Component
public class PassengerMapper {
    public Passenger toEntity(PassengerDTO passengerDTO) {
        return new Passenger(passengerDTO.getNif(), passengerDTO.getFlightId(), passengerDTO.getName(), passengerDTO.getSurname(), passengerDTO.getEmail(), passengerDTO.getSeatNumber());
    }
}
