package com.TPPA.GameLogic.Cards;

import com.TPPA.GameLogic.*;

/**
 * Created by andre on 4/19/17.
 */
public class MonsterCard extends CardBase {
    public MonsterCard(String ID) {
        super(ID);
    }

    @Override
    public IState Effect() {//Start a battle with a monster
        Main.ErrorStream.println("MonsterCard effect called!");
        GameStateController GSC = GameStateController.getCurrentController();

        int HP = (new Dice()).Roll() + GSC.getTrueRoom();
        int XPR = -1;
        int GR = 0;
        int S = 2 * GSC.getCurrentZone();
        String name = "";

        switch (GSC.getCurrentZone()) {
            case 1: {
                XPR = 1;
                name = "Undead Soldier";
                break;
            }
            case 2: {
                XPR = 1;
                name = "Skeleton";
                break;
            }
            case 3: {
                XPR = 2;
                name = "Undead Knight";
                break;
            }
            case 4: {
                XPR = 2;
                name = "Serpent Knight";
                break;
            }
            case 5: {
                XPR = 3;
                name = "Og's Sanctum Guard";
                break;
            }
        }
        GSC.setCurrentMonster(new Monster(HP, XPR, GR, S, true, name));
        return new RollPhase();
    }
}
