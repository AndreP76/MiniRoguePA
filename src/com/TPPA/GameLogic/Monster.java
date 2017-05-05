package com.TPPA.GameLogic;

import com.TPPA.GameLogic.Cards.TreasureCard;

/**
 * Created by andre on 4/20/17.
 */
public class Monster {
    private int HPMax;
    private int HPCurr;

    private int XPReward;
    private int GoldReward;

    private int Strength;

    private Boolean isBoss = false;

    private String Name;

    private Boolean CanAttack = true;

    public Monster(int HPMax, int XPReward, int GoldReward, int MonsterAttack, Boolean isBoss, String Name) {
        this.HPMax = this.HPCurr = HPMax;
        this.XPReward = XPReward;
        this.GoldReward = GoldReward;
        this.isBoss = isBoss;
        this.Name = Name;
        this.Strength = MonsterAttack;
    }

    public IState onDefeat() {
        GameStateController.getCurrentController().getCurrentPlayer().incXP(XPReward);
        GameStateController.getCurrentController().getCurrentPlayer().incGold(GoldReward);
        if (isBoss) {
            return (new TreasureCard(Deck.TreasureCardID)).Effect();
        } else {
            return new AwaitCardSelectionState();
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

    public IState onAttack() {
        GameStateController.getCurrentController().getCurrentPlayer().incHP(this.Strength - GameStateController.getCurrentController().getCurrentPlayer().getArmor());
        if (GameStateController.getCurrentController().getCurrentPlayer().getHP() <= 0) {
            return new GameOverState();
        } else {
            GameStateController.getCurrentController().getCurrentPlayer().resetDice();
            return new RollPhase();
        }
    }
}
