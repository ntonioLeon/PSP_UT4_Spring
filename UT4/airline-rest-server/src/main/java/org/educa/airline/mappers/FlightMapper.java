package org.educa.airline.mappers;

import org.educa.airline.dto.FlightDTO;
import org.educa.airline.entity.Flight;
import org.educa.airline.exceptions.MiValidacionException;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
public class FlightMapper extends Mapper{

    /**
     *
     * @param vuelos
     * @return
     */
    public List<FlightDTO> toDTOs(List<Flight> vuelos) {
        List<FlightDTO> vuelosDTO = new ArrayList<>();
        for (Flight vuelo : vuelos) {
            vuelosDTO.add(new FlightDTO(vuelo.getCod(), vuelo.getId(), vuelo.getOrigin(), vuelo.getDestination(), fromDateToString(vuelo.getDate())));
        }
        return vuelosDTO;
    }

    /**
     *
     * @param vuelo
     * @return
     */
    public FlightDTO toDTO(Flight vuelo) {
        return new FlightDTO(vuelo.getCod(), vuelo.getId(), vuelo.getOrigin(), vuelo.getDestination(), fromDateToString(vuelo.getDate()));
    }

    /**
     *
     * @param flightDTO
     * @return
     */
    public Flight toEntity(FlightDTO flightDTO) throws MiValidacionException {
        return new Flight(flightDTO.getCod(), flightDTO.getId(), flightDTO.getOrigin(), flightDTO.getDestination(), fromStringToDate(flightDTO.getDate()));
    }

    /**
     *
     * @param vuelos
     * @return
     */
    public List<Flight> toEntities(List<FlightDTO> vuelos) throws MiValidacionException {
        List<Flight> vuelosENT = new ArrayList<>();
        for (FlightDTO vuelo : vuelos) {
            vuelosENT.add(new Flight(vuelo.getCod(), vuelo.getId(), vuelo.getOrigin(), vuelo.getDestination(), fromStringToDate(vuelo.getDate())));
        }
        return vuelosENT;
    }

    private String fromDateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    private Date fromStringToDate(String fecha) throws MiValidacionException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            return formatter.parse(fecha);
        } catch (ParseException e) {
            throw new MiValidacionException();
        }
    }
}
