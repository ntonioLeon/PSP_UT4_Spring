package org.example.utils;

import org.example.exception.ValidationFailException;

import java.util.Scanner;

public class Utiles {
    private final String DATE_FORMAT = "yyyy-MM-dd";
    private final String CARACTERES_PROHIBIDOS = "!*'();:@&=+$,/?%#[]";

    public String checkCampo(Scanner scanner, String contexto) throws ValidationFailException {
        int fallos = 0;
        String campo = "";
        while (fallos < 5) {
            if (!"fecha".equals(contexto)) {
                System.out.println(contexto + ":");
            } else {
                System.out.println(contexto + ", formato valido " + DATE_FORMAT + ":");
            }
            campo = scanner.nextLine();
            if (!campo.trim().isEmpty()) {
                campo = campo.trim();
                if (caracteresDeControl(campo)) {
                    campo = sustituirEspacios(campo);
                    return campo;
                } else {
                    fallos += 1;
                    contFallos(fallos);
                    System.out.println("El campo " + contexto + " no puede contener: " + CARACTERES_PROHIBIDOS);
                }
            } else {
                fallos += 1;
                contFallos(fallos);
                System.out.println("El campo " + contexto + " no puede estar vacio o componerse unicamente de espacios.");
            }
        }
        throw new ValidationFailException();
    }

    private boolean caracteresDeControl(String campo) {

        for (int i = 0; i < campo.length(); i++) {
            char c = campo.charAt(i);
            if (CARACTERES_PROHIBIDOS.contains(String.valueOf(c))) {
                return false;
            }
        }

        return true;
    }

    private boolean noContieneEspacios(String campo) {
        for (int i = 0; i < campo.length(); i++) {
            if (campo.charAt(i) == ' ') {
                return false;
            }
        }
        return true;
    }

    private void contFallos(int fallos) {
        System.out.println(fallos + "/5 fallos realizados.");
    }

    private String sustituirEspacios(String cadena) {
        if (cadena.contains(" ")) {
            while(cadena.contains(" ")) {  // Por si hay siete espacios.
                cadena = cadena.replace(" ", "_");
            }
        }
        return cadena;
    }
}
