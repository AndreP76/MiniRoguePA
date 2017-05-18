package com.TPPA.GameLogic.States;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.IState;
import com.TPPA.GameLogic.Internals.Action;
import com.TPPA.GameLogic.Internals.InternalCommandsDictionary;
import com.TPPA.GameLogic.Main;

/**
 * Created by andre on 4/5/17.
 */
public class RestingState extends GameState {
    public RestingState(GameStateController GS) {
        super(GS);
    }
    @Override
    public Action[] GetActions() {
        Action[] Act = new Action[3];
        Act[0] = new Action(InternalCommandsDictionary.ReinforceWeapon, "Reinforce your weapon: +1 XP");
        Act[1] = new Action(InternalCommandsDictionary.SearchRation, "Search for ration: +1 Food");
        Act[2] = new Action(InternalCommandsDictionary.Heal, "Heal: +2 HP");
        return Act;
    }

    @Override
    public IState Action(String ActionString) {

        if (ActionString.equals(InternalCommandsDictionary.ReinforceWeapon)) {
            Main.ErrorStream.println("User got 1 XP");
            getCurrentController().MessageStack.push("");
            getCurrentController().getCurrentPlayer().incXP(1);
            return new AwaitCardSelectionState(getCurrentController());
        }
        if (ActionString.equals(InternalCommandsDictionary.SearchRation)) {
            Main.ErrorStream.println("User got 1 Food");
            getCurrentController().getCurrentPlayer().incFood(1);
            return new AwaitCardSelectionState(getCurrentController());
        }
        if (ActionString.equals(InternalCommandsDictionary.Heal)) {
            Main.ErrorStream.println("User got 2 HP");
            getCurrentController().getCurrentPlayer().incHP(2);
            return new AwaitCardSelectionState(getCurrentController());
        }
        return this;
    }
}
