package org.educa.airline.mappers;

import org.educa.airline.exceptions.MiValidacionException;
import org.educa.airline.services.validador.ValidadorDeCampos;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Component
public abstract class Mapper {
    public final ValidadorDeCampos validadorDeCampos = new ValidadorDeCampos();

    public int fromStringToInt(String seatNumber) throws MiValidacionException {
        if (validadorDeCampos.isANumber(seatNumber)) {
            return Integer.parseInt(seatNumber);
        } else {
            throw new MiValidacionException();
        }

    }

    public String fromIntTOString(int seatNumber) {
        return String.valueOf(seatNumber);
    }

    public String fromDateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public Date fromStringToDate(String fecha) throws MiValidacionException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            return formatter.parse(fecha);
        } catch (ParseException e) {
            throw new MiValidacionException();
        }
    }
}
