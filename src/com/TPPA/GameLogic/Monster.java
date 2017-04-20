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

    public IState onAttack() {
        GameStateController.getCurrentController().getCurrentPlayer().incHP(this.Strength);
        if (GameStateController.getCurrentController().getCurrentPlayer().getHP() <= 0) {
            return new GameOverState();
        } else {
            return new RollPhase();
        }
    }
}
