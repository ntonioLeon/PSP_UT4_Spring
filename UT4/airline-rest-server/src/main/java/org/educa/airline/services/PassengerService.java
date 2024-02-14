package org.educa.airline.services;

import org.educa.airline.dto.PassengerDTO;
import org.educa.airline.entity.Passenger;
import org.educa.airline.exceptions.MiValidacionException;
import org.educa.airline.repository.inmemory.InMemoryFlightRepository;
import org.educa.airline.repository.inmemory.InMemoryPassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {

    InMemoryPassengerRepository inMemoryPassengerRepository;
    InMemoryFlightRepository inMemoryFlightRepository;


    @Autowired
    PassengerService(InMemoryPassengerRepository inMemoryPassengerRepository, InMemoryFlightRepository inMemoryFlightRepository) {
        this.inMemoryPassengerRepository = inMemoryPassengerRepository;
        this.inMemoryFlightRepository = inMemoryFlightRepository;
    }

    public boolean asociarVueloYPasagero(String idVuelo, Passenger passenger) throws MiValidacionException {
        if (inMemoryFlightRepository.getFlight(idVuelo) != null) {
            if (!inMemoryPassengerRepository.addPassenger(passenger)) {
                throw new MiValidacionException();
            }
        }
        return false;
    }

    public Passenger getPassengerByIdAndNif(String idVuelo, String nif) {
        return inMemoryPassengerRepository.getPassenger(idVuelo, nif);
    }

    public boolean update(String idVuelo, String nif, Passenger passenger) throws MiValidacionException {
        if (inMemoryFlightRepository.getFlight(idVuelo) != null && !inMemoryPassengerRepository.existPassenger(idVuelo, nif)) {
            if (!inMemoryPassengerRepository.addPassenger(passenger)) {
                throw new MiValidacionException();
            }
        }
        return false;
    }

    public boolean delete(String idVuelo, String nif) {
        return inMemoryPassengerRepository.deletePassenger(idVuelo, nif);   }

    public List<Passenger> getAllPassengersOfAFlight(String idVuelo) {
        return inMemoryPassengerRepository.listPassengers(idVuelo);
    }

    public List<Passenger> getAllPassengers() {
        return inMemoryPassengerRepository.listPassengers();
    }
}
