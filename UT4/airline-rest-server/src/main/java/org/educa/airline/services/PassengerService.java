package org.educa.airline.services;

import lombok.Getter;
import org.educa.airline.entity.Luggage;
import org.educa.airline.entity.Passenger;
import org.educa.airline.exceptions.MiValidacionException;
import org.educa.airline.repository.inmemory.InMemoryFlightRepository;
import org.educa.airline.repository.inmemory.InMemoryLuggageRepository;
import org.educa.airline.repository.inmemory.InMemoryPassengerRepository;
import org.educa.airline.services.validador.ValidadorDeCampos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Getter
public class PassengerService{

    private final InMemoryPassengerRepository inMemoryPassengerRepository;
    private FlightService flightService;

    @Autowired
    PassengerService(InMemoryPassengerRepository inMemoryPassengerRepository, FlightService flightService) {
        this.flightService = flightService;
        this.inMemoryPassengerRepository = inMemoryPassengerRepository;
    }

    public boolean asociarVueloYPasagero(String cod, Passenger passenger) throws MiValidacionException {
        if (flightService.getAFlight(cod) != null) {
            if (inMemoryPassengerRepository.addPassenger(passenger)) {
                return true;
            } else {
                throw new MiValidacionException();
            }
        } else {
            return false;
        }

    }

    public Passenger getPassengerByIdAndNif(String idVuelo, String nif) {
        return inMemoryPassengerRepository.getPassenger(idVuelo, nif);
    }

    public boolean update(String cod, String nif, Passenger passenger) throws MiValidacionException {
        if (flightService.getAFlight(cod) != null && inMemoryPassengerRepository.existPassenger(cod, nif)) {
            inMemoryPassengerRepository.updatePassenger(nif, passenger);
            return true;
        }
        return false;
    }

    public boolean delete(String idVuelo, String nif) {
        return inMemoryPassengerRepository.deletePassenger(idVuelo, nif);


    }

    public List<Passenger> getAllPassengersOfAFlight(String idVuelo) {
        return inMemoryPassengerRepository.listPassengers(idVuelo);
    }
}
