package org.educa.airline.services;

import org.educa.airline.entity.Flight;
import org.educa.airline.repository.inmemory.InMemoryFlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FlightService {

    private InMemoryFlightRepository inMemoryFlightRepository;

    @Autowired
    public FlightService(InMemoryFlightRepository inMemoryFlightRepository) {
        this.inMemoryFlightRepository = inMemoryFlightRepository;
    }

    public Flight UnVueloPorFecha(String idVuelo, Date date) {
        Flight vuelo = inMemoryFlightRepository.getFlight(idVuelo);
        if (date.equals(vuelo.getDate())) {
            return vuelo;
        } else {
            return null;
        }
    }


    public List<Flight> obtenerVuelosPorOriYDes(String origin, String destination) {
        return inMemoryFlightRepository.list(origin, destination);
    }

    public Flight getAFlight(String idVuelo) {
        return inMemoryFlightRepository.getFlight(idVuelo);
    }

    public boolean agregar(Flight flight) {
        return inMemoryFlightRepository.add(flight);
    }

    public boolean update(String idVuelo, Flight flight) {
        return inMemoryFlightRepository.updateFlight(idVuelo, flight);
    }

    public boolean delete(String idVuelo) {
        return inMemoryFlightRepository.delete(idVuelo);
    }
}
