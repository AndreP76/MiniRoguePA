package com.TPPA.GameLogic.Cards;

import com.TPPA.GameLogic.*;

/**
 * Created by andre on 4/19/17.
 */
public class TrapCard extends CardBase {
    public TrapCard(String ID) {
        super(ID, "Trap Card");
    }

    @Override
    public IState Effect() {
        Main.ErrorStream.println("TrapCard effect called!");
        Dice d = new Dice();
        Player P = GameStateController.getCurrentController().getCurrentPlayer();
        switch (d.Roll()) {
            case 1: {
                if (P.getFood() >= 1) {
                    P.incFood(-1);
                } else {
                    P.incHP(-2);
                }
                Main.ErrorStream.println("Mold Miasma");
                break;
            }

            case 2: {
                if (P.getGold() >= 1) {
                    P.incGold(-1);
                } else {
                    P.incHP(-2);
                }
                Main.ErrorStream.println("Tripwire");
                break;
            }
            case 3: {
                if (P.getArmor() >= 1) {
                    P.incArmor(-1);
                } else {
                    P.incHP(-2);
                }
                Main.ErrorStream.println("Acid Mist");
                break;
            }
            case 4: {
                P.incHP(-1);
                Main.ErrorStream.println("Spring blades");
                break;
            }
            case 5: {
                if (P.getXP() >= 1) {
                    P.incXP(-1);
                } else {
                    P.incHP(-2);
                }
                Main.ErrorStream.println("Moving Walls");
                break;
            }
            case 6: {
                P.incHP(-2);
                Main.ErrorStream.println("Player lost 2 HP");
                GameStateController GSC = GameStateController.getCurrentController();
                if (GSC.getCurrentRoom() == GSC.getRoomsInZone() && GSC.getCurrentZone() == GSC.getMaxZones()) {
                    Main.ErrorStream.println("Player cannot fall anymore.");
                } else {
                    Main.ErrorStream.println("Fall not implemented");
                    //TODO : Some weird math happens here
                    //TODO : Fall to the floor beneath
                }
            }
            default:
                break;
        }
        return new AwaitCardSelectionState();
    }
}
