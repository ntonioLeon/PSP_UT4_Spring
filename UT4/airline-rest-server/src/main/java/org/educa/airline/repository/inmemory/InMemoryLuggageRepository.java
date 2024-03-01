package org.educa.airline.repository.inmemory;

import org.educa.airline.entity.Luggage;
import org.educa.airline.repository.LuggageRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
     * Metodo que borra un equipaje
     * @param flightId del vuelo
     * @param nif del pasajero
     * @param luggageId del equipaje
     * @return true si se borra.
     */
    public boolean deleteLuggage(String flightId, String nif, int luggageId) {
        return luggage.get(flightId).get(nif).remove(luggageId) != null;
    }

    /**
     * Metodo que lista todos los luggages de un vuelo
     * @param cod del vuelo
     * @return una lista con todos los luggages de un vuelo
     */
    public List<Luggage> listLuggageOfAFlight(String cod) {
        List<Luggage> luggages = new ArrayList<>();
        if (vueloExisteYTieneEquipajes(cod)) {
            Map<String, Map<Integer, Luggage>> pasajeros = luggage.get(cod);  //Mapa con los nifs de los pasajeros y sus respectivos equipajes
            luggages = pasajeros.values()
                                .stream()
                                .flatMap(x -> x.values().stream())
                                .collect(Collectors.toList());  //Basandonos en la lambda de InMemoryPassengerRepositoru.
        }
        return luggages;
    }

    /**
     * Metodo que comprueba si el vuelo existe dentro del "luggage"
     * @param cod del vuelo
     * @return un boolean si el vuelo existe
     */
    private boolean vueloExisteYTieneEquipajes(String cod) {
        return luggage.get(cod) != null;
    }
}
