package com.TPPA.GameLogic;

/**
 * Created by andre on 4/5/17.
 */
public class RollPhase extends GameState {

    @Override
    public Action[] GetActions() {
        Action[] Act = new Action[3];
        Act[0] = new Action(InternalCommandsDictionary.RollDice, "Rodar os dados");
        Act[1] = new Action(InternalCommandsDictionary.ReRollDice, "Rodar novamente um dado com dano crítico");
        Act[2] = new Action(InternalCommandsDictionary.EndRollPhase, "Passar à fase seguinte");
        return Act;
    }

    @Override
    public IState Action(String ActionString) {

        String[] SSplit = ActionString.split(" ");

        if (SSplit[0].equals(InternalCommandsDictionary.RollDice)) {
            GameStateController.getCurrentController().getCurrentPlayer().rollDice();
            return this;
        }

        if (SSplit[0].equals(InternalCommandsDictionary.ReRollDice)) {
            if (CanReRollDice() && SSplit.length >= 2) {
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
            return new FeatPhase(); // resetDice só é feito quando concluída a FeatPhase
        }
        return this;
    }

    @Override
    public Boolean CanReRollDice() {
        for (Dice currentDie : GameStateController.getCurrentController().getCurrentPlayer().getUnlockedDice()) {
            if (currentDie.getLastRoll() == 6)
                return true;
        }

        return false;
    }

}
