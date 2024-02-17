package org.educa.airline.services;

import lombok.Getter;
import org.educa.airline.repository.inmemory.InMemoryPassengerRepository;
import org.springframework.stereotype.Service;

@Service
@Getter
public class PassengerLuggageService {

    private final InMemoryPassengerRepository inMemoryPassengerRepository;

    PassengerLuggageService(InMemoryPassengerRepository inMemoryPassengerRepository) {
        this.inMemoryPassengerRepository = inMemoryPassengerRepository;
    }
}
