package org.educa.airline.repository;

import org.educa.airline.entity.Flight;

import java.util.List;

public interface FlightRepository {
    List<Flight> list(String origin, String destination);

    Flight getFlight(String cod);

    boolean add(Flight flight);

    boolean updateFlight(String cod, Flight flight);

    boolean delete(String cod);
}
