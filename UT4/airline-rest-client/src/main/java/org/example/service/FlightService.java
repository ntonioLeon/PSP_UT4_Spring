package org.example.service;

import org.example.api.ApiFlightService;
import org.example.dto.FlightDTO;
import org.example.exception.BadRequestException;
import org.example.exception.NotFoundException;
import org.example.exception.ValidationFailException;
import org.example.exception.YaExisteException;

import java.util.Date;
import java.util.Scanner;

public class FlightService extends Service {

    private final ApiFlightService apiFlightService = new ApiFlightService();

    public void createFlight(Scanner scanner) {
        try {
            System.out.println("Indroduce ");
            String id = utiles.checkCampo(scanner, "Id del Avion");
            System.out.println();
            String codigo = utiles.checkCampo(scanner, "Codigo de vuelo (unique)");
            System.out.println();

            String origin = utiles.checkCampo(scanner, "Origen");
            System.out.println();
            String destination = utiles.checkCampo(scanner, "Destino");
            System.out.println();
            String date = utiles.checkCampo(scanner, "fecha");
            System.out.println();

            FlightDTO flightDTO = new FlightDTO(codigo, id, origin, destination, date);
            apiFlightService.create(flightDTO);
            System.out.println("Vuelo creado");
        } catch (ValidationFailException ex) {
            System.out.println("Se han fallado cinco veces segidas, creacion abortada.");
        } catch (YaExisteException e) {
            System.out.println("Ya existe un vuelo con ese codigo de vuelo.");
        } catch (BadRequestException e) {
            System.out.println("Los campos no son correctos, compruebe que la fecha es valida.");
        } catch (Exception ex) {
            System.out.println("Fallo en la creacion del vuelo.");
        }
    }

    public void deleteFlight(Scanner scanner) {
        System.out.println("Introduce el codigo de vuelo del vuelo que desea eliminar.");
        try {
            String cod = utiles.checkCampo(scanner, "Codigo de vuelo");

            apiFlightService.delete(cod);
            System.out.println("Vuelo borrado.");
        } catch (ValidationFailException vfe) {
            System.out.println("Se han fallado cinco veces segidas, creacion abortada.");
        }catch  (NotFoundException e) {
            System.out.println("No se encontro el vuelo que se deseaba borrar.");
        } catch(Exception nfe) {
            System.out.println("Ocurrio un error inesperado.");
        }
    }

    public void findFlightFromDate(Scanner scanner) {
        System.out.println("Introduce el codigo de vuelo y la fecha.");
        try {
            String codigo = utiles.checkCampo(scanner, "Codigo de vuelo");
            System.out.println();
            String date = utiles.checkCampo(scanner, "fecha");
            System.out.println();

            FlightDTO flightDTO = apiFlightService.getFlightsFromCodAndDate(codigo, date);

            printearVuelo(flightDTO);
        } catch (ValidationFailException vfe) {
            System.out.println("Se han fallado cinco veces segidas, creacion abortada.");
        } catch (NotFoundException e) {
            System.out.println("No se encontro el vuelo que se deseaba buscar.");
        } catch (BadRequestException e) {
            System.out.println("Los campos no son correctos, compruebe que la fecha es valida.");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un erro inesperado");
        }
    }

    public void getFlightsFromOriAndDest(Scanner scanner) {
        System.out.println("Introduce el origen y destino de los vuelos que deseas buscar.");
        try {
            String origin = utiles.checkCampo(scanner, "Origen");
            System.out.println();
            String destination = utiles.checkCampo(scanner, "Destino");
            System.out.println();

            FlightDTO[] flightDTOlist = apiFlightService.getFlightFromOriAndDest(origin, destination);

            for (FlightDTO flightDTO : flightDTOlist) {
                printearVuelo(flightDTO);
            }
        } catch (ValidationFailException ex) {
            System.out.println("Se han fallado cinco veces segidas, creacion abortada.");
        } catch (NotFoundException e) {
            System.out.println("No se encontro el vuelo que se deseaba buscar.");
        } catch (Exception nfe) {
            System.out.println("Un error inesperado a ocurrido");
        }
    }

    private void printearVuelo(FlightDTO flightDTO) {
        System.out.println("Id del avion: " + flightDTO.getId());
        System.out.println("Codigo de vuelo: " + flightDTO.getCod());
        System.out.println("Origen: " + flightDTO.getOrigin());
        System.out.println("Destino: " + flightDTO.getDestination());
        System.out.println("Fecha: " + flightDTO.getDate());
        System.out.println();
    }
}
