package org.educa.airline.mappers;

import org.educa.airline.dto.FlightDTO;
import org.educa.airline.entity.Flight;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FlightMapper {

    /**
     *
     * @param vuelos
     * @return
     */
    public List<FlightDTO> toDTOs(List<Flight> vuelos) {
        List<FlightDTO> vuelosDTO = new ArrayList<>();
        for (Flight vuelo : vuelos) {
            vuelosDTO.add(new FlightDTO(vuelo.getId(), vuelo.getOrigin(), vuelo.getDestination(), vuelo.getDate()));
        }
        return vuelosDTO;
    }

    /**
     *
     * @param vuelo
     * @return
     */
    public FlightDTO toDTO(Flight vuelo) {
        return new FlightDTO(vuelo.getId(), vuelo.getOrigin(), vuelo.getDestination(), vuelo.getDate());
    }

    /**
     *
     * @param flightDTO
     * @return
     */
    public Flight toEntity(FlightDTO flightDTO) {
        return new Flight(flightDTO.getId(), flightDTO.getOrigin(), flightDTO.getDestination(), flightDTO.getDate());
    }

    /**
     *
     * @param vuelos
     * @return
     */
    public List<Flight> toEntities(List<FlightDTO> vuelos) {
        List<Flight> vuelosENT = new ArrayList<>();
        for (FlightDTO vuelo : vuelos) {
            vuelosENT.add(new Flight(vuelo.getId(), vuelo.getOrigin(), vuelo.getDestination(), vuelo.getDate()));
        }
        return vuelosENT;
    }
}
