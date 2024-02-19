package org.example.core;

import org.example.service.LuggageService;

import java.util.Scanner;

public class ClienteLuggage {
    LuggageService luggageService = new LuggageService();

    public void run(Scanner scanner) throws Exception {
        String opt = "-1";
        while (!"0".equals(opt)) {
            menuLuggage();
            opt = scanner.nextLine();
            switch (opt) {
                case "0":
                    System.out.println("Volviendo al menu principal.");
                    break;
                case "1":
                    luggageService.crearLuggage(scanner);
                    break;
                case "2":
                    luggageService.borrarLuggage(scanner);
                    break;
                case "3":
                    luggageService.mostarLuggageDeUnPasajero(scanner);
                    break;
                case "4":
                    luggageService.MostrarTodosLosEquipajesDeUnVuelo(scanner);
                    break;
                default:
                    System.err.println("opción no valida.");
            }
        }
    }

    private void menuLuggage() {
        System.out.println("Menu de equipajes:");
        System.out.println("0. Salir");
        System.out.println("1. Crear un equipaje");
        System.out.println("2. Borrar un equipaje");
        System.out.println("3. Mostrar el equipale de un pasajero");
        System.out.println("4. Mostrar todos los equipajes de un vuelo");
        System.out.println("Introduzca una opción");
    }
}
