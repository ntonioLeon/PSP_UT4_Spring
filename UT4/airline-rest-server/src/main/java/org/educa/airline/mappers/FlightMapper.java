package org.educa.airline.mappers;

import org.educa.airline.dto.FlightDTO;
import org.educa.airline.entity.Flight;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FlightMapper {
    public List<FlightDTO> toDTOs(List<Flight> vuelos) {
        List<FlightDTO> vuelosDTO = new ArrayList<>();
        for (Flight vuelo : vuelos) {
            vuelosDTO.add(new FlightDTO(vuelo.getId(), vuelo.getOrigin(), vuelo.getDestination(), vuelo.getDate()));
        }
        return vuelosDTO;
    }

    public FlightDTO toDTO(Flight vuelo) {
        return new FlightDTO(vuelo.getId(), vuelo.getOrigin(), vuelo.getDestination(), vuelo.getDate());
    }

    public Flight toEntity(FlightDTO flightDTO) {
        return new Flight(flightDTO.getId(), flightDTO.getOrigin(), flightDTO.getDestination(), flightDTO.getDate());
    }
}
