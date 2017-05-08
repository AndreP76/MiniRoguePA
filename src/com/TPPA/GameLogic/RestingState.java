package com.TPPA.GameLogic;

/**
 * Created by andre on 4/5/17.
 */
public class RestingState extends GameState {

    @Override
    public Action[] GetActions() {
        Action[] Act = new Action[3];
        Act[0] = new Action(InternalCommandsDictionary.ReinforceWeapon, "Reinforce your weapon: +1XP");
        Act[1] = new Action(InternalCommandsDictionary.SearchRation, "Search for ration: +1Food");
        Act[2] = new Action(InternalCommandsDictionary.Heal, "Heal: +2HP");
        return Act;
    }

    @Override
    public IState Action(String ActionString) {

        if (ActionString.equals(InternalCommandsDictionary.ReinforceWeapon)) {
            GameStateController.getCurrentController().getCurrentPlayer().incXP(1);
            //GameStateController.getCurrentController().getCurrentPlayer().unlockNewDie(); //Lidia, explain plz // Sorry, my mistake ( by Lídia)
            return new AwaitCardSelectionState();
        }
        if (ActionString.equals(InternalCommandsDictionary.SearchRation)) {
            GameStateController.getCurrentController().getCurrentPlayer().incFood(1);
            return new AwaitCardSelectionState();
        }
        if (ActionString.equals(InternalCommandsDictionary.Heal)) {
            GameStateController.getCurrentController().getCurrentPlayer().incHP(2);
            return new AwaitCardSelectionState();
        }
        return this;
    }
}
