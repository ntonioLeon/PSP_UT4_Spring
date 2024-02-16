package org.example.core;


import org.example.service.PassengerService;

import java.util.Scanner;

public class ClientePassenger {
    PassengerService passengerService = new PassengerService();

    public void run(Scanner scanner) throws Exception {
        String opt = "-1";
        while (!"0".equals(opt)) {
            menuPassenger();
            opt = scanner.nextLine();
            switch (opt) {
                case "0":
                    System.out.println("Volviendo al menu principal.");
                    break;
                case "1":
                    passengerService.createPassenger(scanner);
                    break;
                case "2":
                    passengerService.associatePassenger(scanner);
                    break;
                case "3":
                    passengerService.findAPassengerFromAFlight(scanner);
                    break;
                case "4":

                    break;
                case "5":

                    break;
                case "6":

                    break;
                default:
                    System.err.println("opción no valida.");
            }
        }
    }

    private void menuPassenger() {
        System.out.println("Menu de pasajeros:");
        System.out.println("0. Salir.");
        System.out.println("1. Crear pasajero.");
        System.out.println("2. Asociar pasajero a un vuelo.");
        System.out.println("3. Consultar si un pasajero esta en un vuelo.");
        System.out.println("4. Actualizar un pasajero de un vuelo.");
        System.out.println("5. Eliminar un pasajero de un vuelo.");
        System.out.println("6. Obtener todos los psasajeros de un vuelo.");
        System.out.println("Introduzca una opción:");
    }
}
