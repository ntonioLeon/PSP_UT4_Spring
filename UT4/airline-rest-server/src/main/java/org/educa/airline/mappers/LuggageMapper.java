package org.educa.airline.mappers;

import org.educa.airline.dto.LuggageDTO;
import org.educa.airline.entity.Luggage;
import org.educa.airline.exceptions.MiValidacionException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LuggageMapper  extends Mapper{

    /**
     *
     * @param luggageDTO
     * @return
     */
    public Luggage toEntity(LuggageDTO luggageDTO) throws MiValidacionException {
        return new Luggage(fromStringToInt(luggageDTO.getId()), luggageDTO.getNif(), luggageDTO.getFlightCod(), luggageDTO.getDescription());
    }

    /**
     *
     * @param luggage
     * @return
     */
    public LuggageDTO toDTO(Luggage luggage) {
        return new LuggageDTO(fromIntTOString(luggage.getId()), luggage.getNif(), luggage.getFlightCod(), luggage.getDescription());
    }

    /**
     *
     * @param luggages
     * @return
     */
    public List<LuggageDTO> toDTOs(List<Luggage> luggages) {
        List<LuggageDTO> luggageDTOs = new ArrayList<>();
        for (Luggage luggage : luggages) {
            luggageDTOs.add(new LuggageDTO(fromIntTOString(luggage.getId()), luggage.getNif(), luggage.getFlightCod(), luggage.getDescription()));

        }
        return luggageDTOs;
    }

    /**
     *
     * @param luggagesDTO
     * @return
     */
    public List<Luggage> toEntities(List<LuggageDTO> luggagesDTO) throws MiValidacionException {
        List<Luggage> luggages = new ArrayList<>();
        for (LuggageDTO luggageDTO : luggagesDTO) {
            luggages.add(new Luggage(fromStringToInt(luggageDTO.getId()), luggageDTO.getNif(), luggageDTO.getFlightCod(), luggageDTO.getDescription()));
        }
        return luggages;
    }
}
