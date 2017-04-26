package com.TPPA.GameLogic.Cards;

import com.TPPA.GameLogic.*;

/**
 * Created by andre on 4/19/17.
 */
public class BossMonsterCard extends MonsterCard {
    public BossMonsterCard(String ID) {
        super(ID, "Boss Monster");
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
                break;
            }
            case 2: {
                HP = 15;
                XPR = 3;
                GR = 2;
                S = 5;
                name = "Skeleton Lord";
                break;
            }
            case 3: {
                HP = 20;
                XPR = 4;
                GR = 3;
                S = 7;
                name = "Undead Lord";
                break;
            }
            case 4: {
                HP = 25;
                XPR = 5;
                GR = 3;
                S = 9;
                name = "Serpent Demon";
                break;
            }
            case 5: {
                HP = 30;
                XPR = 0;
                GR = 0;
                S = 12;
                name = "Og's Remains";
                break;
            }
        }
        GSC.setCurrentMonster(new Monster(HP, XPR, GR, S, true, name));
        return new RollPhase();
    }
}
