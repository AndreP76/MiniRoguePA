package com.TPPA.GameLogic;

import com.TPPA.GameLogic.Spells.SpellBase;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Lídia on 05/04/2017.
 */
public class Player {
    private int Food = 0;
    private int XP = 0;
    private int Attack = 0;
    private int Gold = 0;
    private int Armor = 0;
    private int HP = 0;
    private int Rank = 1;

    private ArrayList<SpellBase> SpellsInventory;

    private ArrayList<Dice> UnlockedDice;

    Player(int StartAttack, int StartArmor, int StartGold, int StartFood, int StartXP, int StartHP) {
        Food = StartFood;
        XP = StartXP;
        Attack = StartAttack;
        Gold = StartGold;
        Armor = StartArmor;
        SpellsInventory = new ArrayList<SpellBase>();
        HP = StartHP;
        this.Rank = 1;
        UnlockedDice = new ArrayList<>();
    }

    //getters
    public int getAttack() {
        return Attack;
    }

    //setters
    public void setAttack(int attack) {
        Attack = attack;
    }

    public int getRank() {
        return Rank;
    }

    public void setRank(int rank) {
        Rank = rank;
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

    public int getArmor() {
        return Armor;
    }

    public void setArmor(int armor) {
        Armor = armor;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public ArrayList<SpellBase> getSpellsInventory() {
        return SpellsInventory;
    }

    public ArrayList<Dice> getUnlockedDice() {
        return UnlockedDice;
    }

    //incrementers
    public void incAttack(int ammount) {
        this.Attack = this.Attack + ammount;
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

    public void incArmor(int ammount) {
        this.Armor += ammount;
    }

    public void incHP(int ammount) {
        this.HP += ammount;
    }

    public void incRank(int ammount) {
        this.Rank += ammount;
    }

    public void incRank() {
        this.Rank++;
    }


    // Só é chamado quando XP aumenta
    public void unlockNewDie() {
        if (this.XP % 6 == 0) {
            this.UnlockedDice.add(new Dice());
        }
    }

    // Só é chamado se Player descer o Rank quando apanha uma TrapCard
    public void removeUnlockedDice() {
        try {
            this.UnlockedDice.remove(this.UnlockedDice.size() - 1);
        } catch (IndexOutOfBoundsException e) {
            Main.ErrorStream.println("Failed to remove Dice from UnlockedDice " + e);
        }
    }

    // Métodos a ser usados na RollPhase
    public void rollDice() {
        for (Dice currentDie : this.UnlockedDice) {
            int roll = currentDie.Roll();
            currentDie.incRollSum(roll);
        }
    }

    public void resetDice() {
        for (Dice currentDie : this.UnlockedDice) {
            currentDie.resetRollSum();
        }
    }


    public Boolean skillCheck() {
        int roll = new Dice().Roll();
        if (roll <= this.Rank)
            return true;

        return false;
    }
}
