package org.example.service;

import org.example.api.ApiPassengerService;
import org.example.dto.PassengerDTO;
import org.example.exception.ValidationFailException;

import java.util.*;

public class PassengerService extends Service {

    private final ApiPassengerService apiPassengerService = new ApiPassengerService();
    private Map<String, PassengerDTO> pasajeros = new HashMap<>();

    public void createPassenger(Scanner scanner) {
        try {
            System.out.println("Indroduce ");
            String nif = utiles.checkDni(scanner);
            System.out.println();
            String codigo = utiles.checkCampo(scanner, "Codigo de vuelo", 25);
            System.out.println();
            String nombre = utiles.checkCampo(scanner, "nombre", 25);
            System.out.println();
            String apellido = utiles.checkCampo(scanner, "Apellido", 25);
            System.out.println();
            String email = utiles.checkCampo(scanner, "Email", 25);
            System.out.println();
            int number = utiles.checkNumber(scanner, "Numero de asiento", 100);
            System.out.println();

            pasajeros.put(nif, new PassengerDTO(nif, codigo, nombre, apellido, email, number));

            System.out.println("Pasajero creado");
        } catch (ValidationFailException ex) {
            System.out.println("Se han fallado cinco veces segidas, creacion abortada.");
        } catch (Exception ex) {
            System.out.println("Fallo en la creacion del vuelo.");
        }
    }

    public void associatePassenger(Scanner scanner) {
        try {
            System.out.println("Introduce el NIF del pasajero y el codigo de vuelo para ");
            String nif = utiles.checkDni(scanner);
            System.out.println();
            String codigo = utiles.checkCampo(scanner, "Codigo de vuelo", 25);
            System.out.println();

            if (pasajeros.containsKey(nif) ) {
                apiPassengerService.asociar(codigo, pasajeros.get(nif));
            }

            System.out.println("Pasajero asociado...");
        } catch (ValidationFailException ex) {
            System.out.println("Se han fallado cinco veces segidas, creacion abortada.");
        } catch (Exception ex) {
            System.out.println("Fallo en la creacion del vuelo.");
        }
    }

    public void findAPassengerFromAFlight(Scanner scanner) {
        try {
            System.out.println("Introduce el NIF del pasajero y el codigo de vuelo para encontrar al pasajero.");
            String nif = utiles.checkDni(scanner);
            System.out.println();
            String codigo = utiles.checkCampo(scanner, "Codigo de vuelo", 25);
            System.out.println();

            PassengerDTO passengerDTO = apiPassengerService.findFromNif(codigo, nif);

            printearPassenger(passengerDTO);
        } catch (ValidationFailException ex) {
            System.out.println("Se han fallado cinco veces segidas, creacion abortada.");
        } catch (Exception ex) {
            System.out.println("Fallo en la creacion del vuelo.");
        }
    }

    public void updatePasengerFromFlight(Scanner scanner) {
        PassengerDTO passengerDTO = null;
        try {
            System.out.println("Introduce el NIF del pasajero y el codigo de vuelo para encontrar al pasajero para la modificacion.");
            String nif = utiles.checkDni(scanner);
            System.out.println();
            String codigo = utiles.checkCampo(scanner, "Codigo de vuelo", 25);
            System.out.println();

            passengerDTO = apiPassengerService.findFromNif(codigo, nif);

            printearPassenger(passengerDTO);
        } catch (ValidationFailException ex) {
            System.out.println("Se han fallado cinco veces segidas, creacion abortada.");
        } catch (Exception ex) {
            System.out.println("Fallo en la creacion del vuelo.");
        }

        if (passengerDTO != null) {
            System.out.println("Pasajero encontrado");
            modificacion(scanner, passengerDTO);
        }
    }

    private void modificacion(Scanner scanner, PassengerDTO passengerDTO) {
        String elec = "";
        while (!"0".equals(elec)) {
            menuModif();
            elec = scanner.nextLine();
            switch (elec) {
                case "0":
                    break;
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    break;
                case "6":
                    break;
                default:
                    break;
            }
        }
    }

    public void deletePassengerFromFlight(Scanner scanner) {
        try {
            System.out.println("Introduce el NIF del pasajero y el codigo de vuelo para encontrar al pasajero.");
            String nif = utiles.checkDni(scanner);
            System.out.println();
            String codigo = utiles.checkCampo(scanner, "Codigo de vuelo", 25);
            System.out.println();

            apiPassengerService.desAsociar(codigo, nif);

            System.out.println("Borrado...");
        } catch (ValidationFailException ex) {
            System.out.println("Se han fallado cinco veces segidas, creacion abortada.");
        } catch (Exception ex) {
            System.out.println("Fallo en la creacion del vuelo.");
        }
    }

    public void findAllFromAFlight(Scanner scanner) {
        try {
            System.out.println("Introduce el codigo de vuelo.");
            String codigo = utiles.checkCampo(scanner, "Codigo de vuelo", 25);
            System.out.println();

            PassengerDTO[] passengerDTOs = apiPassengerService.findAllFromCod(codigo);

            for (PassengerDTO passengerDTO : passengerDTOs) {
                printearPassenger(passengerDTO);
            }
        } catch (ValidationFailException ex) {
            System.out.println("Se han fallado cinco veces segidas, creacion abortada.");
        } catch (Exception ex) {
            System.out.println("Fallo en la creacion del vuelo.");
        }
    }

    private void printearPassenger(PassengerDTO passengerDTO) {
        System.out.println("NIF:" + passengerDTO.getNif());
        System.out.println("Codigo de vuelo" + passengerDTO.getFlightCod());
        System.out.println("Nombre" + passengerDTO.getName());
        System.out.println("Apellido" + passengerDTO.getSurname());
        System.out.println("Email" + passengerDTO.getEmail());
        System.out.println("Numero de asiento" + passengerDTO.getSeatNumber());
        System.out.println();
    }

    private void menuModif() {
        System.out.println("1. Modificar NIF:");
        System.out.println("2. Modificar Codigo de vuelo");
        System.out.println("3. Modificar Nombre");
        System.out.println("4. Modificar Apellido");
        System.out.println("5. Modificar Email");
        System.out.println("6. Modificar Numero de asiento");
        System.out.println("0. Salir");
    }
}
