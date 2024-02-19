package org.example.api;

import com.google.gson.Gson;
import org.example.dto.LuggageDTO;
import org.example.dto.PassengerDTO;

public class ApiLuggageService extends ApiService {

    private final String URL = super.URL + "/flights/";
    private final String TRAS_CODE_URL = "/passenger";
    private final String TRAS_NIF_URL = "/luggage";


    public void create(LuggageDTO luggageDTO) throws Exception {
        Gson gson = new Gson();
        String body = gson.toJson(luggageDTO);
        connection.doPost(body, URL+luggageDTO.getFlightCod()+TRAS_CODE_URL+"/"+luggageDTO.getNif()+TRAS_NIF_URL);
    }

    public LuggageDTO[] getAllLuggagesFromAFlight(String cod) throws Exception {
        Gson gson = new Gson();
        String body = connection.doGet(URL+cod+TRAS_CODE_URL+TRAS_NIF_URL);
        return gson.fromJson(body, LuggageDTO[].class);
    }

    public LuggageDTO getALuggageFromAPassenger(String id, String nif, String cod) throws Exception {
        Gson gson = new Gson();
        String body = connection.doGet(URL+cod+TRAS_CODE_URL+"/"+nif+TRAS_NIF_URL+"/"+id);
        return gson.fromJson(body, LuggageDTO.class);
    }

    public void deleteLuggageFromPassenger(String id, String nif, String cod) throws Exception {
        connection.doDelete(URL+cod+TRAS_CODE_URL+"/"+nif+TRAS_NIF_URL+"/"+id);
    }
}
