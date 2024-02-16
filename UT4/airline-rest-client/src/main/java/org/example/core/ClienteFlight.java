package org.example.core;

import org.example.service.FlightService;

import java.util.Scanner;

public class ClienteFlight {

    FlightService flightService = new FlightService();

    public void run(Scanner scanner) throws Exception {
        String opt = "-1";
        while (!"0".equals(opt)) {
            menuFlight();
            opt = scanner.nextLine();
            switch (opt) {
                case "0":
                    System.out.println("Volviendo al menu principal.");
                    break;
                case "1":
                        flightService.createFlight(scanner);
                    break;
                case "2":
                        flightService.findFlightFromDate(scanner);
                    break;
                case "3":
                        flightService.getFlightsFromOriAndDest(scanner);
                    break;
                default:
                    System.err.println("opción no valida.");
            }
        }
    }

    private void menuFlight() {
        System.out.println("Menu de vuelos:");
        System.out.println("0. Salir.");
        System.out.println("1. Crear vuelo.");
        System.out.println("2. Encontrar un vuelo por Codigo y Fecha.");
        System.out.println("3. Encontrar un vuelo por origen y destino.");
        System.out.println("Introduzca una opción.");
    }
}
