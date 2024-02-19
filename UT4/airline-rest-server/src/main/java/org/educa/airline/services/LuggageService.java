package org.educa.airline.services;

import lombok.Getter;
import org.educa.airline.entity.Luggage;
import org.educa.airline.exceptions.FlightNotFoundException;
import org.educa.airline.exceptions.LuggageNotFoundException;
import org.educa.airline.exceptions.LuggageYaExisteException;
import org.educa.airline.exceptions.PassengerNotFoundException;
import org.educa.airline.repository.inmemory.InMemoryFlightRepository;
import org.educa.airline.repository.inmemory.InMemoryLuggageRepository;
import org.educa.airline.repository.inmemory.InMemoryPassengerRepository;
import org.educa.airline.services.validador.ValidadorDeCampos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.error.YAMLException;

import java.util.List;

@Service
@Getter
public class LuggageService {

    private InMemoryLuggageRepository inMemoryLuggageRepository;
    private PassengerService passengerService;

    @Autowired
    LuggageService(PassengerService passengerService, InMemoryLuggageRepository inMemoryLuggageRepository) {
        this.inMemoryLuggageRepository = inMemoryLuggageRepository;
        this.passengerService = passengerService;
    }

    public Luggage getALuggageFromAFlight(int id, String cod, String nif) {
        return inMemoryLuggageRepository.getLuggage(cod, nif, id);
    }


    public List<Luggage> getAllLuggageFromAFlight(String cod, String nif) {
        return inMemoryLuggageRepository.listLuggage(cod, nif);
    }


    public boolean create(String cod, String nif, Luggage entity) throws FlightNotFoundException, PassengerNotFoundException, LuggageYaExisteException {
        if (passengerService.getFlightService().getAFlight(cod) != null) {
            if (passengerService.getPassengerByIdAndNif(cod, nif) != null) {
                if (inMemoryLuggageRepository.addLuggage(cod, nif, entity)) {
                    return true;
                } else {
                    throw new LuggageYaExisteException();
                }
            } else {
                throw new PassengerNotFoundException();
            }
        } else {
            throw new FlightNotFoundException();
        }
    }

    public boolean deleteLuggage(String cod, String nif, int id) throws LuggageNotFoundException {
        return inMemoryLuggageRepository.deleteLuggage(cod, nif, id);
    }

    public List<Luggage> listLuggage(String cod, String nif) {
        return inMemoryLuggageRepository.listLuggage(cod, nif);
    }
}
