package org.educa.airline.services;

import org.educa.airline.entity.Passenger;
import org.educa.airline.exceptions.MiValidacionException;
import org.educa.airline.repository.inmemory.InMemoryPassengerRepository;
import org.educa.airline.services.validador.ValidadorDeCampos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PassengerService {

    private final InMemoryPassengerRepository inMemoryPassengerRepository;
    private final ValidadorDeCampos validadorDeCampos;
    private final FlightPassengerService flightPassengerService;

    @Autowired
    PassengerService(InMemoryPassengerRepository inMemoryPassengerRepository, ValidadorDeCampos validadorDeCampos, FlightPassengerService flightPassengerService) {
        this.inMemoryPassengerRepository = inMemoryPassengerRepository;
        this.validadorDeCampos = validadorDeCampos;
        this.flightPassengerService = flightPassengerService;
    }

    public boolean asociarVueloYPasagero(String cod, Passenger passenger) throws MiValidacionException {
        if (flightPassengerService.getInMemoryFlightRepository().getFlight(cod) != null) {
            if (!inMemoryPassengerRepository.addPassenger(passenger)) {
                throw new MiValidacionException();
            } else {
                return true;
            }
        }
        return false;
    }

    public Passenger getPassengerByIdAndNif(String idVuelo, String nif) {
        return inMemoryPassengerRepository.getPassenger(idVuelo, nif);
    }

    public boolean  update(String cod, String nif, Passenger passenger) throws MiValidacionException {
        if (flightPassengerService.getInMemoryFlightRepository().getFlight(cod) != null && inMemoryPassengerRepository.existPassenger(cod, nif)) {
            if (!inMemoryPassengerRepository.addPassenger(passenger)) {
                throw new MiValidacionException();
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean delete(String idVuelo, String nif) {
        return inMemoryPassengerRepository.deletePassenger(idVuelo, nif);   }

    public List<Passenger> getAllPassengersOfAFlight(String idVuelo) {
        return inMemoryPassengerRepository.listPassengers(idVuelo);
    }

    public List<Passenger> getAllPassengers() {
        return inMemoryPassengerRepository.listPassengers();
    }
}
