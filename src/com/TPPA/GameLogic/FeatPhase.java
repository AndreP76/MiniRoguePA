package com.TPPA.GameLogic;

/**
 * Created by andre on 4/5/17.
 * Change the course of battle through sheer despair
 */
public class FeatPhase extends GameState {
    @Override
    public Action[] GetActions() {
        Action[] Act = new Action[4];
        Act[0] = new Action(InternalCommandsDictionary.UseFeatLosingHP, "Use Feat sacrificing HP (- 2HP)");
        Act[1] = new Action(InternalCommandsDictionary.UseFeatLosingXP, "Use Feat sacrificing XP (- 1XP)");
        Act[2] = new Action(InternalCommandsDictionary.EndFeatPhase, "Skip to next phase");
        Act[3] = new Action(InternalCommandsDictionary.ReRollDice, "Re-roll a die with critical damage");
        return Act;
    }

    @Override
    public IState Action(String ActionString) {
        String[] SSplit = ActionString.split(" ");

        GameStateController GSC = GameStateController.getCurrentController();
        if (SSplit[0].equals(InternalCommandsDictionary.EndFeatPhase)) {
            GSC.getCurrentPlayer().setHasUsedFeat(false);
            //calcular dano infligido ao monstro
            return AttackMonster();
        } else if (SSplit[0].equals(InternalCommandsDictionary.UseFeatLosingHP)) {
            if (SSplit.length >= 2) {
                try {
                    int index = Integer.parseInt(SSplit[SSplit.length - 1]);
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
            if (SSplit.length >= 2) {
                try {
                    int index = Integer.parseInt(SSplit[SSplit.length - 1]);
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

        GSC.getCurrentMonster().incHPCurr(-playerAttack);

        if (GSC.getCurrentMonster().getPoisoned())
            GSC.getCurrentMonster().incHPCurr(-5);

        if (GSC.getCurrentMonster().getHPCurr() <= 0)
            return OnDefeatingMonster();

        GSC.getCurrentPlayer().resetDice();
        return new SpellPhase();
    }
}
