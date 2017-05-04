package com.TPPA.GameLogic;

/**
 * Created by andre on 4/5/17.
 * Change the course of battle through sheer despair
 */
public class FeatPhase extends GameState {
    @Override
    public Action[] GetActions() {
        Action[] Act = new Action[4];
        Act[0] = new Action(InternalCommandsDictionary.UseFeatLosingHP, "Usar Feat sacrificando HP (- 2HP)");
        Act[1] = new Action(InternalCommandsDictionary.UseFeatLosingXP, "Usar Feat sacrificando HP (- 1XP)");
        Act[2] = new Action(InternalCommandsDictionary.ReRollDice, "Rodar novamente um dado com dano crítico");
        Act[3] = new Action(InternalCommandsDictionary.EndFeatPhase, "Passar à fase seguinte");
        return Act;
    }

    @Override
    public IState Action(String ActionString) {
        //TODO:  End this phase
        String[] SSplit = ActionString.split(" ");

        GameStateController GSC = GameStateController.getCurrentController();
        if (SSplit[0].equals(InternalCommandsDictionary.EndFeatPhase)) {
            GameStateController.getCurrentController().getCurrentPlayer().resetDice();
            return new SpellPhase();
        } else if (SSplit[0].equals(InternalCommandsDictionary.UseFeatLosingHP)) {
            if (GSC.getCurrentPlayer().useFeatConsumingHP(Integer.parseInt(SSplit[SSplit.length - 1]) - 1)) {
                GSC.MessageStack.push("Re-rolled dice number " + SSplit[SSplit.length - 1] + ", got " + GSC.getCurrentPlayer().getUnlockedDice().get(Integer.parseInt(SSplit[SSplit.length - 1]) - 1).getLastRoll());
            } else {
                GSC.MessageStack.push("Re-rolling would kill you...");
            }
            return this;
        } else if (SSplit[0].equals(InternalCommandsDictionary.UseFeatLosingXP)) {
            if (GSC.getCurrentPlayer().useFeatConsumingXP(Integer.parseInt(SSplit[SSplit.length - 1]) - 1)) {
                GSC.MessageStack.push("Re-rolled dice number " + SSplit[SSplit.length - 1] + ", got " + GSC.getCurrentPlayer().getUnlockedDice().get(Integer.parseInt(SSplit[SSplit.length - 1]) - 1).getLastRoll());
            } else {
                GSC.MessageStack.push("Re-rolling would break your sword...");
            }
        } else if (SSplit[0].equals(InternalCommandsDictionary.ReRollDice)) {
            return new RollPhase();
        }

        return this;
    }
}
