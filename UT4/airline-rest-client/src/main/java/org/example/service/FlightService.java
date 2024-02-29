package org.example.service;

import org.example.api.ApiFlightService;
import org.example.dto.FlightDTO;
import org.example.exception.*;

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
        } catch (NoTienesPermisoException ex) {
            System.out.println("No tienes permiso para hacer esta accion");
        } catch (NoAutenticatedException ex) {
            System.out.println("Para realizar esta accion debes estar autenticado");
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
        } catch  (NotFoundException e) {
            System.out.println("No se encontro el vuelo que se deseaba borrar.");
        } catch (NoTienesPermisoException ex) {
            System.out.println("No tienes permiso para hacer esta accion");
        } catch (NoAutenticatedException ex) {
            System.out.println("Para realizar esta accion debes estar autenticado");
        } catch(Exception nfe) {
            System.out.println("Ocurrio un error inesperado.");
        }
    }

    public void updateFlight(Scanner scanner) {
        FlightDTO flightDTO = null;
        String cod = "";
        System.out.println("Introduce el codigo de vuelo del vuelo que desea eliminar.");
        try {
            cod = utiles.checkCampo(scanner, "Codigo de vuelo");

            flightDTO = apiFlightService.getAFlightFromCod(cod);
        } catch (ValidationFailException vfe) {
            System.out.println("Se han fallado cinco veces segidas, creacion abortada.");
        }catch  (NotFoundException e) {
            System.out.println("No se encontro el vuelo que se deseaba borrar.");
        } catch (NoTienesPermisoException ex) {
            System.out.println("No tienes permiso para hacer esta accion");
        } catch (NoAutenticatedException ex) {
            System.out.println("Para realizar esta accion debes estar autenticado");
        } catch(Exception nfe) {
            System.out.println("Ocurrio un error inesperado.");
        }
        if (flightDTO != null) {
            System.out.println("vuelo encontrado");
            try {
                modificacion(scanner, flightDTO);
                apiFlightService.update(cod, flightDTO);
                System.out.println("Modificacion realizada con existo.");
            } catch (ValidationFailException ex) {
                System.out.println("ERROR EN LA MODIFICACION:");
                System.out.println("Se han fallado cinco veces segidas, modificacion abortada.");
            } catch (BadRequestException e) {
                System.out.println("ERROR EN LA MODIFICACION:");
                System.out.println("Los campos enviados no eran validos.");
            }  catch (YaExisteException ex) {
                System.out.println("ERROR EN LA MODIFICACION:");
                System.out.println("Ha intentado poner un DNI de un pasajero que existia previamente");
            } catch (NoTienesPermisoException ex) {
                System.out.println("ERROR EN LA MODIFICACION:");
                System.out.println("No tienes permiso para hacer esta accion");
            } catch (NoAutenticatedException ex) {
                System.out.println("ERROR EN LA MODIFICACION:");
                System.out.println("Para realizar esta accion debes estar autenticado");
            } catch (Exception ex) {
                System.out.println("ERROR EN LA MODIFICACION:");
                System.out.println("Error inesperado.");
            }
        }
    }

    private void modificacion(Scanner scanner, FlightDTO flightDTO) throws ValidationFailException {
        String elec = "";
        while (!"0".equals(elec)) {
            menuModif();
            elec = scanner.nextLine();
            switch (elec) {
                case "0":
                    break;
                case "1":
                    String nueCodigo = utiles.checkCampo(scanner, "Codigo de vuelo");
                    flightDTO.setCod(nueCodigo);
                    System.out.println("Modificación realizada");
                    break;
                case "2":
                    String nueId = utiles.checkCampo(scanner, "ID de vuelo");
                    flightDTO.setId(nueId);
                    System.out.println("Modificación realizada");
                    break;
                case "3":
                    String nuevaOrigen = utiles.checkCampo(scanner, "Origen de vuelo");
                    flightDTO.setOrigin(nuevaOrigen);
                    System.out.println("Modificación realizada");
                    break;
                case "4":
                    String nuevaDestino = utiles.checkCampo(scanner, "Destino de vuelo");
                    flightDTO.setDestination(nuevaDestino);
                    System.out.println("Modificación realizada");
                    break;
                case "5":
                    String nuevaFecha = utiles.checkCampo(scanner, "Fecha de vuelo");
                    flightDTO.setDate(nuevaFecha);
                    System.out.println("Modificación realizada");
                    break;
            }
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
        } catch (NoTienesPermisoException ex) {
            System.out.println("No tienes permiso para hacer esta accion");
        } catch (NoAutenticatedException ex) {
            System.out.println("Para realizar esta accion debes estar autenticado");
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
        } catch (NoTienesPermisoException ex) {
            System.out.println("No tienes permiso para hacer esta accion");
        } catch (NoAutenticatedException ex) {
            System.out.println("Para realizar esta accion debes estar autenticado");
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

    private void menuModif() {
        System.out.println("Menu de modificacion.");
        System.out.println("1. Para codigo de vuelo.");
        System.out.println("2. Para id de avion.");
        System.out.println("3. Para origen de vuelo.");
        System.out.println("4. Para destino del vuelo.");
        System.out.println("5. Para fecha de vuelo.");
        System.out.println("0. Para volver.");
        System.out.println("Seleccione una opcion:");
    }
}
