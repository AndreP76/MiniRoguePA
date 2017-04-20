package com.TPPA.GameLogic.Cards;

import com.TPPA.GameLogic.*;

/**
 * Created by andre on 4/19/17.
 */
public class BossMonsterCard extends MonsterCard {
    public BossMonsterCard(String ID) {
        super(ID);
    }

    @Override
    public IState Effect() {
        Main.ErrorStream.println("BossMonsterCard effect called!");
        GameStateController GSC = GameStateController.getCurrentController();

        int HP = -1;
        int XPR = -1;
        int GR = -1;
        int S = -1;
        String name = "";

        switch (GSC.getCurrentZone()) {
            case 1: {
                HP = 10;
                XPR = 2;
                GR = 2;
                S = 3;
                name = "Undead Giant";
            }
        }

        Monster M = new Monster(HP, XPR, GR, S, true, name);
        return new RollPhase();
    }
}
