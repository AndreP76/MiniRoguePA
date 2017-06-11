package com.TPPA.GameLogic.Cards;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.IState;
import com.TPPA.GameLogic.Internals.Dice;
import com.TPPA.GameLogic.Internals.Monster;
import com.TPPA.GameLogic.States.RollPhase;
import com.TPPA.Main;

/**
 * Created by andre on 4/19/17.
 */
public class MonsterCard extends CardBase {
    protected MonsterCard(GameStateController GS, String ID, String Name) {
        super(GS, ID, Name);
    }

    public MonsterCard(GameStateController GS, String ID) {
        super(GS, ID, "Monster Card");
    }

    public IState Effect(int HP, int XPR, int GR, int Strength, Boolean Boss) {
        String Name = "";
        switch (GSC.getCurrentZone()) {
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
        GSC.setCurrentMonster(new Monster(GSC, HP, XPR, GR, Strength, Boss, Name));
        return new RollPhase(GSC);
    }

    @Override
    public IState Effect() {//Start a battle with a monster
        Main.ErrorStream.println("MonsterCard effect called!");
        GameStateController GSC = this.GSC;

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
