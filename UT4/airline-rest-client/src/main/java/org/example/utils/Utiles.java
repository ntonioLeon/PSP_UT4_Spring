package org.example.utils;

import org.example.exception.ValidationFailException;

import java.util.Scanner;

public class Utiles {
    private final String DATE_FORMAT = "yyyy-MM-dd";

    public String checkCampo(Scanner scanner, String contexto) throws ValidationFailException {
        int fallos = 0;
        String campo = "";
        while (fallos < 5) {
            System.out.println(contexto + ":");
            campo = scanner.nextLine();
            if (!campo.trim().isEmpty()) {
                campo = campo.trim();
                if (campo.contains("/") || noContieneEspacios(campo) || campo.contains("=") || campo.contains("?")) {
                    System.out.println(contexto + " valido");
                    return campo;
                } else {
                    fallos += 1;
                    contFallos(fallos);
                    System.out.println("El campo " + contexto + " no puede contener: '/', '=', '?' o espacios.");
                }
            } else {
                fallos += 1;
                contFallos(fallos);
                System.out.println("El campo " + contexto + " no puede estar vacio o componerse unicamente de espacios.");
            }
        }
        throw new ValidationFailException();
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
}
