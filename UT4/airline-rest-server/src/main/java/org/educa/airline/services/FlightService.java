package org.educa.airline.services;

import org.educa.airline.repository.inmemory.InMemoryFlightRepository;
import org.educa.airline.repository.inmemory.InMemoryLuggageRepository;
import org.educa.airline.repository.inmemory.InMemoryPassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightService {

    private InMemoryFlightRepository inMemoryFlightRepository;
    private InMemoryLuggageRepository inMemoryLuggageRepository;
    private InMemoryPassengerRepository inMemoryPassengerRepository;

    @Autowired
    public FlightService(InMemoryFlightRepository inMemoryFlightRepository, InMemoryLuggageRepository inMemoryLuggageRepository, InMemoryPassengerRepository inMemoryPassengerRepository) {
        this.inMemoryFlightRepository = inMemoryFlightRepository;
        this.inMemoryLuggageRepository = inMemoryLuggageRepository;
        this.inMemoryPassengerRepository = inMemoryPassengerRepository;
    }


}
