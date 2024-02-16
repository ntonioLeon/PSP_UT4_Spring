package org.educa.airline.mappers;

import org.educa.airline.dto.LuggageDTO;
import org.educa.airline.entity.Luggage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LuggageMapper {

    /**
     *
     * @param LuggageDTO
     * @return
     */
    public Luggage toEntity(LuggageDTO luggageDTO) {
        return new Luggage(luggageDTO.getId(), luggageDTO.getNif(), luggageDTO.getFlightCod(), luggageDTO.getDescription());
    }

    /**
     *
     * @param Luggage
     * @return
     */
    public LuggageDTO toDTO(Luggage luggage) {
        return new LuggageDTO(luggage.getId(), luggage.getNif(), luggage.getFlightCod(), luggage.getDescription());
    }

    /**
     *
     * @param luggages
     * @return
     */
    public List<LuggageDTO> toDTOs(List<Luggage> luggages) {
        List<LuggageDTO> luggageDTOs = new ArrayList<>();
        for (Luggage luggage : luggages) {
            luggageDTOs.add(new LuggageDTO(luggage.getId(), luggage.getNif(), luggage.getFlightCod(), luggage.getDescription()));

        }
        return luggageDTOs;
    }

    /**
     *
     * @param luggagesDTO
     * @return
     */
    public List<Luggage> toEntities(List<LuggageDTO> luggagesDTO) {
        List<Luggage> luggages = new ArrayList<>();
        for (LuggageDTO luggageDTO : luggagesDTO) {
            luggages.add(new Luggage(luggageDTO.getId(), luggageDTO.getNif(), luggageDTO.getFlightCod(), luggageDTO.getDescription()));
        }
        return luggages;
    }
}
