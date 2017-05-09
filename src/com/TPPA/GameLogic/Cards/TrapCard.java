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
        GameStateController GSC = GameStateController.getCurrentController();
        Main.ErrorStream.println("TrapCard effect called!");

        if (GSC.getCurrentPlayer().skillCheck()) {
            GSC.MessageStack.push("Skill check successful - trap avoided\n");
            Main.ErrorStream.println("Skill check successful - trap avoided");
            return new AwaitCardSelectionState();
        }
        Dice d = new Dice();
        Player P = GameStateController.getCurrentController().getCurrentPlayer();
        switch (d.Roll()) {
            case 1: {
                if (P.getFood() >= 1) {
                    P.incFood(-1);
                    GSC.MessageStack.push("Mold Miasma - you've lost a piece of food\n");
                } else {
                    P.incHP(-2);
                    GSC.MessageStack.push("Mold Miasma - you've lost 2 HP\n");
                }
                Main.ErrorStream.println("Mold Miasma");
                break;
            }

            case 2: {
                if (P.getGold() >= 1) {
                    P.incGold(-1);
                    GSC.MessageStack.push("Tripwire - you've lost 1 Gold\n");
                } else {
                    P.incHP(-2);
                    GSC.MessageStack.push("Tripwire - you've lost 2 HP\n");
                }
                Main.ErrorStream.println("Tripwire");
                break;
            }
            case 3: {
                if (P.getArmor() >= 1) {
                    P.incArmor(-1);
                    GSC.MessageStack.push("Acid Mist - you've lost a piece of armor\n");
                } else {
                    P.incHP(-2);
                    GSC.MessageStack.push("Acid Mist - you've lost 2 HP\n");
                }
                Main.ErrorStream.println("Acid Mist");
                break;
            }
            case 4: {
                P.incHP(-1);
                GSC.MessageStack.push("Spring Blades - you've lost 1 HP\n");
                Main.ErrorStream.println("Spring blades");
                break;
            }
            case 5: {
                if (P.getXP() >= 1) {
                    P.incXP(-1);
                    GSC.MessageStack.push("Moving Walls - you've lost 1 XP\n");
                } else {
                    P.incHP(-2);
                    GSC.MessageStack.push("Moving Walls - you've lost 2 HP\n");
                }
                Main.ErrorStream.println("Moving Walls");
                break;
            }
            case 6: {
                P.incHP(-2);
                Main.ErrorStream.println("Player lost 2 HP");
                GSC.MessageStack.push("Pit - you've lost 2 HP and fell down to the next Level");
                //GameStateController GSC = GameStateController.getCurrentController();
                if (GSC.getCurrentRoom() == GSC.getRoomsInZone() && GSC.getCurrentZone() == GSC.getMaxZones()) {
                    Main.ErrorStream.println("Player cannot fall anymore.");
                } else {
                    Main.ErrorStream.println("Fall not implemented");
                    //TODO : HERE
                }
            }
            default:
                break;
        }
        return new AwaitCardSelectionState();
    }
}
