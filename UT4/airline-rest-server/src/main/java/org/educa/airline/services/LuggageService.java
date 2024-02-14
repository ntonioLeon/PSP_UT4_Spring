package org.educa.airline.services;

import org.educa.airline.repository.inmemory.InMemoryLuggageRepository;
import org.educa.airline.services.validador.ValidadorDeCampos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LuggageService {

    private final InMemoryLuggageRepository inMemoryLuggageRepository;
    private final ValidadorDeCampos validadorDeCampos;

    @Autowired
    public LuggageService(InMemoryLuggageRepository inMemoryLuggageRepository, ValidadorDeCampos validadorDeCampos) {
        this.inMemoryLuggageRepository = inMemoryLuggageRepository;
        this.validadorDeCampos = validadorDeCampos;
    }
}
