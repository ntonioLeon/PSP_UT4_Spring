package org.educa.airline.services;

import org.educa.airline.entity.Flight;
import org.educa.airline.exceptions.VueloYaExistente;
import org.educa.airline.repository.inmemory.InMemoryFlightRepository;
import org.educa.airline.services.validador.ValidadorDeCampos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.error.YAMLException;

import java.util.Date;
import java.util.List;

@Service
public class FlightService {

    private final InMemoryFlightRepository inMemoryFlightRepository;
    private final ValidadorDeCampos validadorDeCampos;

    @Autowired
    public FlightService(InMemoryFlightRepository inMemoryFlightRepository, ValidadorDeCampos validadorDeCampos) {
        this.inMemoryFlightRepository = inMemoryFlightRepository;
        this.validadorDeCampos = validadorDeCampos;
    }

    public Flight UnVueloPorFecha(String cod, String date) {
        Flight vuelo = inMemoryFlightRepository.getFlight(cod);
        if (vuelo != null) {
            if (date.equals(vuelo.getDate())) {
                return vuelo;
            }
        }
        return null;
    }


    public List<Flight> obtenerVuelosPorOriYDes(String origin, String destination) {
        return inMemoryFlightRepository.list(origin, destination);
    }

    public Flight getAFlight(String cod) {
        return inMemoryFlightRepository.getFlight(cod);
    }

    public boolean agregar(Flight flight) throws VueloYaExistente {
        if (inMemoryFlightRepository.getFlight(flight.getCod()) == null) {
            return inMemoryFlightRepository.add(flight);
        } else {
            throw new VueloYaExistente();
        }
    }

    public boolean update(String cod, Flight flight) {
        return inMemoryFlightRepository.updateFlight(cod, flight);
    }

    public boolean delete(String cod) {
        return inMemoryFlightRepository.delete(cod);
    }
}
