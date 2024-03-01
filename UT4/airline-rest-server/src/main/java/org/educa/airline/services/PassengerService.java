package org.educa.airline.services;

import lombok.Getter;
import org.educa.airline.entity.Passenger;
import org.educa.airline.exceptions.MiValidacionException;
import org.educa.airline.exceptions.passenger.PassengerNotFoundException;
import org.educa.airline.exceptions.passenger.PassengerYaExisteException;
import org.educa.airline.repository.inmemory.InMemoryPassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
public class PassengerService{

    private final InMemoryPassengerRepository inMemoryPassengerRepository;

    @Autowired
    PassengerService(InMemoryPassengerRepository inMemoryPassengerRepository) {
        this.inMemoryPassengerRepository = inMemoryPassengerRepository;
    }

    public boolean asociarVueloYPasagero(String cod, Passenger passenger) throws MiValidacionException {
        return inMemoryPassengerRepository.addPassenger(passenger);
    }

    public Passenger getPassengerByIdAndNif(String idVuelo, String nif) throws PassengerNotFoundException {
        Passenger passenger = inMemoryPassengerRepository.getPassenger(idVuelo, nif);
        if (passenger != null) {
            return passenger;
        } else {
            throw new PassengerNotFoundException();
        }
    }

    public boolean update(String cod, String nif, Passenger passenger) throws PassengerYaExisteException {
        if (inMemoryPassengerRepository.getPassenger(cod, nif) != null) {
            inMemoryPassengerRepository.updatePassenger(nif, passenger);
            return true;
        } else {
            throw new PassengerYaExisteException();
        }
    }

    public boolean delete(String idVuelo, String nif) {
        return inMemoryPassengerRepository.deletePassenger(idVuelo, nif);
    }

    public List<Passenger> getAllPassengersOfAFlight(String idVuelo) {
        return inMemoryPassengerRepository.listPassengers(idVuelo);
    }

    public boolean existPassenger(String cod, String nif) throws PassengerNotFoundException {
        if (inMemoryPassengerRepository.existPassenger(cod, nif)) {
            return true;
        } else {
            throw new PassengerNotFoundException();
        }
    }
}
