package com.TPPA.GameLogic.Internals;

import com.TPPA.GameLogic.Spells.SpellBase;
import com.TPPA.Main;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Lídia on 05/04/2017.
 */
public class Player implements Serializable {
    private int MaxFood = 10;
    private int Food = 0;
    private int MinFood = 0;
    private int MinXP = 0;
    private int XP = 0;
    private int MaxXP = 36;
    private int Rank1XP = 0;
    private int Rank2XP = 6;
    private int Rank3XP = 18;
    private int Rank4XP = 36;

    private int MaxAttack = 10; //
    private int Attack = 0;     // I think these are useless
    private int MinAttack = 0;  //

    private int MaxGold = 20;
    private int Gold = 0;
    private int MinGold = 0;

    private int MaxArmor = 7;
    private int Armor = 0;
    private int MinArmor = 0;

    private int MaxHP = 20;
    private int HP = 0;
    private int MinHP = 0;

    private int Rank = 1;

    private ArrayList<SpellBase> SpellsInventory;

    private ArrayList<Dice> UnlockedDice;

    private boolean hasDefeatedMonster;
    private boolean hasUsedFeat;

    public Player(int StartAttack, int StartArmor, int StartGold, int StartFood, int StartXP, int StartHP) {
        Food = StartFood;
        XP = StartXP;
        Attack = StartAttack;
        Gold = StartGold;
        Armor = StartArmor;
        SpellsInventory = new ArrayList<>();
        HP = StartHP;
        this.Rank = 1;
        UnlockedDice = new ArrayList<>();
        unlockNewDie(); // começa já com um dado desbloqueado

        hasDefeatedMonster = false;

        hasUsedFeat = false;
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
        if (food > 6)
            food = 6;
        if (food < 0)
            food = 0;
        Food = food;
    }

    public int getGold() {
        return Gold;
    }

    public void setGold(int gold) {
        if (gold > 20)
            gold = 20;
        if (gold < 0)
            gold = 0;
        Gold = gold;
    }

    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        if (XP < MinXP)
            this.XP = MinXP;
        else if (XP > MaxXP) {
            if (this.XP == MaxXP) {
                this.incHP(1);
            } else {
                this.XP = MaxXP;
            }
        } else {
            this.XP = XP;
            if (this.XP >= Rank1XP) {
                this.setRank(1);
            }
            if (this.XP >= Rank2XP) {
                this.setRank(2);
            }
            if (this.XP >= Rank3XP) {
                this.setRank(3);
            }
            if (this.XP >= Rank4XP) {
                this.setRank(4);
            }
        }
    }

    public int getArmor() {
        return Armor;
    }

    public void setArmor(int armor) {
        if (armor < 0)
            armor = 0;
        if (armor > 5)
            armor = 5;
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

    public boolean incFood(int ammount) {
        if (this.Food == 6 && ammount > 0)
            return false;

        if (this.Food + ammount < 0)
            return false;

        if (this.Food + ammount > 6)
            this.Food = 6;
        else
            this.Food = this.Food + ammount;
        return true;
    }

    public boolean incGold(int ammount) {

        if (this.Gold + ammount < 0)
            return false;

        if (this.Gold + ammount > 20)
            this.Gold = 20;
        else
            this.Gold = this.Gold + ammount;
        return true;
    }
    public void incXP(int ammount) {
        if (this.XP + ammount > MaxXP) {
            this.setXP(MaxXP);
        } else {
            this.setXP(this.XP + ammount);
        }
    }

    public boolean incArmor(int ammount) {
        if (this.Armor == 5 && ammount > 0)
            return false;
        if (this.Armor + ammount < 0)
            return false;

        if (this.Armor + ammount > 5)
            this.Armor = 5;
        else
            this.Armor += ammount;
        return true;
    }

    public boolean incHP(int ammount) {
        if (this.HP == 20 && ammount > 0)
            return false;

        if (this.HP + ammount > 20)
            this.HP = 20;
        else
            this.HP += ammount;

        return true;
    }

    public void incRank(int ammount) {
        if (ammount == 0)
            return;

        if (this.Rank + ammount < 1) {
            while (this.Rank > 1) {
                this.Rank--;
                removeUnlockedDice();
            }
            return;
        }
        if (this.Rank + ammount > 4) {
            while (this.Rank < 4) {
                this.Rank++;
                unlockNewDie();
            }
            return;
        }

        if (ammount > 0) {
            for (int i = 0; i < ammount; i++) {
                this.Rank++;
                unlockNewDie();
            }
            return;
        }

        if (ammount < 0) {
            for (int i = 0; i < ammount; i++) {
                this.Rank--;
                removeUnlockedDice();
            }
            return;
        }

    }

    public void incRank() {
        if (this.Rank + 1 <= 4) {
            this.Rank++;
            unlockNewDie();
        }
    }


    // Só é chamado quando Rank aumenta
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

    public boolean getHasDefeatedMonster() {
        return this.hasDefeatedMonster;
    }

    public void setHasDefeatedMonster(boolean hasDefeatedMonster) {
        this.hasDefeatedMonster = hasDefeatedMonster;
    }

    public boolean getHasUsedFeat() {
        return hasUsedFeat;
    }

    public void setHasUsedFeat(boolean hasUsedFeat) {
        this.hasUsedFeat = hasUsedFeat;
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

    public boolean useFeatConsumingHP(int index) throws IndexOutOfBoundsException {
        Dice currentDie;
        currentDie = UnlockedDice.get(index);
        if (currentDie == null) {
            throw new IndexOutOfBoundsException();
        }

        if (this.HP - 2 <= 0)
            return false;
        else
            this.incHP(-2);

        int roll = currentDie.Roll();
        if (roll != 1)
            currentDie.incRollSum(roll);
        else
            currentDie.resetRollSum();
        return true;
    }

    public boolean useFeatConsumingXP(int index) throws IndexOutOfBoundsException {
        Dice currentDie;
        currentDie = UnlockedDice.get(index);
        if (currentDie == null)
            throw new IndexOutOfBoundsException();

        if (this.XP - 1 < 0)
            return false;
        else
            this.incXP(-1);

        int roll = currentDie.Roll();
        if (roll != 1)
            currentDie.incRollSum(roll);
        else
            currentDie.resetRollSum();
        return true;
    }


    public Boolean skillCheck() {
        int roll = new Dice().Roll();
        return roll <= this.Rank;

    }

    public boolean hasRolledDice() {
        for (Dice currentDie : UnlockedDice)
            if (currentDie.getLastRoll() != 0)
                return true;
        return false;
    }


    public String getUnlockedDiceDescription() {
        String s = "";

        for (int i = 0; i < UnlockedDice.size(); i++) {
            s += (i + 1) + ") " + UnlockedDice.get(i).toString() + "\n";
        }
        return s;
    }

    public String getCriticalDiceDescription() {
        String s = "";

        for (int i = 0; i < UnlockedDice.size(); i++) {
            if (UnlockedDice.get(i).getLastRoll() == 6)
                s += (i + 1) + ") " + UnlockedDice.get(i).toString() + "\n";
        }
        return s;
    }

    public int getUnlockedDiceSum() {
        int sum = 0;
        for (Dice currentDie : UnlockedDice)
            sum += currentDie.getRollSum();
        return sum;
    }

    public String getSpellsDescription() {
        String s = "";

        if (!SpellsInventory.isEmpty()) {
            for (int i = 0; i < SpellsInventory.size(); i++) {
                s += (i + 1) + ") " + SpellsInventory.get(i).getSpellID() + "\n";
            }
        } else
            s += "Player has no spells to use";

        return s;
    }

    @Override
    public String toString() {
        String s = "";
        s += "Player stats:\n";
        s += "Rank: " + Rank + "\tXP: " + XP + "\tGold: " + Gold + "\tFood: " + Food + "\n";
        s += "Armor: " + Armor + "\tHP: " + HP + "\n";
        return s;
    }

    public int getTotalDiceSum() {
        int total = 0;
        for (Dice currDie : UnlockedDice) {
            total += currDie.getRollSum();
        }
        return total;
    }

    public int getMaxHP() {
        return MaxHP;
    }
}
