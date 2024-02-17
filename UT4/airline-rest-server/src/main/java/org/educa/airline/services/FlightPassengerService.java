package org.educa.airline.services;

import lombok.Getter;
import org.educa.airline.repository.inmemory.InMemoryFlightRepository;
import org.springframework.stereotype.Service;

@Service
@Getter
public class FlightPassengerService {

    private final InMemoryFlightRepository inMemoryFlightRepository;

    FlightPassengerService(InMemoryFlightRepository inMemoryFlightRepository) {
        this.inMemoryFlightRepository = inMemoryFlightRepository;
    }
}
