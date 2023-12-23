package com.marcosflobo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils {

  public static String getDateToday() {
    LocalDate fechaActual = LocalDate.now();

    // Formatear la fecha en el formato deseado (YYYY/MM/DD)
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    return fechaActual.format(formato);
  }
}
