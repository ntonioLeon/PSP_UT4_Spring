package org.educa.airline.services;

import lombok.Getter;
import org.educa.airline.entity.Luggage;
import org.educa.airline.exceptions.luggage.LuggageNotFoundException;
import org.educa.airline.exceptions.luggage.LuggageYaExisteException;
import org.educa.airline.repository.inmemory.InMemoryLuggageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
public class LuggageService {

    private InMemoryLuggageRepository inMemoryLuggageRepository;

    @Autowired
    LuggageService(PassengerService passengerService, InMemoryLuggageRepository inMemoryLuggageRepository) {
        this.inMemoryLuggageRepository = inMemoryLuggageRepository;
    }

    public Luggage getALuggageFromAFlight(int id, String cod, String nif) {
        return inMemoryLuggageRepository.getLuggage(cod, nif, id);
    }


    public List<Luggage> getAllLuggageFromAFlight(String cod, String nif) {
        return inMemoryLuggageRepository.listLuggage(cod, nif);
    }


    public boolean create(String cod, String nif, Luggage entity) throws LuggageYaExisteException {
        if (inMemoryLuggageRepository.addLuggage(cod, nif, entity)) {
            return true;
        } else {
            throw new LuggageYaExisteException();
        }
    }

    public boolean deleteLuggage(String cod, String nif, int id) throws LuggageNotFoundException {
        return inMemoryLuggageRepository.deleteLuggage(cod, nif, id);
    }

    public List<Luggage> listLuggage(String cod, String nif) {
        return inMemoryLuggageRepository.listLuggage(cod, nif);
    }
}
