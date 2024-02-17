package org.educa.airline.services.validador;

import org.educa.airline.exceptions.MiValidacionException;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ValidadorDeCampos {

    /**
     * Metodo que se asegura si una string introducida es numerica o no
     * @param num el num que sera comprobado
     * @return true si es numerica, false si no lo es.
     */
    public boolean isANumber(String num) {
        for (int i = 0; i < num.length(); i++) {
            if (!Character.isDigit(num.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Metodo que pide una llamada al scanner, y tras eso llama a al metodo que valida el dni. si el dni es valido o se escribe fin lo devuelve como tal, si no devuelve -1
     * @return Un dni o una excepcion
     */
    public String checkDni(String dni) throws MiValidacionException {
        int fallos = 0;
        if (validarDNI(dni)) {
            return dni;
        } else {
            throw new MiValidacionException();
        }
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

    public int checkNumber(String campo) throws MiValidacionException {
        if (!campo.trim().isEmpty()) {
            campo = campo.trim();
            if (isNumeric(campo)) {
                int num = Integer.parseInt(campo);
                if (0 < num && num <= 100) {
                    return num;
                }
            }
        }
        throw new MiValidacionException();
    }
}
