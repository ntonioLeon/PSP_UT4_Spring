package org.example.service;

import org.example.api.ApiUserService;
import org.example.core.Cliente;
import org.example.dto.UserDTO;
import org.example.exception.*;
import org.example.utils.Utiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserService extends Service {

    private final ApiUserService apiUserService = new ApiUserService();
    public void createUser(Scanner scanner) {
        try {
            System.out.println("Indroduce los datos del usuario");
            String userName = utiles.checkCampo(scanner, "Username");
            System.out.println();
            String password = utiles.checkCampo(scanner, "Contraseña");
            System.out.println();
            String nif = utiles.checkCampo(scanner, "NIF");
            System.out.println();
            String name = utiles.checkCampo(scanner, "Nombre");
            System.out.println();
            String surname = utiles.checkCampo(scanner, "Apellido");
            System.out.println();
            String email = utiles.checkCampo(scanner, "Email");
            System.out.println();
            List<String> roles = askforRoles(scanner);
            System.out.println();

            UserDTO userDTO = new UserDTO(userName, password, nif, name, surname, email, roles);

            apiUserService.createUser(userDTO);

            System.out.println("Usuario creado");
        } catch (ValidationFailException ex) {
            System.out.println("Fallo 5 veces en la creacion de campos");
        } catch (YaExisteException ex) {
            System.out.println("Ya existe un usuario con esos datos.");
        } catch (BadRequestException ex) {
            System.out.println("Los datos del usuario no son validos.");
        } catch (NotFoundException ex) {
            System.out.println("No se encontro el endpoint, pobablemente se ha roto la URL por algun caracter raro.");
        } catch (NoTienesPermisoException ex) {
            System.out.println("No tienes permiso para crear."); //No se usara, es anonimo, pero para que el IDE se calle.
        } catch (NoAutenticatedException ex) {
            System.out.println("Recuerda que debes estar autenticado.");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error inesperado.");
        }
    }

    private List<String> askforRoles(Scanner scanner) {
        List<String> roles = new ArrayList<>();
        String opt = "-1";
        while (!"0".equals(opt)) {
            printRoles();
            opt = scanner.nextLine();
            switch (opt) {
                case "0":
                    break;
                case "1":
                    if (!roles.contains("ROLE_admin")) {
                        roles.add("ROLE_admin");
                        System.out.println("Rol agregado.");
                    } else {
                        System.out.println("El usuario ya es administrador");
                        if (utiles.confirmaciom(scanner, "Desea eliminar el rol admin del usuario?")) {
                            roles.remove("ROLE_admin");
                            System.out.println("Rol retirado");
                        }
                    }
                    break;
                case "2":
                    if (!roles.contains("ROLE_personal")) {
                        roles.add("ROLE_personal");
                        System.out.println("Rol agregado.");
                    } else {
                        System.out.println("El usuario ya es personal");
                        if (utiles.confirmaciom(scanner, "Desea eliminar el rol personal del usuario?")) {
                            roles.remove("ROLE_personal");
                            System.out.println("Rol retirado");
                        }
                    }
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        }
        roles.add("ROLE_usuario");
        return roles;
    }

    public void deleteUser(Scanner scanner) {
        try {
            System.out.println("Introduzca el usarname del usuario que desea borrar.");
            String id = utiles.checkCampo(scanner,  "Username");

            apiUserService.deleteUser(id);

            System.out.println("Usuario borrado");
            if (id.equals(Cliente.userName)) {
                Cliente.userName = "";
                Cliente.password = "";
                System.out.println("Puesto que ha borrado el usuario con el que está loggado se le ha desloggeado automaticamente.");
            }
        } catch (ValidationFailException ex) {
            System.out.println("Fallaste 5 veces...");
        } catch (BadRequestException ex) {
            System.out.println("Los datos del usuario no son validos.");
        } catch (NotFoundException ex) {
            System.out.println("No se encontro el user que queria borrar.");
        } catch (NoTienesPermisoException ex) {
            System.out.println("No tienes permiso para borrar."); //No se usara, es anonimo, pero para que el IDE se calle.
        } catch (NoAutenticatedException ex) {
            System.out.println("Recuerda que debes estar autenticado.");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error inesperado.");
        }
    }

    public void updateUser(Scanner scanner) {
        String id = null;
        UserDTO userDTO = null;
        try {
            System.out.println("Introduzca el id del usuario que desea modificar.");
             id = utiles.checkCampo(scanner,  "Username");

            userDTO = apiUserService.getUser(id);
            print(userDTO);
        } catch (ValidationFailException ex) {
            System.out.println("Fallaste 5 veces...");
        } catch (BadRequestException ex) {
            System.out.println("Los datos del usuario no son validos.");
        } catch (NotFoundException ex) {
            System.out.println("No se encontro el usuario.");
        } catch (NoTienesPermisoException ex) {
            System.out.println("No tienes permiso para buscar a ese usuario."); //No se usara, es anonimo, pero para que el IDE se calle.
        } catch (NoAutenticatedException ex) {
            System.out.println("Recuerda que debes estar autenticado.");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error inesperado.");
        }

        if (userDTO != null) {

            try {
                modificacion(scanner, userDTO);
                apiUserService.updateUser(id, userDTO);
                System.out.println("Modificacion realizada con existo.");
            } catch (ValidationFailException ex) {
                System.out.println("ERROR EN LA MODIFICACION:");
                System.out.println("Fallo 5 veces en la creacion de campos");
            } catch (YaExisteException ex) {
                System.out.println("ERROR EN LA MODIFICACION:");
                System.out.println("Ya existe un usuario con esos datos.");
            } catch (BadRequestException ex) {
                System.out.println("ERROR EN LA MODIFICACION:");
                System.out.println("Los datos del usuario no son validos.");
            } catch (NotFoundException ex) {
                System.out.println("ERROR EN LA MODIFICACION:");
                System.out.println("No se encontro el user que queria modificar.");
            } catch (NoTienesPermisoException ex) {
                System.out.println("ERROR EN LA MODIFICACION:");
                System.out.println("No tienes permiso para modificar."); //No se usara, es anonimo, pero para que el IDE se calle.
            } catch (NoAutenticatedException ex) {
                System.out.println("ERROR EN LA MODIFICACION:");
                System.out.println("Recuerda que debes estar autenticado.");
            } catch (Exception e) {
                System.out.println("ERROR EN LA MODIFICACION:");
                System.out.println("Ha ocurrido un error inesperado.");
            }
        }
    }

    private void modificacion(Scanner scanner, UserDTO userDTO) throws ValidationFailException {
        String elec = "";
        while (!"0".equals(elec)) {
            menuModif();
            elec = scanner.nextLine();
            switch (elec) {
                case "0":
                    break;
                case "1":
                    String nuevoUsername = utiles.checkCampo(scanner, "Username");
                    userDTO.setUsername(nuevoUsername);
                    System.out.println("Modificacion realizada");
                    break;
                case "2":
                    String nuevaPassword = utiles.checkCampo(scanner, "Password");
                    userDTO.setPassword(nuevaPassword);
                    System.out.println("Modificacion realizada");
                    break;
                case "3":
                    String nuevoNif = utiles.checkCampo(scanner, "Nif (unique)");
                    userDTO.setNif(nuevoNif);
                    System.out.println("Modificacion realizada");
                    break;
                case "4":
                    String nuevoNombre = utiles.checkCampo(scanner, "Name");
                    userDTO.setName(nuevoNombre);
                    System.out.println("Modificacion realizada");
                    break;
                case "5":
                    String nuevoApellido = utiles.checkCampo(scanner, "Surname");
                    userDTO.setSurname(nuevoApellido);
                    System.out.println("Modificacion realizada");
                    break;
                case "6":
                    String nuevoEmail = utiles.checkCampo(scanner, "Email");
                    userDTO.setEmail(nuevoEmail);
                    System.out.println("Modificacion realizada");
                    break;
                case "7":
                    userDTO.setRoles(modicarRoles(scanner, userDTO.getRoles()));
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
        }
    }

    private List<String> modicarRoles(Scanner scanner, List<String> roles) {
        if (!roles.isEmpty()) {
            System.out.println("Roles del usuario: ");
            for (String rol : roles) {
                System.out.print(rol + " ");
            }
        }
        String opt = "-1";
        while (!"0".equals(opt)) {
            System.out.println("Elegir un rol existente servira como indicativo de querer eliminarlo.");
            printRoles();
            opt = scanner.nextLine();
            switch (opt) {
                case "0":
                    break;
                case "1":
                    if (!roles.contains("ROLE_admin")) {
                        roles.add("ROLE_admin");
                        System.out.println("Rol agregado.");
                    } else {
                        if (utiles.confirmaciom(scanner, "Seguro que desea eliminar el rol admin del usuario?")) {
                            roles.remove("ROLE_admin");
                            System.out.println("Rol retirado.");
                        }
                    }
                    break;
                case "2":
                    if (!roles.contains("ROLE_personal")) {
                        roles.add("ROLE_personal");
                        System.out.println("Rol agregado.");
                    } else {
                        if (utiles.confirmaciom(scanner, "Seguro que desea eliminar el rol personal del usuario?")) {
                            roles.remove("ROLE_personal");
                            System.out.println("Rol retirado.");
                        }
                    }
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        }
        return roles;
    }

    public void readUser(Scanner scanner) {
        try {
            System.out.println("Introduzca el usarname del usuario que desea buscar.");
            String id = utiles.checkCampo(scanner,  "Username");

            UserDTO userDTO = apiUserService.getUser(id);

            print(userDTO);
        } catch (ValidationFailException ex) {
            System.out.println("Fallaste 5 veces...");
        } catch (BadRequestException ex) {
            System.out.println("Los datos del usuario no son validos.");
        } catch (NotFoundException ex) {
            System.out.println("No se encontro el usuario.");
        } catch (NoTienesPermisoException ex) {
            System.out.println("No tienes permiso para buscar a ese usuario."); //No se usara, es anonimo, pero para que el IDE se calle.
        } catch (NoAutenticatedException ex) {
            System.out.println("Recuerda que debes estar autenticado.");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error inesperado.");
        }
    }

    private void printRoles() {
        System.out.println("Asignacion de roles.");
        System.out.println("1. Administrador");
        System.out.println("2. Personal");
        System.out.println("0. salir");
        System.out.println("Elija una opcion:");
    }

    private void print(UserDTO userDTO) {
        System.out.println("UserName: " + userDTO.getUsername());
        System.out.println("NIF: " + userDTO.getNif());
        System.out.println("Nombre: " + userDTO.getName());
        System.out.println("Apellido: " + userDTO.getSurname());
        System.out.println("Email: " + userDTO.getEmail());
        System.out.print("Roles: ");
        for (String rol : userDTO.getRoles()) {
            System.out.print(rol + " ");
        }
        System.out.println("\n"); //Doble salto si, para conpensar los print de antes
    }

    private void menuModif() {
        System.out.println("1. Cambiar userName.");
        System.out.println("2. Cambiar password.");
        System.out.println("3. Cambiar NIF.");
        System.out.println("4. Cambiar nombre.");
        System.out.println("5. Cambiar apellido.");
        System.out.println("6. Cambiar email.");
        System.out.println("7. Cambiar roles.");
        System.out.println("0. Volver.");
    }
}
