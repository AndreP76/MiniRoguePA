package com.TPPA.GameLogic;

import com.TPPA.GameLogic.Spells.SpellBase;

import java.util.ArrayList;

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
        SpellsInventory = new ArrayList<>();
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
        if (XP < 0)
            XP = 0;
        else if (XP > 36)
            XP = 36;
        this.XP = XP;

        if (this.XP < 6)
            setRank(1);
        if (this.XP >= 6 && this.XP < 12)
            setRank(2);
        if (this.XP >= 12 && this.XP < 36)
            setRank(3);
        if (this.XP >= 36)
            setRank(4);

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
        if (this.XP == 36 && ammount > 0) { // já chegou ao Rank 4
            this.incHP(1);
            return;
        }

        this.XP += ammount;
        if (this.XP < 0)
            this.XP = 0;
        if (this.XP > 36)
            this.XP = 36;

        if (this.XP < 6)
            setRank(1);
        if (this.XP >= 6 && this.XP < 12)
            setRank(2);
        if (this.XP >= 12 && this.XP < 36)
            setRank(3);
        if (this.XP >= 36)
            setRank(4);
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
        if (roll <= this.Rank)
            return true;

        return false;
    }
}
