package org.educa.airline.services;

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
public class PassengerService extends Repositories{

    @Autowired
    PassengerService(InMemoryFlightRepository inMemoryFlightRepository, InMemoryLuggageRepository inMemoryLuggageRepository, InMemoryPassengerRepository inMemoryPassengerRepository, ValidadorDeCampos validadorDeCampos) {
        super(inMemoryFlightRepository, inMemoryLuggageRepository, inMemoryPassengerRepository, validadorDeCampos);
    }

    public boolean asociarVueloYPasagero(String cod, Passenger passenger) throws MiValidacionException {
        if (getInMemoryFlightRepository().getFlight(cod) != null) {
            if (getInMemoryPassengerRepository().addPassenger(passenger)) {
                return true;
            } else {
                throw new MiValidacionException();
            }
        } else {
            return false;
        }

    }

    public Passenger getPassengerByIdAndNif(String idVuelo, String nif) {
        return getInMemoryPassengerRepository().getPassenger(idVuelo, nif);
    }

    public boolean update(String cod, String nif, Passenger passenger) throws MiValidacionException {
        if (getInMemoryFlightRepository().getFlight(cod) != null && getInMemoryPassengerRepository().existPassenger(cod, nif)) {
            getInMemoryPassengerRepository().updatePassenger(nif, passenger);
            return true;
        }
        return false;
    }

    public boolean delete(String idVuelo, String nif) {
        if (getInMemoryPassengerRepository().deletePassenger(idVuelo, nif)) {
            borrarLosEquipajesDeUnPasajeroEnUnVuelo(idVuelo, nif);
            return true;
        } else {
            return false;
        }

    }

    public List<Passenger> getAllPassengersOfAFlight(String idVuelo) {
        return getInMemoryPassengerRepository().listPassengers(idVuelo);
    }

    private void borrarLosEquipajesDeUnPasajeroEnUnVuelo(String cod, String nif) {
        List<Luggage> equipajes = getInMemoryLuggageRepository().listLuggage(cod, nif);
        for (int i = 0; i < equipajes.size(); i++) {
            getInMemoryLuggageRepository().deleteLuggage(equipajes.get(i).getFlightCod(), equipajes.get(i).getNif(), equipajes.get(i).getId());
        }
    }
}
