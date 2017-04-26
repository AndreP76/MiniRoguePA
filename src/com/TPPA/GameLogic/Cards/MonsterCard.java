package com.TPPA.GameLogic.Cards;

import com.TPPA.GameLogic.*;

/**
 * Created by andre on 4/19/17.
 * I have no idea what I''m doing
 */
public class MonsterCard extends CardBase {
    protected MonsterCard(String ID, String Name) {
        super(ID, Name);
    }

    public MonsterCard(String ID) {
        super(ID, "Monster Card");
    }

    public IState Effect(int HP, int XPR, int GR, int Strength, Boolean Boss) {
        String Name = "";
        switch (GameStateController.getCurrentController().getCurrentZone()) {
            case 1: {
                Name = "Undead Soldier";
                break;
            }
            case 2: {
                Name = "Skeleton";
                break;
            }
            case 3: {
                Name = "Undead Knight";
                break;
            }
            case 4: {
                Name = "Serpent Knight";
                break;
            }
            case 5: {
                Name = "Og's Sanctum Guard";
                break;
            }
        }
        GameStateController.getCurrentController().setCurrentMonster(new Monster(HP, XPR, GR, Strength, Boss, Name));
        return new RollPhase();
    }

    @Override
    public IState Effect() {//Start a battle with a monster
        Main.ErrorStream.println("MonsterCard effect called!");
        GameStateController GSC = GameStateController.getCurrentController();

        int HP = (new Dice()).Roll() + GSC.getTrueRoom();
        int XPR = -1;
        int GR = 0;
        int S = 2 * GSC.getCurrentZone();

        switch (GSC.getCurrentZone()) {
            case 1: {
                XPR = 1;
                break;
            }
            case 2: {
                XPR = 1;
                break;
            }
            case 3: {
                XPR = 2;
                break;
            }
            case 4: {
                XPR = 2;
                break;
            }
            case 5: {
                XPR = 3;
                break;
            }
        }

        return this.Effect(HP, XPR, GR, S, false);
    }
}
