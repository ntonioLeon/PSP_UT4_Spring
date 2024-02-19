package org.educa.airline.services;

import lombok.Getter;
import org.educa.airline.repository.inmemory.InMemoryFlightRepository;
import org.educa.airline.repository.inmemory.InMemoryLuggageRepository;
import org.educa.airline.repository.inmemory.InMemoryPassengerRepository;
import org.educa.airline.services.validador.ValidadorDeCampos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public abstract class Repositories {
    private final InMemoryFlightRepository inMemoryFlightRepository;
    private final InMemoryLuggageRepository inMemoryLuggageRepository;
    private final InMemoryPassengerRepository inMemoryPassengerRepository;
    private final ValidadorDeCampos validadorDeCampos;

    @Autowired
    Repositories(InMemoryFlightRepository inMemoryFlightRepository, InMemoryLuggageRepository inMemoryLuggageRepository, InMemoryPassengerRepository inMemoryPassengerRepository, ValidadorDeCampos validadorDeCampos) {
        this.inMemoryFlightRepository = inMemoryFlightRepository;
        this.inMemoryLuggageRepository = inMemoryLuggageRepository;
        this.inMemoryPassengerRepository = inMemoryPassengerRepository;
        this.validadorDeCampos = validadorDeCampos;
    }
}
