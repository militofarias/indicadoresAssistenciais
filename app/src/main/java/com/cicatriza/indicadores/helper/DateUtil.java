package com.cicatriza.indicadores.helper;

import java.text.SimpleDateFormat;

public class DateUtil {

    public static String dataAtual() {
        long date = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = simpleDateFormat.format(date);
        return  dateString;
    }


}
