package org.example.service;

import org.example.api.ApiLuggageService;
import org.example.dto.LuggageDTO;
import org.example.dto.PassengerDTO;
import org.example.exception.BadRequestException;
import org.example.exception.NotFoundException;
import org.example.exception.ValidationFailException;
import org.example.exception.YaExisteException;

import java.util.Scanner;

public class LuggageService  extends Service {

    private final ApiLuggageService apiLuggageService = new ApiLuggageService();

    public void crearLuggage(Scanner scanner) {
        try {
            System.out.println();
            String id = utiles.checkCampo(scanner, "Id (unique)");
            System.out.println();
            String nif = utiles.checkCampo(scanner, "Nif");
            System.out.println();
            String cod = utiles.checkCampo(scanner, "Codigo de vuelo");
            System.out.println();
            String descripcion = utiles.checkCampo(scanner, "Descripcion");

            LuggageDTO luggageDTO = new LuggageDTO(id, nif, cod, descripcion);

            apiLuggageService.create(luggageDTO);

            System.out.println("Equipaje creado.");
        } catch (ValidationFailException e) {
            System.out.println("Fallo en la validacion de campos");
        } catch (BadRequestException e) {
            System.out.println("El equipaje esta mal construido");
        } catch (YaExisteException e) {
            System.out.println("Ya existe un equipaje con ese id.");
        } catch (NotFoundException e) {
            System.out.println("vuelo o pasajero no encontrado.");
        } catch (Exception e) {
            System.out.println("Error inesperado.");
        }
    }

    public void borrarLuggage(Scanner scanner) {
        try {
            System.out.println();
            String id = utiles.checkCampo(scanner, "Id");
            System.out.println();
            String nif = utiles.checkCampo(scanner, "Nif");
            System.out.println();
            String cod = utiles.checkCampo(scanner, "Codigo de vuelo");
            System.out.println();

            apiLuggageService.deleteLuggageFromPassenger(id, nif, cod);

            System.out.println("Equipaje borrado...");
        } catch (ValidationFailException e) {
            System.out.println("Fallaste 5 veces al introducir un campo");
        } catch (BadRequestException e) {
            System.out.println("Datos no validos");
        } catch (YaExisteException e) {
            System.out.println("Ya existe un equipaje con ese id.");
        } catch (NotFoundException e) {
            System.out.println("vuelo, pasajero o equipaje no encontrado.");
        } catch (Exception e) {
            System.out.println("Error inesperado.");
        }
    }

    public void mostarLuggageDeUnPasajero(Scanner scanner) {
        try {
            System.out.println();
            String id = utiles.checkCampo(scanner, "Id");
            System.out.println();
            String nif = utiles.checkCampo(scanner, "Nif");
            System.out.println();
            String cod = utiles.checkCampo(scanner, "Codigo de vuelo");
            System.out.println();

            LuggageDTO luggageDTO = apiLuggageService.getALuggageFromAPassenger(id, nif, cod);

            printearLuggage(luggageDTO);
        } catch (ValidationFailException e) {
            System.out.println("Fallaste 5 veces al introducir un campo");
        } catch (BadRequestException e) {
            System.out.println("Datos incorrectos");
        } catch (YaExisteException e) {
            System.out.println("Ya existe un equipaje con ese id.");
        } catch (NotFoundException e) {
            System.out.println("vuelo, pasajero o equipaje no encontrado.");
        } catch (Exception e) {
            System.out.println("Error inesperado.");
        }
    }

    public void mostrarTodosLosEquipajesDeUnVuelo(Scanner scanner) {
        try {
            String cod = utiles.checkCampo(scanner, "Codigo de vuelo");
            System.out.println();

            LuggageDTO[] luggageDTOs = apiLuggageService.getAllLuggagesFromAFlight(cod);

            for (LuggageDTO luggageDTO : luggageDTOs) {
                printearLuggage(luggageDTO);
            }
        } catch (ValidationFailException e) {
            System.out.println("Fallaste 5 veces al tratar de meter el campo.");
        } catch (BadRequestException e) {
            System.out.println("Id de vuelo no valida.");
        } catch (YaExisteException e) {
            System.out.println("Ya existe un equipaje con ese id.");
        } catch (NotFoundException e) {
            System.out.println("vuelo no encontrado.");
        } catch (Exception e) {
            System.out.println("Error inesperado.");
        }
    }

    public void mostrarTodosLosEquipajesDePasajeroEnUnVuelo(Scanner scanner) {
        try {
            String cod = utiles.checkCampo(scanner, "Codigo de vuelo");
            System.out.println();
            String nif = utiles.checkCampo(scanner, "Nif");
            System.out.println();

            LuggageDTO[] luggageDTOs = apiLuggageService.getAllLuggagesFromAPassenger(cod, nif);

            for (LuggageDTO luggageDTO : luggageDTOs) {
                printearLuggage(luggageDTO);
            }
        } catch (ValidationFailException e) {
            System.out.println("Has fallado 5 veces en no introducir campos no validos.");
        } catch (BadRequestException e) {
            System.out.println("Campos no validos, chechea el nif.");
        } catch (YaExisteException e) {
            System.out.println("Ya existe un equipaje con ese id.");
        } catch (NotFoundException e) {
            System.out.println("vuelo o pasajero no encontrado.");
        } catch (Exception e) {
            System.out.println("Error inesperado.");
        }
    }

    private void printearLuggage(LuggageDTO luggageDTO) {
        System.out.println("Id: " + luggageDTO.getId());
        System.out.println("Nif: " + luggageDTO.getNif());
        System.out.println("Codigo de vuelo: " + luggageDTO.getFlightCod());
        System.out.println("Descipcion: " + luggageDTO.getDescription());
        System.out.println();
    }
}
