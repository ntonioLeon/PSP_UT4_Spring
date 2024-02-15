package org.example.core;

import org.example.service.FlightService;

import java.util.Scanner;

public class ClienteFlight {

    FlightService flightService = new FlightService();

    public void run(Scanner scanner) throws Exception {
        int opt = -1;
        while (opt != 0) {
            menuFlight();
            try {
                opt = scanner.nextInt();
            } catch (Exception ex) {
                System.out.println("Introduzca únicamente números enteros.");
            }
            scanner.nextLine();
            switch (opt) {
                case 0:
                    System.out.println("Volviendo al menu principal.");
                    break;
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                default:
                    System.err.println("opción no valida.");
            }
        }
    }

    private void menuFlight() {
        System.out.println("Menu de vuelos:");
        System.out.println("0. Salir.");
        System.out.println("1. ");
        System.out.println("2. ");
        System.out.println("3. ");
        System.out.println("4. ");
        System.out.println("4. ");
        System.out.println("Introduzca una opción.");
    }
}
