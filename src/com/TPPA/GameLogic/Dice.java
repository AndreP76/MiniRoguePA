package com.TPPA.GameLogic;

import java.util.Objects;
import java.util.Random;

/**
 * Created by andre on 4/20/17.
 */
public class Dice {
    private Random SR;
    private int RollSum;
    private Boolean isLocked = false;

    public Dice() {
        isLocked = false;
        SR = new Random();
        SR.setSeed(System.nanoTime());
    }

    public int Roll() {
        int R = SR.nextInt(6) + 1;
        Main.ErrorStream.println("Dice rolled " + R);
        return R;
    }

    public int getRollSum() {
        return RollSum;
    }

    public void incRollSum(int v) {
        RollSum = RollSum + v;
    }

    public void resetRollSum() {
        RollSum = 0;
    }

    public void LockDice() {
        isLocked = true;
    }

    public void unlockDice() {
        isLocked = false;
    }

    public Boolean isLocked() {
        return isLocked;
    }
}
