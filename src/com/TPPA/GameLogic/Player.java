package com.TPPA.GameLogic;

/**
 * Created by Lídia on 05/04/2017.
 */
public class Player {
    private int Food = 0;
    private int XP = 0;
    private int Attack = 0;
    private int Defense = 0;
    private int Gold = 0;

    Player(int StartAttack, int StartDefense, int StartGold, int StartFood, int StartXP) {
        Food = StartFood;
        XP = StartXP;
        Attack = StartAttack;
        Defense = StartDefense;
        Gold = StartGold;
    }

    //getters
    public int getAttack() {
        return Attack;
    }

    //setters
    public void setAttack(int attack) {
        Attack = attack;
    }

    public int getDefense() {
        return Defense;
    }

    public void setDefense(int defense) {
        Defense = defense;
    }

    public int getFood() {
        return Food;
    }

    public void setFood(int food) {
        Food = food;
    }

    public int getGold() {
        return Gold;
    }

    public void setGold(int gold) {
        Gold = gold;
    }

    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }

    //incrementers
    public void incAttack(int ammount) {
        this.Attack = this.Attack + ammount;
    }

    public void incDefense(int ammount) {
        this.Defense = this.Defense + ammount;
    }

    public void incFood(int ammount) {
        this.Food = this.Food + ammount;
    }

    public void incGold(int ammount) {
        this.Gold = this.Gold + ammount;
    }

    public void incXP(int ammount) {
        this.XP = this.XP + ammount;
    }
}
