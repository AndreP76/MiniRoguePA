package com.TPPA.GameLogic.Internals;

import com.TPPA.GameLogic.Cards.TreasureCard;
import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.IState;
import com.TPPA.GameLogic.States.AwaitCardSelectionState;
import com.TPPA.GameLogic.States.GameOverState;
import com.TPPA.GameLogic.States.RollPhase;

import java.io.Serializable;

/**
 * Created by andre on 4/20/17.
 */
public class Monster implements Serializable {
    private int HPMax;
    private int HPCurr;

    private int XPReward;
    private int GoldReward;

    private int Strength;

    private Boolean isBoss = false;

    private String Name;

    private Boolean CanAttack = true;

    private boolean poisoned = false;
    private GameStateController GSC;

    public Monster(GameStateController GSC, int HPMax, int XPReward, int GoldReward, int MonsterAttack, Boolean isBoss, String Name) {
        this.GSC = GSC;
        this.HPMax = this.HPCurr = HPMax;
        this.XPReward = XPReward;
        this.GoldReward = GoldReward;
        this.isBoss = isBoss;
        this.Name = Name;
        this.Strength = MonsterAttack;
    }

    public IState onDefeat() {
        GSC.getCurrentPlayer().incXP(XPReward);
        GSC.getCurrentPlayer().incGold(GoldReward);
        if (isBoss) {
            return (new TreasureCard(GSC, Deck.TreasureCardID)).Effect();
        } else {
            return new AwaitCardSelectionState(GSC);
        }
    }

    public int getGoldReward() {
        return GoldReward;
    }

    public void setGoldReward(int goldReward) {
        GoldReward = goldReward;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Boolean getBoss() {
        return isBoss;
    }

    public void setBoss(Boolean boss) {
        isBoss = boss;
    }

    public Boolean getCanAttack() {
        return CanAttack;
    }

    public void setCanAttack(Boolean canAttack) {
        CanAttack = canAttack;
    }

    public int getHPCurr() {
        return HPCurr;
    }

    public void setHPCurr(int HPCurr) {
        this.HPCurr = HPCurr;
    }

    public void incHPCurr(int HPCurr) {
        if (this.HPCurr + HPCurr > HPMax) {
            this.HPCurr = HPMax;
            return;
        }
        this.HPCurr += HPCurr;
    }

    public int getHPMax() {
        return HPMax;
    }

    public void setHPMax(int HPMax) {
        this.HPMax = HPMax;
    }

    public int getStrength() {
        return Strength;
    }

    public void setStrength(int strength) {
        Strength = strength;
    }

    public int getXPReward() {
        return XPReward;
    }

    public void setXPReward(int XPReward) {
        this.XPReward = XPReward;
    }

    public boolean getPoisoned() {
        return poisoned;
    }

    public void setPoisoned(boolean poisoned) {
        this.poisoned = poisoned;
    }

    public IState onAttack() {
        GSC.getCurrentPlayer().incHP(this.Strength - GSC.getCurrentPlayer().getArmor());
        if (GSC.getCurrentPlayer().getHP() <= 0) {
            return new GameOverState(GSC);
        } else {
            //GameStateController.getCurrentController().getCurrentPlayer().resetDice();
            return new RollPhase(GSC);
        }
    }

    @Override
    public String toString() {
        String s = "";

        s += "HP: " + HPCurr + ",  Attack: " + Strength + "\n";
        s += "XP reward: " + XPReward;
        if (this.isBoss)
            s += ",  Gold reward: " + GoldReward;
        s += "\n";
        return s;
    }
}
