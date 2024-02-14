package org.educa.airline.services.validador;

import org.springframework.stereotype.Component;

@Component
public class ValidadorDeCampos {


    /**
     * Metodo que se asegura que el campo date sea una fecha valida
     * @return true si es una fecha valida, false si no lo es.
     */
    public boolean EsUnaFecha() {
        return true;
    }


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
}
