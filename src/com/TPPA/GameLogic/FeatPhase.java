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

        if (SSplit[0].equals(InternalCommandsDictionary.EndFeatPhase)) {
            //int monsterHP = calculateAttackResult();
            GameStateController.getCurrentController().getCurrentPlayer().resetDice();
            //if(monsterHP <= 0)
            //return new AwaitCardSelectionState();
            return new RollPhase();
        }

        return this;
    }
}
