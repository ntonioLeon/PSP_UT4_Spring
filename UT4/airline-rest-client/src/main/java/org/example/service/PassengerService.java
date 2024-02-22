package org.example.service;

import org.example.api.ApiPassengerService;
import org.example.dto.PassengerDTO;
import org.example.exception.BadRequestException;
import org.example.exception.NotFoundException;
import org.example.exception.ValidationFailException;
import org.example.exception.YaExisteException;

import java.util.*;

public class PassengerService extends Service {

    private final ApiPassengerService apiPassengerService = new ApiPassengerService();

    public void associatePassenger(Scanner scanner) {
        try {
            System.out.println("Indroduce ");
            String nif = utiles.checkCampo(scanner, "nif (unique)");
            System.out.println();
            String codigo = utiles.checkCampo(scanner, "Codigo de vuelo");
            System.out.println();
            String nombre = utiles.checkCampo(scanner, "nombre");
            System.out.println();
            String apellido = utiles.checkCampo(scanner, "Apellido");
            System.out.println();
            String email = utiles.checkCampo(scanner, "Email");
            System.out.println();
            String number = utiles.checkCampo(scanner, "Numero de asiento");
            System.out.println();

            PassengerDTO passengerDTO = new PassengerDTO(nif, codigo, nombre, apellido, email, number);

            apiPassengerService.asociar(passengerDTO.getFlightCod(), passengerDTO);

            System.out.println("Pasajero asociado...");
        } catch (ValidationFailException ex) {
            System.out.println("Se han fallado cinco veces segidas, creacion abortada.");
        } catch (BadRequestException e) {
            System.out.println("Los campos del pasajero no son validos, compruebe que el NIF sea valido y el asiento un numero entero.");
        } catch (NotFoundException e) {
            System.out.println("Vuelo no encontrado");
        } catch (YaExisteException ex) {
            System.out.println("Ya existe un pasajero con ese NIF en ese vuelo.");
        } catch (Exception ex) {
            System.out.println("Fallo en la creacion del pasajero.");
        }
    }

    public void findAPassengerFromAFlight(Scanner scanner) {
        try {
            System.out.println("Introduce el NIF del pasajero y el codigo de vuelo para encontrar al pasajero.");
            String nif = utiles.checkCampo(scanner, "Nif");
            System.out.println();
            String codigo = utiles.checkCampo(scanner, "Codigo de vuelo");
            System.out.println();

            PassengerDTO passengerDTO = apiPassengerService.findFromNif(codigo, nif);

            printearPassenger(passengerDTO);
        } catch (ValidationFailException ex) {
            System.out.println("Se han fallado cinco veces segidas, creacion abortada.");
        } catch (NotFoundException e) {
            System.out.println("No se encontro el vuelo.");
        } catch (Exception ex) {
            System.out.println("Fallo en la creacion del vuelo.");
        }
    }

    public void updatePasengerFromFlight(Scanner scanner) {
        PassengerDTO passengerDTO = null;
        String codigo = "";
        String nif = "";
        try {
            System.out.println("Introduce el NIF del pasajero y el codigo de vuelo para encontrar al pasajero para la modificacion.");
            nif = utiles.checkCampo(scanner, "Nif");
            System.out.println();
            codigo = utiles.checkCampo(scanner, "Codigo de vuelo");
            System.out.println();

            passengerDTO = apiPassengerService.findFromNif(codigo, nif);

            printearPassenger(passengerDTO);
        } catch (ValidationFailException ex) {
            System.out.println("Se han fallado cinco veces segidas, creacion abortada.");
        } catch (NotFoundException e) {
            System.out.println("No se encontro el pasajero que se deseaba modificar.");
        } catch (BadRequestException ex) {
            System.out.println("Error en la integridad de los campos compruebe el dni y el nuemro de asiento");
        } catch (YaExisteException ex) {
            System.out.println("Ha intentado poner un DNI de un pasajero que existia previamente");
        } catch (Exception ex) {
            System.out.println("Fallo en la creacion del vuelo.");
        }

        if (passengerDTO != null) {
            System.out.println("Pasajero encontrado");
            try {
                modificacion(scanner, passengerDTO);
                apiPassengerService.updatePassenger(codigo, nif, passengerDTO);
                System.out.println("Modificacion realizada con existo.");
            } catch (ValidationFailException ex) {
                System.out.println("ERROR EN LA MODIFICACION:");
                System.out.println("Se han fallado cinco veces segidas, creacion abortada.");
            } catch (BadRequestException e) {
                System.out.println("ERROR EN LA MODIFICACION:");
                System.out.println("Los campos enviados no eran validos.");
            } catch (NotFoundException e) {
                System.out.println("ERROR EN LA MODIFICACION:");
                System.out.println("No se encontro el vuelo en el que desea asignar al pasajero. ");
            } catch (YaExisteException ex) {
                System.out.println("Ha intentado poner un DNI de un pasajero que existia previamente");
                System.out.println("ERROR EN LA MODIFICACION:");
            } catch (Exception ex) {
                System.out.println("ERROR EN LA MODIFICACION:");
                System.out.println("Error inesperado.");
            }
        }
    }

    private void modificacion(Scanner scanner, PassengerDTO passengerDTO) throws ValidationFailException {
        String elec = "";
        while (!"0".equals(elec)) {
            menuModif();
            elec = scanner.nextLine();
            switch (elec) {
                case "0":
                    System.out.println();
                    break;
                case "1": //nif
                    String nueNif = utiles.checkCampo(scanner, "Nif (unique)");
                    passengerDTO.setNif(nueNif);
                    System.out.println("Modificacion realizada");
                    break;
                case "2": //cod
                    String nueCodigo = utiles.checkCampo(scanner, "Codigo de vuelo");
                    passengerDTO.setFlightCod(nueCodigo);
                    System.out.println("Modificacion realizada");
                    break;
                case "3": //nomb
                    String nueNombre = utiles.checkCampo(scanner, "Nombre");
                    passengerDTO.setName(nueNombre);
                    System.out.println("Modificacion realizada");
                    break; //ape
                case "4":
                    String nueApe = utiles.checkCampo(scanner, "Apellido");
                    passengerDTO.setSurname(nueApe);
                    System.out.println("Modificacion realizada");
                    break; //email
                case "5":
                    String nueMail = utiles.checkCampo(scanner, "Email");
                    passengerDTO.setEmail(nueMail);
                    System.out.println("Modificacion realizada");
                    break; //seat
                case "6":
                    String nueSeat = utiles.checkCampo(scanner, "Numero de asiento");
                    passengerDTO.setSeatNumber(nueSeat);
                    System.out.println("Modificacion realizada");
                    break;
                default:
                    System.out.println("Opcion no valida.");
                    break;
            }
        }
    }

    public void deletePassengerFromFlight(Scanner scanner) {
        try {
            System.out.println("Introduce el NIF del pasajero y el codigo de vuelo para encontrar al pasajero.");
            String nif = utiles.checkCampo(scanner, "Nif");
            System.out.println();
            String codigo = utiles.checkCampo(scanner, "Codigo de vuelo");
            System.out.println();

            apiPassengerService.desAsociar(codigo, nif);

            System.out.println("Borrado...");
        } catch (ValidationFailException ex) {
            System.out.println("Se han fallado cinco veces segidas, creacion abortada.");
        } catch (NotFoundException e) {
            System.out.println("Vuelo o pasajero no encontrado.");
        }catch (Exception ex) {
            System.out.println("Fallo en la creacion del vuelo.");
        }
    }

    public void findAllFromAFlight(Scanner scanner) {
        try {
            System.out.println("Introduce el codigo de vuelo.");
            String codigo = utiles.checkCampo(scanner, "Codigo de vuelo");
            System.out.println();

            PassengerDTO[] passengerDTOs = apiPassengerService.findAllFromCod(codigo);

            for (PassengerDTO passengerDTO : passengerDTOs) {
                printearPassenger(passengerDTO);
            }
        } catch (ValidationFailException ex) {
            System.out.println("Se han fallado cinco veces segidas, creacion abortada.");
        } catch (NotFoundException e) {
            System.out.println("No se encontro el vuelo.");
        }catch (Exception ex) {
            System.out.println("Ha ocurrido un error inesperado.");
        }
    }

    private void printearPassenger(PassengerDTO passengerDTO) {
        System.out.println("NIF: " + passengerDTO.getNif());
        System.out.println("Codigo de vuelo: " + passengerDTO.getFlightCod());
        System.out.println("Nombre: " + passengerDTO.getName());
        System.out.println("Apellido: " + passengerDTO.getSurname());
        System.out.println("Email: " + passengerDTO.getEmail());
        System.out.println("Numero de asiento: " + passengerDTO.getSeatNumber());
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
