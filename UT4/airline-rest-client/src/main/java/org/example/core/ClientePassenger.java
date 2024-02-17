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
                    passengerService.associatePassenger(scanner);
                    break;
                case "2":
                    passengerService.findAPassengerFromAFlight(scanner);
                    break;
                case "3":
                    passengerService.updatePasengerFromFlight(scanner);
                    break;
                case "4":
                    passengerService.deletePassengerFromFlight(scanner);
                    break;
                case "5":
                    passengerService.findAllFromAFlight(scanner);
                    break;
                default:
                    System.err.println("opción no valida.");
            }
        }
    }

    private void menuPassenger() {
        System.out.println("Menu de pasajeros:");
        System.out.println("0. Salir.");
        System.out.println("1. Asociar pasajero a un vuelo.");
        System.out.println("2. Consultar si un pasajero esta en un vuelo.");
        System.out.println("3. Actualizar un pasajero de un vuelo.");
        System.out.println("4. Eliminar un pasajero de un vuelo.");
        System.out.println("5. Obtener todos los psasajeros de un vuelo.");
        System.out.println("Introduzca una opción:");
    }
}
