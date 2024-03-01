package org.educa.airline.mappers;

import org.educa.airline.dto.PassengerDTO;
import org.educa.airline.entity.Passenger;
import org.educa.airline.exceptions.MiValidacionException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PassengerMapper extends Mapper{

    /**
     *
     * @param passengerDTO
     * @return
     */
    public Passenger toEntity(PassengerDTO passengerDTO) throws MiValidacionException{
        return new Passenger(validadorDeCampos.checkDni(passengerDTO.getNif()), passengerDTO.getFlightCod(), passengerDTO.getName(), passengerDTO.getSurname(), passengerDTO.getEmail(), fromStringToInt(passengerDTO.getSeatNumber()));
    }

    /**
     *
     * @param passenger
     * @return
     */
    public PassengerDTO toDTO(Passenger passenger) throws MiValidacionException{
        return new PassengerDTO(validadorDeCampos.checkDni(passenger.getNif()), passenger.getFlightCod(), passenger.getName(), passenger.getSurname(), passenger.getEmail(), fromIntTOString(passenger.getSeatNumber()));
    }

    /**
     *
     * @param passengers
     * @return
     */
    public List<PassengerDTO> toDTOs(List<Passenger> passengers) throws MiValidacionException {
        List<PassengerDTO> passengerDTOs = new ArrayList<>();
        for (Passenger passenger : passengers) {
            passengerDTOs.add(new PassengerDTO(validadorDeCampos.checkDni(passenger.getNif()), passenger.getFlightCod(), passenger.getName(), passenger.getSurname(), passenger.getEmail(), fromIntTOString(passenger.getSeatNumber())));

        }
        return passengerDTOs;
    }

    /**
     *
     * @param passengersDTO
     * @return
     */
    public List<Passenger> toEntities(List<PassengerDTO> passengersDTO) throws MiValidacionException {
        List<Passenger> passengers = new ArrayList<>();
        for (PassengerDTO passengerDTO : passengersDTO) {
            passengers.add(new Passenger(validadorDeCampos.checkDni(passengerDTO.getNif()), passengerDTO.getFlightCod(), passengerDTO.getName(), passengerDTO.getSurname(), passengerDTO.getEmail(), fromStringToInt(passengerDTO.getSeatNumber())));

        }
        return passengers;
    }

}
