package org.example.utils;

import org.example.exception.ValidationFailException;

import java.util.Date;
import java.util.Scanner;

public class Utiles {

    public String checkCampo(Scanner scanner, String contexto, int longitud) throws ValidationFailException {
        int fallos = 0;
        String campo = "";
        while (fallos < 5) {
            System.out.println(contexto+":");
            campo = scanner.nextLine();
            if (!campo.trim().isEmpty()) {
                campo = campo.trim();
                if (campo.length() < longitud) {
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

    public String checkId(Scanner scanner, String nombre, int i) {
    }

    public Date checkfecha(Scanner scanner, String fecha) {
    }
}
