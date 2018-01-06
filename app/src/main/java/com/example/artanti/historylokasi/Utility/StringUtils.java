package com.example.artanti.historylokasi.Utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by root on 12/25/17.
 */

public class StringUtils {

    public static String datenow(){
        Date date = new Date(); // your date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.DAY_OF_MONTH);
    }

    public static String timenow(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    public static int convertToInt(String str) throws NumberFormatException {
        int s, e;
        for (s = 0; s < str.length(); s++)
            if (Character.isDigit(str.charAt(s)))
                break;
        for (e = str.length(); e > 0; e--)
            if (Character.isDigit(str.charAt(e - 1)))
                break;
        if (e > s) {
            try {
                return Integer.parseInt(str.substring(s, e));
            } catch (NumberFormatException ex) {
//                Log.ERROR("convertToInt", ex);
                throw new NumberFormatException();
            }
        } else {
            throw new NumberFormatException();
        }
    }


    public static String getShortMonthName(int month) {
        String m = "";
        switch (month) {
            case 0:
                m = "Jan";
                break;
            case 1:
                m = "Feb";
                break;
            case 2:
                m = "Mar";
                break;
            case 3:
                m = "Apr";
                break;
            case 4:
                m = "Mei";
                break;
            case 5:
                m = "Jun";
                break;
            case 6:
                m = "Jul";
                break;
            case 7:
                m = "Agu";
                break;
            case 8:
                m = "Sep";
                break;
            case 9:
                m = "Okt";
                break;
            case 10:
                m = "Nov";
                break;
            case 11:
                m = "Des";
                break;
        }
        return m;
    }

    public static String getCompleteMonthName(int month) {
        String m = "";
        switch (month) {
            case 0:
                m = "Januari";
                break;
            case 1:
                m = "Februari";
                break;
            case 2:
                m = "Maret";
                break;
            case 3:
                m = "April";
                break;
            case 4:
                m = "Mei";
                break;
            case 5:
                m = "Juni";
                break;
            case 6:
                m = "Juli";
                break;
            case 7:
                m = "Agustus";
                break;
            case 8:
                m = "September";
                break;
            case 9:
                m = "Oktober";
                break;
            case 10:
                m = "November";
                break;
            case 11:
                m = "Desember";
                break;
        }
        return m;
    }

}

