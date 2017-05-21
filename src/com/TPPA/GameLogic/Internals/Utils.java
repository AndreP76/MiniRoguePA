package com.TPPA.GameLogic.Internals;

import com.TPPA.GameLogic.Cards.CardBase;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by andre on 5/11/17.
 */
public class Utils {
    public static String GenerateDateTimeStringNow(String Predicate) {
        Date now = new Date();
        return Predicate + now.getDay() + "_" + now.getHours() + "_" + now.getMinutes() + "_" + now.getSeconds();
    }

    public static String MakeVerticalString(String original) {
        String n = "";
        for (int i = 0; i < original.length(); ++i) {
            n += original.charAt(i) + "\n";
        }
        return n;
    }

    public static CardBase[] MakeLinear(CardBase[][] cb) {
        ArrayList<CardBase> C = new ArrayList<>();
        for (int i = 0; i < cb.length; i++) {
            for (int j = 0; j < cb[i].length; j++) {
                if (cb[i][j] != null)
                    C.add(cb[i][j]);
            }
        }
        CardBase[] x = new CardBase[C.size()];
        C.toArray(x);
        return x;
    }
}
