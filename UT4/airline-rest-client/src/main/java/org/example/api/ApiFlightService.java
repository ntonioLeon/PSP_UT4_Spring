package org.example.api;

import com.google.gson.Gson;
import org.example.dto.FlightDTO;
import org.example.exception.NotFoundException;

import java.util.List;

public class ApiFlightService extends ApiService {

    private final String URL = super.URL + "/flights";

    public void create(FlightDTO flightDTO) throws Exception{
        Gson gson = new Gson();
        String body = gson.toJson(flightDTO);
        connection.doPost( body, URL + "/add");
    }

    public void delete(String cod) throws Exception {
        connection.doDelete(URL+"/delete/"+cod);
    }

    public FlightDTO[] getFlightFromOriAndDest(String origin, String destination) throws Exception {
        String body = connection.doGet(URL+"?ori="+origin+"&des="+destination);
        Gson gson = new Gson();
        return gson.fromJson(body, FlightDTO[].class);
    }

    public FlightDTO getFlightsFromCodAndDate(String codigo, String date) throws Exception {
        String body = connection.doGet(URL+"/"+codigo+"?date="+date);
        Gson gson = new Gson();
        return gson.fromJson(body, FlightDTO.class);
    }
}
