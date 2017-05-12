package com.TPPA.GameLogic;

/**
 * Created by andre on 4/5/17.
 * You lost the game
 */

public class GameOverState extends GameState {
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
