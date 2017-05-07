package com.TPPA.GameLogic;

import com.TPPA.GameLogic.Cards.TreasureCard;

/**
 * Created by andre on 4/5/17.
 * Change the course of battle through sheer despair
 */
public class FeatPhase extends GameState {
    @Override
    public Action[] GetActions() {
        Action[] Act = new Action[4];
        Act[0] = new Action(InternalCommandsDictionary.UseFeatLosingHP, "Usar Feat sacrificando HP (- 2HP)");
        Act[1] = new Action(InternalCommandsDictionary.UseFeatLosingXP, "Usar Feat sacrificando XP (- 1XP)");
        Act[2] = new Action(InternalCommandsDictionary.EndFeatPhase, "Passar à fase seguinte");
        Act[3] = new Action(InternalCommandsDictionary.ReRollDice, "Rodar novamente um dado com dano crítico");
        return Act;
    }

    @Override
    public IState Action(String ActionString) {
        //TODO:  End this phase
        String[] SSplit = ActionString.split(" ");

        GameStateController GSC = GameStateController.getCurrentController();
        if (SSplit[0].equals(InternalCommandsDictionary.EndFeatPhase)) {
            GSC.getCurrentPlayer().setHasUsedFeat(false);
            //calcular dano infligido ao monstro
            return AttackMonster();
        } else if (SSplit[0].equals(InternalCommandsDictionary.UseFeatLosingHP)) {
//            if (GSC.getCurrentPlayer().useFeatConsumingHP(Integer.parseInt(SSplit[SSplit.length - 1]) - 1)) {
//                GSC.MessageStack.push("Re-rolled dice number " + SSplit[SSplit.length - 1] + ", got " + GSC.getCurrentPlayer().getUnlockedDice().get(Integer.parseInt(SSplit[SSplit.length - 1]) - 1).getLastRoll());
//            } else {
//                GSC.MessageStack.push("Re-rolling would kill you...");
//            }
            if (SSplit.length >= 2) {
                try {
                    int index = Integer.parseInt(SSplit[1]);
                    try {
                        if (GSC.getCurrentPlayer().useFeatConsumingHP(index)) {
                            GSC.MessageStack.push("Re-rolled dice number " + index + ", got " + GSC.getCurrentPlayer().getUnlockedDice().get(index).getLastRoll());
                            GSC.getCurrentPlayer().setHasUsedFeat(true);
                        } else
                            GSC.MessageStack.push("Re-rolling would kill you...");
                    } catch (IndexOutOfBoundsException e) {
                        Main.ErrorStream.println("Second argument of " + InternalCommandsDictionary.UseFeatLosingHP + " is not a valid index: " + e);
                    }
                } catch (NumberFormatException e) {
                    Main.ErrorStream.println("Second argument of " + InternalCommandsDictionary.UseFeatLosingHP + " is not a valid number: " + e);
                }
            }
        } else if (SSplit[0].equals(InternalCommandsDictionary.UseFeatLosingXP)) {
//            if (GSC.getCurrentPlayer().useFeatConsumingXP(Integer.parseInt(SSplit[SSplit.length - 1]) - 1)) {
//                GSC.MessageStack.push("Re-rolled dice number " + SSplit[SSplit.length - 1] + ", got " + GSC.getCurrentPlayer().getUnlockedDice().get(Integer.parseInt(SSplit[SSplit.length - 1]) - 1).getLastRoll());
//            } else {
//                GSC.MessageStack.push("Re-rolling would break your sword...");
//            }
            if (SSplit.length >= 2) {
                try {
                    int index = Integer.parseInt(SSplit[1]);
                    try {
                        if (GSC.getCurrentPlayer().useFeatConsumingXP(index)) {
                            GSC.MessageStack.push("Re-rolled dice number " + index + ", got " + GSC.getCurrentPlayer().getUnlockedDice().get(index).getLastRoll());
                            GSC.getCurrentPlayer().setHasUsedFeat(true);
                        } else
                            GSC.MessageStack.push("Re-rolling would break your sword...");
                    } catch (IndexOutOfBoundsException e) {
                        Main.ErrorStream.println("Second argument of " + InternalCommandsDictionary.UseFeatLosingXP + " is not a valid index: " + e);
                    }
                } catch (NumberFormatException e) {
                    Main.ErrorStream.println("Second argument of " + InternalCommandsDictionary.UseFeatLosingXP + " is not a valid number: " + e);
                }
            }
        } else if (SSplit[0].equals(InternalCommandsDictionary.ReRollDice) && CanReRollDice()) {
            if (SSplit.length >= 2) {
                try {
                    int index;
                    index = Integer.parseInt(SSplit[1]);
                    try {
                        int roll;
                        roll = GameStateController.getCurrentController().getCurrentPlayer().getUnlockedDice().get(index).getLastRoll();
                        if (roll == 6)
                            GameStateController.getCurrentController().getCurrentPlayer().reRollDice(index);
                    } catch (IndexOutOfBoundsException e) {
                        Main.ErrorStream.println("Second argument of " + InternalCommandsDictionary.ReRollDice + " is not a valid index: " + e);
                    }
                } catch (NumberFormatException e) {
                    Main.ErrorStream.println("Second argument of " + InternalCommandsDictionary.ReRollDice + " is not a valid number: " + e);
                }
            }
        }

        return this;
    }

    @Override
    public IState AttackMonster() {
        GameStateController GSC = GameStateController.getCurrentController();

        int playerAttack = GSC.getCurrentPlayer().getUnlockedDiceSum();

        GSC.getCurrentMonster().incHPCurr(0 - playerAttack);

        if (GSC.getCurrentMonster().getHPCurr() <= 0) {
            if (GSC.getCurrentMonster().getBoss()) {
                int currentZone = GSC.getCurrentZone();
                if (currentZone == 12) { // Qual é a zona final???
                    GSC.MessageStack.push("==== Final Boss defeated! ====\nYou've won Og's Blood!\n");

                    GSC.getCurrentPlayer().resetDice();
                    return new StartState();
                }

                GSC.getCurrentPlayer().incGold(GSC.getCurrentMonster().getGoldReward());
                GSC.getCurrentPlayer().incXP(GSC.getCurrentMonster().getXPReward());
                GSC.setBattledInThisRoom(true); // ???
                GSC.MessageStack.push("Congratulations! You have defeated " + GSC.getCurrentMonster().getName() + "\n");
                GSC.MessageStack.push("Your rewards: " + GSC.getCurrentMonster().getXPReward() + "XP and " + GSC.getCurrentMonster().getGoldReward() + " Gold\n");
                GSC.setCurrentZone(currentZone + 1);
                GSC.setCurrentRoom(1); // Qual é a sala inicial???

                GSC.getCurrentPlayer().resetDice();
                return (new TreasureCard(Deck.TreasureCardID)).Effect();
            }

            GSC.getCurrentPlayer().incXP(GSC.getCurrentMonster().getXPReward());
            GSC.setBattledInThisRoom(true); // ???
            GSC.MessageStack.push("Congratulations! You have defeated " + GSC.getCurrentMonster().getName() + "\n");
            GSC.MessageStack.push("Your rewards: " + GSC.getCurrentMonster().getXPReward() + "XP\n");
            GSC.setCurrentRoom(GSC.getCurrentRoom() + 1);

            GSC.getCurrentPlayer().resetDice();
            return new AwaitCardSelectionState();
        }

        GSC.getCurrentPlayer().resetDice();
        return new SpellPhase();
    }
}
