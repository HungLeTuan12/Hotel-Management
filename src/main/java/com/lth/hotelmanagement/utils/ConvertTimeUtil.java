package com.lth.hotelmanagement.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertTimeUtil {
    public static Date stringToDate(String s) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = dateFormat.parse(s);
            System.out.println("Value: " + date);
            return date;
        }
        catch (Exception e) {
            System.out.println("Have error parse: " + e.getMessage());
            return null;
        }
    }
}
