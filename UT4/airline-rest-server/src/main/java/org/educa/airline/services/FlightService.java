package org.educa.airline.services;

import org.educa.airline.entity.Flight;
import org.educa.airline.exceptions.MiValidacionException;
import org.educa.airline.exceptions.VueloYaExistente;
import org.educa.airline.repository.inmemory.InMemoryFlightRepository;
import org.educa.airline.repository.inmemory.InMemoryLuggageRepository;
import org.educa.airline.repository.inmemory.InMemoryPassengerRepository;
import org.educa.airline.services.validador.ValidadorDeCampos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.error.YAMLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class FlightService extends Repositories{

    @Autowired
    FlightService(InMemoryFlightRepository inMemoryFlightRepository, InMemoryLuggageRepository inMemoryLuggageRepository, InMemoryPassengerRepository inMemoryPassengerRepository, ValidadorDeCampos validadorDeCampos) {
        super(inMemoryFlightRepository, inMemoryLuggageRepository, inMemoryPassengerRepository, validadorDeCampos);
    }

    public Flight UnVueloPorFecha(String cod, String date) throws MiValidacionException {
        Flight vuelo = getInMemoryFlightRepository().getFlight(cod);
        if (vuelo != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            if (date.equals(formatter.format(vuelo.getDate()))) {
                return vuelo;
            }
        }
        return null;
    }


    public List<Flight> obtenerVuelosPorOriYDes(String origin, String destination) {
        return getInMemoryFlightRepository().list(origin, destination);
    }

    public Flight getAFlight(String cod) {
        return getInMemoryFlightRepository().getFlight(cod);
    }

    public boolean agregar(Flight flight) throws VueloYaExistente {
        if (getInMemoryFlightRepository().getFlight(flight.getCod()) == null) {
            return getInMemoryFlightRepository().add(flight);
        } else {
            throw new VueloYaExistente();
        }
    }

    public boolean update(String cod, Flight flight) {
        return getInMemoryFlightRepository().updateFlight(cod, flight);
    }

    public boolean delete(String cod) {
        return getInMemoryFlightRepository().delete(cod);
    }
}
