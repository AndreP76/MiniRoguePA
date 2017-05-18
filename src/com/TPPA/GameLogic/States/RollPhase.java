package com.TPPA.GameLogic.States;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.IState;
import com.TPPA.GameLogic.Internals.Action;
import com.TPPA.GameLogic.Internals.InternalCommandsDictionary;
import com.TPPA.GameLogic.Main;

/**
 * Created by andre on 4/5/17.
 */
public class RollPhase extends GameState {
    public RollPhase(GameStateController GSC) {
        super(GSC);
    }

    @Override
    public Action[] GetActions() {
        Action[] Act = new Action[3];
        Act[0] = new Action(InternalCommandsDictionary.RollDice, "Roll dice");
        Act[1] = new Action(InternalCommandsDictionary.ReRollDice, "Re-roll a die with critical damage");
        Act[2] = new Action(InternalCommandsDictionary.EndRollPhase, "Skip to next phase");
        return Act;
    }

    @Override
    public IState Action(String ActionString) {

        String[] SSplit = ActionString.split(" ");

        if (SSplit[0].equals(InternalCommandsDictionary.RollDice)) {
            getCurrentController().getCurrentPlayer().rollDice();
            return this;
        }

        if (SSplit[0].equals(InternalCommandsDictionary.ReRollDice)) {
            if (CanReRollDice() && SSplit.length >= 2) {
                try {
                    int index;
                    index = Integer.parseInt(SSplit[1]);
                    try {
                        int roll;
                        roll = getCurrentController().getCurrentPlayer().getUnlockedDice().get(index).getLastRoll();
                        if (roll == 6)
                            getCurrentController().getCurrentPlayer().reRollDice(index);
                    } catch (IndexOutOfBoundsException e) {
                        Main.ErrorStream.println("Second argument of " + InternalCommandsDictionary.ReRollDice + " is not a valid index: " + e);
                        return this;
                    }
                } catch (NumberFormatException e) {
                    Main.ErrorStream.println("Second argument of " + InternalCommandsDictionary.ReRollDice + " is not a valid number");
                    return this;
                }
            }
            return this;
        }
        if (SSplit[0].equals(InternalCommandsDictionary.EndRollPhase)) {
            return new FeatPhase(getCurrentController());
        }
        return this;
    }

}
