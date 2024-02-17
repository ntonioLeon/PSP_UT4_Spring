package org.educa.airline.mappers;

import org.educa.airline.dto.PassengerDTO;
import org.educa.airline.entity.Passenger;
import org.educa.airline.exceptions.MiValidacionException;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PassengerMapper extends Mapper{

    /**
     *
     * @param passengerDTO
     * @return
     */
    public Passenger toEntity(PassengerDTO passengerDTO) {
        return new Passenger(passengerDTO.getNif(), passengerDTO.getFlightCod(), passengerDTO.getName(), passengerDTO.getSurname(), passengerDTO.getEmail(), passengerDTO.getSeatNumber());
    }

    /**
     *
     * @param passenger
     * @return
     */
    public PassengerDTO toDTO(Passenger passenger) {
        return new PassengerDTO(passenger.getNif(), passenger.getFlightCod(), passenger.getName(), passenger.getSurname(), passenger.getEmail(), passenger.getSeatNumber());
    }

    /**
     *
     * @param passengers
     * @return
     */
    public List<PassengerDTO> toDTOs(List<Passenger> passengers) {
        List<PassengerDTO> passengerDTOs = new ArrayList<>();
        for (Passenger passenger : passengers) {
            passengerDTOs.add(new PassengerDTO(passenger.getNif(), passenger.getFlightCod(), passenger.getName(), passenger.getSurname(), passenger.getEmail(), passenger.getSeatNumber()));

        }
        return passengerDTOs;
    }

    /**
     *
     * @param passengersDTO
     * @return
     */
    public List<Passenger> toEntities(List<PassengerDTO> passengersDTO) {
        List<Passenger> passengers = new ArrayList<>();
        for (PassengerDTO passengerDTO : passengersDTO) {
            passengers.add(new Passenger(passengerDTO.getNif(), passengerDTO.getFlightCod(), passengerDTO.getName(), passengerDTO.getSurname(), passengerDTO.getEmail(), passengerDTO.getSeatNumber()));

        }
        return passengers;
    }

    private int toStringToInt(String seatNumber) throws MiValidacionException {
        if (validadorDeCampos.isANumber(seatNumber)) {
            return Integer.parseInt(seatNumber);
        } else {
            throw new MiValidacionException();
        }

    }

    private String toIntTOString(int seatNumber) {
        return String.valueOf(seatNumber);
    }
}
