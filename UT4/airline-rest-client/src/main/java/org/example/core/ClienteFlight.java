package org.example.core;

import org.example.service.FlightService;

import java.util.Scanner;

public class ClienteFlight {

    FlightService flightService = new FlightService();

    public void run(Scanner scanner) {
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
                    flightService.deleteFlight(scanner);
                    break;
                case "3":
                    flightService.updateFlight(scanner);
                    break;
                case "4":
                        flightService.findFlightFromDate(scanner);
                    break;
                case "5":
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
        System.out.println("2. Borrar vuelo.");
        System.out.println("3. Modificar vuelo.");
        System.out.println("4. Encontrar un vuelo por Codigo y Fecha.");
        System.out.println("5. Encontrar un vuelo por origen y destino.");
        System.out.println("Introduzca una opción.");
    }
}
