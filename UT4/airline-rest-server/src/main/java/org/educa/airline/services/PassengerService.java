package org.educa.airline.services;

import org.educa.airline.entity.Passenger;
import org.educa.airline.exceptions.MiValidacionException;
import org.educa.airline.repository.inmemory.InMemoryFlightRepository;
import org.educa.airline.repository.inmemory.InMemoryPassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
