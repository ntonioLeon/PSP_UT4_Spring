package org.educa.airline.repository.inmemory;

import org.educa.airline.entity.Flight;
import org.educa.airline.repository.FlightRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class InMemoryFlightRepository implements FlightRepository {
    private final Map<String, Flight> flights = new HashMap<>();

    @Override
    public List<Flight> list(String origin, String destination) {
        return
                flights
                        .values()
                        .stream()
                        .filter(f -> origin == null || origin.equals(f.getOrigin()))
                        .filter(f -> destination == null || destination.equals(f.getDestination()))
                        .collect(Collectors.toList());
    }

    @Override
    public Flight getFlight(
            String cod
    ) {
        return flights.get(cod);
    }

    @Override
    public boolean add(Flight flight) {
        if (!flights.containsKey(flight.getCod())) {
            flights.put(flight.getCod(), flight);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateFlight(
            String cod,
            Flight flight
    ) {
        // 3 -> Madrid - Londres
        // 4 -> 1 Madrid - Berlin
        if (flights.containsKey(cod)) {
            flights.remove(cod);
            flights.put(flight.getCod(), flight);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(String cod) {
        if (flights.containsKey(cod)) {
            flights.remove(cod);
            return true;
        } else {
            return false;
        }
    }
}
