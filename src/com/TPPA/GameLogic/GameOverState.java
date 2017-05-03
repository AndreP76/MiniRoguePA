package com.TPPA.GameLogic;

/**
 * Created by andre on 4/5/17.
 * You lost the game
 */

public class GameOverState extends GameState {
    @Override
    public Action[] GetActions() {
        return new Action[0];
    }

    @Override
    public IState Action(String ActionString) {
        if (ActionString.equals(InternalCommandsDictionary.Skip)) {
            return new StartState();
        } else {
            Main.ErrorStream.println("Got unknown command " + ActionString + " on GameOverPhase");
            return this;
        }
    }
}
