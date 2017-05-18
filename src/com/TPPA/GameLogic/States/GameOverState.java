package com.TPPA.GameLogic.States;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.IState;
import com.TPPA.GameLogic.Internals.Action;
import com.TPPA.GameLogic.Internals.InternalCommandsDictionary;
import com.TPPA.GameLogic.Main;

/**
 * Created by andre on 4/5/17.
 * You lost the game
 */

public class GameOverState extends GameState {
    public GameOverState(GameStateController GS) {
        super(GS);
    }

    @Override
    public Action[] GetActions() {
        Action[] Act = new Action[1];
        Act[0] = new Action(InternalCommandsDictionary.QuitCommand, "Exit game");
        return Act;
    }

    @Override
    public IState Action(String ActionString) {
        if (ActionString.equals(InternalCommandsDictionary.QuitCommand)) {
            System.exit(0);
        } else {
            Main.ErrorStream.println("Got unknown command " + ActionString + " on GameOverPhase");
            return this;
        }
        return this;
    }
}
