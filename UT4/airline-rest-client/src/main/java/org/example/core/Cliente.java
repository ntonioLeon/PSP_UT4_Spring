package org.example.core;

import java.util.Scanner;

public class Cliente {

    public static String userName = "";
    public static String password = "";
    private ClienteFlight clienteFlight;
    private ClientePassenger clientePassenger;
    private ClienteLuggage clienteLuggage;
    private ClienteUser clienteUser;

    public Cliente() {
        this.clienteFlight = new ClienteFlight();
        this.clientePassenger = new ClientePassenger();
        this.clienteLuggage = new ClienteLuggage();
        this.clienteUser = new ClienteUser();
    }

    public void run() throws Exception {
        int opt = -1;
        System.out.println("Bienvenido al cliente de la tarea UT4!");
        try(Scanner scanner = new Scanner(System.in)){
            while(opt != 0) {
                printMenu();
                try {
                    opt = scanner.nextInt();
                } catch (Exception ex) {
                    System.out.println("Introduzca únicamente números enteros.");
                }
                scanner.nextLine();
                switch (opt) {
                    case 0:
                        System.out.println("Saliendo.");
                        break;
                    case 1:
                        clienteFlight.run(scanner);
                        break;
                    case 2:
                        clientePassenger.run(scanner);
                        break;
                    case 3:
                        clienteLuggage.run(scanner);
                        break;
                    case 4:
                        clienteUser.run(scanner);
                        break;
                    default:
                        System.err.println("opción no valida.");
                }
            }
        }
    }

    private void printMenu() {
        System.out.println("Menu principal:");
        System.out.println("0. Salir.");
        System.out.println("1. Menu de vuelos.");
        System.out.println("2. Menu de pasajeros.");
        System.out.println("3. Menu de eqipaje.");
        System.out.println("4. Menu de usuario.");
        System.out.println("Introduzca una opción:");
    }
}
