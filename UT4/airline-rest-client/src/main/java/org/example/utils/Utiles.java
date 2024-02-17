package org.example.utils;

import org.example.exception.ValidationFailException;

import java.util.Scanner;

public class Utiles {
    private final String DATE_FORMAT = "yyyy-MM-dd";

    public String checkCampo(Scanner scanner, String contexto, int longitud) throws ValidationFailException {
        int fallos = 0;
        String campo = "";
        while (fallos < 5) {
            System.out.println(contexto + ":");
            campo = scanner.nextLine();
            if (!campo.trim().isEmpty()) {
                campo = campo.trim();
                if (campo.length() < longitud) {
                    System.out.println(contexto + " valido");
                    return campo;
                } else {
                    fallos += 1;
                    contFallos(fallos);
                    System.out.println("El campo no puede superar los " + longitud + " caracteres.");
                }
            } else {
                fallos += 1;
                contFallos(fallos);
                System.out.println("El campo " + contexto + " no puede estar vacio o componerse unicamente de espacios.");
            }
        }
        throw new ValidationFailException();
    }

    private void contFallos(int fallos) {
        System.out.println(fallos + "/5 fallos realizados.");
    }

    public String checkfecha(Scanner scanner) throws ValidationFailException {
        int fallos = 0;
        String fecha = "";
        while (fallos < 5) {
            System.out.println("Introduzca una fecha valida (Formato de fecha valida yyyy-MM-dd)");
            fecha = scanner.nextLine();
            if (contarGuiones(fecha) == 2) {
                String[] datos = fecha.split("-");
                if (isNumeric(datos[0]) && isNumeric(datos[1]) && isNumeric(datos[2])) {
                    int anyo = Integer.parseInt(datos[0]);
                    int mes = Integer.parseInt(datos[1]);
                    int dia = Integer.parseInt(datos[2]);
                    if (anyo > 1980 && anyo < 2050) {
                        if ((mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) && (dia > 0 && dia <= 31)) {
                            return anyo+"-"+mes+"-"+dia;
                        } else if (((mes == 4 || mes == 6 || mes == 9 || mes == 11) && (dia > 0 && dia <= 30))) {
                            return anyo+"-"+mes+"-"+dia;
                        }else if (mes == 2 && (dia > 0 && dia <= 28)) {
                            return anyo+"-"+mes+"-"+dia;
                        } else {
                            fallos += 1;
                            contFallos(fallos);
                            System.out.println("Mes y dia no valido");
                        }
                    } else {
                        fallos += 1;
                        contFallos(fallos);
                        System.out.println("Anio invalido (de 1981 a 2049)");
                    }
                } else {
                    fallos += 1;
                    contFallos(fallos);
                    System.out.println("Solo numeros y guiones");
                }
            } else {
                fallos += 1;
                contFallos(fallos);
                System.out.println("Formato no valido");
            }
        }
        throw new ValidationFailException();
    }

    private int contarGuiones(String fecha) {
        int cont = 0;
        for (int i = 0; i < fecha.length(); i++) {
            if (fecha.charAt(i) == '-') {
                cont += 1;
            }
        }
        return cont;
    }

    /**
     * Metodo que pide una llamada al scanner, y tras eso llama a al metodo que valida el dni. si el dni es valido o se escribe fin lo devuelve como tal, si no devuelve -1
     * @return Un dni o una excepcion
     */
    public String checkDni(Scanner scanner) throws ValidationFailException {
        int fallos = 0;
        while (fallos < 5) {
            System.out.println("Introduzca DNI:");
            String dni = scanner.nextLine();
            if (validarDNI(dni)) {
                return dni;
            } else {
                fallos += 1;
                contFallos(fallos);
                System.out.println("DNI no valido");
            }
        }
        throw new ValidationFailException();
    }

    /**
     * Metodo que comprueba campo por campo si el dni es valido, comprobando su tamaño y composicion
     * @param dni
     * @return True si es un dni valido
     */
    private static boolean validarDNI(String dni) {
        if (dni != null && !dni.trim().isEmpty()) {
            dni = dni.trim();
            if (dni.length() == 9 && isNumeric(dni.substring(0, 8)) && isAlfabetic(dni.substring(8)) && calcularLetraControl(dni)) {
                // los 8 primeros numericos recordemos que substring es inclusivo por el inicio y exclusivo por el limite.
                return true;
            }
        }
        return false;
    }

    /**
     * metodo que comprueba que los 8 primeros caracteres del dni sean numeros
     * @param dato Solo la parte numerica de este
     * @return true si los 8 primeros caracteres del dni sean numeros
     */
    private static boolean isNumeric(String dato) {
        for (int i = 0; i < dato.length(); i++) { //Es un bucle aplicando un metodo statico de Character
            if (!Character.isDigit(dato.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Metodo que valida que el ultimo caracter del dni es una letra
     * @param dni dni, solo la parte alfabetica de este
     * @return true si el ultimo caracter del dni es una letra
     */
    private static boolean isAlfabetic(String dni) {
        for (int i = 0; i < dni.length(); i++) { //Es un bucle aplicando un metodo statico de Character (El bucle no es necesario pero estoy reutilizado codigo)
            if (!Character.isAlphabetic(dni.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Metodo que comprueba que la letra de control del dni es acertada
     * @param dni el dni
     * @return true si la letra de control es acertada false si no
     */
    private static boolean calcularLetraControl(String dni) {
        int resto = 0;
        String letras[] = {"T","R","W","A","G","M","Y","F","P","D","X","B","N","J","Z","S","Q","V","H","L","C","K","E"};
        resto = Integer.parseInt(dni.substring(0, 8)) % 23;
        if (letras[resto].equalsIgnoreCase(dni.substring(8))) {
            return true;
        }
        return false;
    }

    public int checkNumber(Scanner scanner, String contexto, int longitud) throws ValidationFailException {
        int fallos = 0;
        String campo = "";
        while (fallos < 5) {
            System.out.println(contexto + ":");
            campo = scanner.nextLine();
            if (!campo.trim().isEmpty()) {
                campo = campo.trim();
                if (isNumeric(campo)) {
                    int num = Integer.parseInt(campo);
                    if (0 < num && num <= 100) {
                        return num;
                    } else {
                        fallos += 1;
                        contFallos(fallos);
                        System.out.println("El numero de asiento debe estar comprendido entre 1 y 100");
                    }
                } else {
                    fallos += 1;
                    contFallos(fallos);
                    System.out.println("El numero de asiento debe ser un numero");
                }
            } else {
                fallos += 1;
                contFallos(fallos);
                System.out.println("No puede ser solo espacios o estar vacio");
            }
        }
        throw new ValidationFailException();
    }
}
