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
        unlockNewDie(); // começa já com um dado desbloqueado
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
        int i;
        if (rank <= 0 || rank == this.Rank)
            return;

        if (rank < Rank) {
            for (i = rank; i < Rank; i++) {
                removeUnlockedDice();
            }
        } else {
            for (i = this.Rank; i < rank; i++) {
                unlockNewDie();
            }
        }

        Rank = rank;
    }

    public int getFood() {
        return Food;
    }

    public void setFood(int food) {
        if (food < 0)
            food = 0;
        Food = food;
    }

    public int getGold() {
        return Gold;
    }

    public void setGold(int gold) {
        if (gold < 0)
            gold = 0;
        Gold = gold;
    }

    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        if (XP < 0)
            XP = 0;
        this.XP = XP;
    }

    public int getArmor() {
        return Armor;
    }

    public void setArmor(int armor) {
        if (armor < 0)
            armor = 0;
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
        if (this.Food < 0)
            this.Food = 0;
    }
    public void incGold(int ammount) {
        this.Gold = this.Gold + ammount;
        if (this.Gold < 0)
            this.Gold = 0;
    }
    public void incXP(int ammount) {
        this.XP = this.XP + ammount;
        if (this.XP < 0)
            this.XP = 0;
    }

    public void incArmor(int ammount) {
        this.Armor += ammount;
        if (this.Armor < 0)
            this.Armor = 0;
    }

    public void incHP(int ammount) {
        this.HP += ammount;
    }

    public void incRank(int ammount) {
        this.Rank += ammount;
        if (ammount < 0)
            removeUnlockedDice();
        if (ammount > 0)
            unlockNewDie();
    }

    public void incRank() {
        this.Rank++;
        unlockNewDie();
    }


    // Só é chamado quando XP aumenta
    public void unlockNewDie() {
        this.UnlockedDice.add(new Dice());
    }

    // Só é chamado se Player descer o Rank quando apanha uma TrapCard
    public void removeUnlockedDice() {
        try {
            this.UnlockedDice.remove(this.UnlockedDice.size() - 1);
        } catch (IndexOutOfBoundsException e) {
            Main.ErrorStream.println("Failed to remove Die from UnlockedDice " + e);
        }
    }

    // Métodos a ser usados na RollPhase
    public void rollDice() {
        for (Dice currentDie : this.UnlockedDice) {
            int roll = currentDie.Roll();
            if (roll != 1)
                currentDie.incRollSum(roll);
            else
                currentDie.resetRollSum();
        }
    }

    public void reRollDice(int index) throws IndexOutOfBoundsException {
        Dice currentDie;
        currentDie = UnlockedDice.get(index);
        if (currentDie == null)
            throw new IndexOutOfBoundsException();

        if (currentDie.getLastRoll() != 6)
            return;

        int roll = UnlockedDice.get(index).Roll();
        if (roll != 1)
            currentDie.incRollSum(roll);
        else
            currentDie.resetRollSum();
    }

    public void resetDice() {
        for (Dice currentDie : this.UnlockedDice) {
            currentDie.resetRollSum();
            currentDie.resetLastRoll();
        }
    }

    public void useFeat(int index, int mode) throws IndexOutOfBoundsException {
        Dice currentDie;
        currentDie = UnlockedDice.get(index);
        if (currentDie == null)
            throw new IndexOutOfBoundsException();

        if (mode < 1 || mode > 2) {
            Main.ErrorStream.println("Unable to use Feat: invalid mode!");
            return;
        }

        if (mode == 1) { //mode = 1 -> usa Feat e perde 2 HP
            if (this.HP - 2 <= 0)
                return;
            else
                this.incHP(-2);
        }
        if (mode == 2) { //mode = 2 -> usa Feat e perde 1 XP
            if (this.XP - 1 < 0)
                return;
            else
                this.incXP(-1);
        }

        int roll = currentDie.Roll();
        if (roll != 1)
            currentDie.incRollSum(roll);
        if (roll == 1)
            currentDie.resetRollSum();

    }


    public Boolean skillCheck() {
        int roll = new Dice().Roll();
        if (roll <= this.Rank)
            return true;

        return false;
    }
}
