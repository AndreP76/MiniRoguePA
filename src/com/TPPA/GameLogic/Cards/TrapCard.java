package com.TPPA.GameLogic.Cards;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.IState;
import com.TPPA.GameLogic.Internals.Dice;
import com.TPPA.GameLogic.Internals.Player;
import com.TPPA.GameLogic.States.AwaitCardSelectionState;
import com.TPPA.Main;

/**
 * Created by andre on 4/19/17.
 */
public class TrapCard extends CardBase {
    public TrapCard(GameStateController GSC, String ID) {
        super(GSC, ID, "Trap Card");
    }

    @Override
    public IState Effect() {
        GameStateController GSC = this.GSC;
        Main.ErrorStream.println("TrapCard effect called!");

        if (GSC.getCurrentPlayer().skillCheck()) {
            GSC.MessageStack.push("Skill check successful - trap avoided\n");
            Main.ErrorStream.println("Skill check successful - trap avoided");
            return new AwaitCardSelectionState(GSC);
        }
        Dice d = new Dice();
        Player P = GSC.getCurrentPlayer();
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
                if (GSC.getCurrentZone() == GSC.getMaxZones()) {
                    Main.ErrorStream.println("Player cannot fall anymore.");
                    GSC.MessageStack.push("Pit - you've lost 2 HP when you fell down an hole");
                } else {
                    GSC.setRoomStages(null);
                    GSC.MessageStack.push("Pit - you've lost 2 HP and fell down to the next Level");
                    if (GSC.getCurrentZone() < GSC.getMaxZones()) {//player can still fall down
                        GSC.setCurrentZone(GSC.getCurrentZone() + 1);
                        int CurrentRoom = GSC.getTrueRoom();
                        switch (CurrentRoom) {
                            case 1: {
                                GSC.setCurrentZone(1);
                                GSC.setCurrentRoom(0);
                                GSC.setCurrentStageInRoom(0);
                                break;
                            }
                            case 2: {
                                GSC.setCurrentZone(1);
                                GSC.setCurrentRoom(1);
                                GSC.setCurrentStageInRoom(0);
                                break;
                            }
                            case 3: {
                                GSC.setCurrentZone(2);
                                GSC.setCurrentRoom(0);
                                GSC.setCurrentStageInRoom(0);
                                break;
                            }
                            case 4: {
                                GSC.setCurrentZone(2);
                                GSC.setCurrentRoom(1);
                                GSC.setCurrentStageInRoom(0);
                                break;
                            }
                            case 5: {
                                GSC.setCurrentZone(3);
                                GSC.setCurrentRoom(0);
                                GSC.setCurrentStageInRoom(0);
                                break;
                            }
                            case 6: {
                                GSC.setCurrentZone(3);
                                GSC.setCurrentRoom(1);
                                GSC.setCurrentStageInRoom(0);
                                break;
                            }
                            case 7: {
                                GSC.setCurrentZone(3);
                                GSC.setCurrentRoom(2);
                                GSC.setCurrentStageInRoom(0);
                                break;
                            }
                            case 8: {
                                GSC.setCurrentZone(4);
                                GSC.setCurrentRoom(0);
                                GSC.setCurrentStageInRoom(0);
                                break;
                            }
                            case 9: {
                                GSC.setCurrentZone(4);
                                GSC.setCurrentRoom(1);
                                GSC.setCurrentStageInRoom(0);
                                break;
                            }
                            case 10: {
                                GSC.setCurrentZone(4);
                                GSC.setCurrentRoom(2);
                                GSC.setCurrentStageInRoom(0);
                                break;
                            }
                        }
                        Main.ErrorStream.println("Player fell to Zone " + GSC.getCurrentZone() + ", Room : " + GSC.getCurrentRoom());
                    }
                }
            }
            default:
                break;
        }
        return new AwaitCardSelectionState(GSC);
    }
}
