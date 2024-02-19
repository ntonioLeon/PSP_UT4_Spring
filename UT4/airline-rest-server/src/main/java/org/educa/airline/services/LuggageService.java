package org.educa.airline.services;

import org.educa.airline.entity.Luggage;
import org.educa.airline.exceptions.FlightNotFoundException;
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
public class LuggageService extends Repositories{


    @Autowired
    LuggageService(InMemoryFlightRepository inMemoryFlightRepository, InMemoryLuggageRepository inMemoryLuggageRepository, InMemoryPassengerRepository inMemoryPassengerRepository, ValidadorDeCampos validadorDeCampos) {
        super(inMemoryFlightRepository, inMemoryLuggageRepository, inMemoryPassengerRepository, validadorDeCampos);
    }

    public Luggage getALuggageFromAFlight(int id, String cod, String nif) {
        return getInMemoryLuggageRepository().getLuggage(cod, nif, id);
    }


    public List<Luggage> getAllLuggageFromAFlight(String cod, String nif) {
        return getInMemoryLuggageRepository().listLuggage(cod, nif);
    }


    public boolean create(String cod, String nif, Luggage entity) throws FlightNotFoundException, PassengerNotFoundException, LuggageYaExisteException {
        if (getInMemoryFlightRepository().getFlight(cod) != null) {
            if (getInMemoryPassengerRepository().getPassenger(cod, nif) != null) {
                if (getInMemoryLuggageRepository().addLuggage(cod, nif, entity)) {
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
}
