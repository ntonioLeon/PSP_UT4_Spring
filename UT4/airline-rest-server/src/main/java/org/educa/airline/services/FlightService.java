package org.educa.airline.services;

import lombok.Getter;
import org.educa.airline.entity.Flight;
import org.educa.airline.entity.Passenger;
import org.educa.airline.exceptions.MiValidacionException;
import org.educa.airline.exceptions.flight.FlightNotFoundException;
import org.educa.airline.exceptions.passenger.PassengerNotFoundException;
import org.educa.airline.repository.inmemory.InMemoryFlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@Service
@Getter
public class FlightService{

    private final InMemoryFlightRepository inMemoryFlightRepository;

    @Autowired
    FlightService(InMemoryFlightRepository inMemoryFlightRepository) {
        this.inMemoryFlightRepository = inMemoryFlightRepository;
    }

    public Flight UnVueloPorFecha(String cod, String date) throws MiValidacionException, FlightNotFoundException, ParseException {
        Flight vuelo = inMemoryFlightRepository.getFlight(cod);
        if (vuelo != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            formatter.setLenient(false);
            if (formatter.parse(date).equals(vuelo.getDate())) {
                return vuelo;
            } else {
                throw new FlightNotFoundException();
            }
        } else {
            throw  new FlightNotFoundException();
        }
    }


    public List<Flight> obtenerVuelosPorOriYDes(String origin, String destination) {
        return inMemoryFlightRepository.list(origin, destination);
    }

    public boolean getAFlight(String cod) throws FlightNotFoundException {
        Flight flight = inMemoryFlightRepository.getFlight(cod);
        if (flight != null) {
            return true;
        } else {
            throw new FlightNotFoundException();
        }
    }

    public boolean agregar(Flight flight) {
        return inMemoryFlightRepository.add(flight);
    }

    public boolean update(String cod, Flight flight) {
        return inMemoryFlightRepository.updateFlight(cod, flight);
    }

    public boolean delete(String cod) {
        return inMemoryFlightRepository.delete(cod);
    }
}
