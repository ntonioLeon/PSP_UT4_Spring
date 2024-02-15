package org.example.service;

import org.example.api.ApiFlightService;
import org.example.exception.ValidationFailException;

import java.util.Date;
import java.util.Scanner;

public class FlightService extends Service {

    private final ApiFlightService apiFlightService = new ApiFlightService();

    public void createFlight(Scanner scanner) {

        try {
            System.out.println("Indroduce ");
            String id = utiles.checkId(scanner, "Nombre", 25);
            System.out.println();
            String origin = utiles.checkCampo(scanner, "Nombre", 25);
            System.out.println();
            String destination = utiles.checkCampo(scanner, "Nombre", 25);
            System.out.println();
            Date date = utiles.checkfecha(scanner, "Fecha");
        } catch (ValidationFailException ex) {
            System.out.println("Se han fallado cinco veces segidas, creacion abortada.");
        }
    }
}
