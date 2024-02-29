package org.example.core;

import org.example.service.UserService;

import java.util.Scanner;

public class ClienteUser {

    private UserService userService = new UserService();

    public void run(Scanner scanner) {
        String opt = "-1";
        while (!"0".equals(opt)) {
            menuUsers();
            opt = scanner.nextLine();
            switch (opt) {
                case "0":
                    System.out.println("Volviendo al menu principal.");
                    break;
                case "1":
                    userService.createUser(scanner);
                    break;
                case "2":
                    userService.deleteUser(scanner);
                    break;
                case "3":
                    userService.updateUser(scanner);
                    break;
                case "4":
                    userService.readUser(scanner);
                    break;
                default:
                    System.err.println("opción no valida.");
            }
        }
    }

    private void menuUsers() {
        System.out.println("Menu de usuarios.");
        System.out.println("1. Crear un usuario.");
        System.out.println("2. Borrar un usuario.");
        System.out.println("3. Modificar un usuario.");
        System.out.println("4. Buscar un usuario.");
        System.out.println("Introduzca una opcion:");
    }
}
