package org.educa.airline.services;

import lombok.Getter;
import org.educa.airline.repository.inmemory.InMemoryFlightRepository;
import org.springframework.stereotype.Service;

@Service
@Getter
public class FlightPassengerService {

    private InMemoryFlightRepository inMemoryFlightRepository;

    FlightPassengerService(InMemoryFlightRepository inMemoryFlightRepository) {
        this.inMemoryFlightRepository = inMemoryFlightRepository;
    }
}
