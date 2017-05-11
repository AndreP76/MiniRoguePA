package com.TPPA.GameLogic;

import java.util.Date;

/**
 * Created by andre on 5/11/17.
 */
public class Utils {
    public static String GenerateDateTimeStringNow(String Predicate) {
        Date now = new Date();
        return Predicate + now.getDay() + "_" + now.getHours() + "_" + now.getMinutes() + "_" + now.getSeconds();
    }
}
