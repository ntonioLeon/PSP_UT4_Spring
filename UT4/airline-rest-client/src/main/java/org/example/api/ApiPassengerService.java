package org.example.api;

import com.google.gson.Gson;
import org.example.dto.PassengerDTO;

public class ApiPassengerService extends ApiService {

    private final String URL = super.URL + "/flights/";
    private final String TRAS_CODE_URL = "/passenger";


    public void asociar(String codigo, PassengerDTO passengerDTO) throws Exception {
        Gson gson = new Gson();
        String body = gson.toJson(passengerDTO);
        connection.doPost(body, URL+codigo+TRAS_CODE_URL);
    }

    public PassengerDTO findFromNif(String codigo, String nif) throws Exception {
        String body = connection.doGet(URL+codigo+TRAS_CODE_URL+"/"+nif);
        Gson gson = new Gson();

        return gson.fromJson(body, PassengerDTO.class);
    }

    public PassengerDTO[] findAllFromCod(String codigo) throws Exception {
        String body = connection.doGet(URL+codigo+TRAS_CODE_URL);
        Gson gson = new Gson();

        return gson.fromJson(body, PassengerDTO[].class);
    }

    public void updatePassenger(String codigo, PassengerDTO passengerDTO) throws Exception {
        Gson gson = new Gson();
        String body = gson.toJson(passengerDTO);
        connection.doUpdate(body, URL+codigo+"/"+passengerDTO.getNif());
    }

    public void desAsociar(String codigo, String nif) throws  Exception{
        connection.doDelete(URL+codigo+TRAS_CODE_URL+"/"+nif);
    }
}
