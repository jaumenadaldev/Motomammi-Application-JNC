package com.dam.tfg.MotoMammiApplicationJNC.Utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MetodosGenericos {
    public static Date formatDate(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/DD");
        return simpleDateFormat.parse(date);
    }
}
