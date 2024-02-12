package org.educa.airline.services;

import org.educa.airline.entity.Flight;
import org.educa.airline.repository.inmemory.InMemoryFlightRepository;
import org.educa.airline.repository.inmemory.InMemoryLuggageRepository;
import org.educa.airline.repository.inmemory.InMemoryPassengerRepository;
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
}
