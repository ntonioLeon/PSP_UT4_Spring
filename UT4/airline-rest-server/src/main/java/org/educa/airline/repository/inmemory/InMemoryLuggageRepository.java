package org.educa.airline.repository.inmemory;

import org.educa.airline.entity.Luggage;
import org.educa.airline.exceptions.LuggageNotFoundException;
import org.educa.airline.repository.LuggageRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryLuggageRepository implements LuggageRepository {
    private final Map<String, Map<String, Map<Integer, Luggage>>> luggage = new HashMap<>();


    @Override
    public List<Luggage> listLuggage(String flightId, String nif) {
        return new ArrayList<>(getLuggage(flightId, nif).values());
    }


    @Override
    public boolean existLuggage(String flightId, String nif, int luggageId) {
        return getLuggage(flightId, nif).containsKey(luggageId);
    }


    @Override
    public boolean addLuggage(String flightId, String nif, Luggage luggage) {
        if (existLuggage(flightId, nif, luggage.getId())) {
            return false;
        } else {
            getLuggage(flightId, nif).put(luggage.getId(), luggage);
            return true;
        }
    }

    @Override
    public Luggage getLuggage(String flightId, String nif, int luggageId) {
        return getLuggage(flightId, nif).get(luggageId);
    }


    @Override
    public boolean updateLuggage(String flightNumber, String nif, Luggage luggage) {
        if (existLuggage(flightNumber, nif, luggage.getId())) {
            getLuggage(flightNumber, nif).put(luggage.getId(), luggage);
            return true;
        } else {
            return false;
        }
    }

    private Map<Integer, Luggage> getLuggage(String flightId, String nif) {
        luggage.putIfAbsent(flightId, new HashMap<>());
        luggage.get(flightId).putIfAbsent(nif, new HashMap<>());
        return luggage.get(flightId).get(nif);
    }

    /**
     *
     */
    public boolean deleteLuggage(String flightId, String nif, int luggageId) throws LuggageNotFoundException {
        if (existLuggage(flightId, nif, luggageId)) {
            return luggage.get(flightId).get(nif).remove(luggageId) != null;
        } else {
            throw new LuggageNotFoundException();
        }
    }
}
