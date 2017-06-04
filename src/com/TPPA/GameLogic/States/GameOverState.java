package com.TPPA.GameLogic.States;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.IState;
import com.TPPA.GameLogic.Internals.Action;
import com.TPPA.GameLogic.Internals.InternalCommandsDictionary;
import com.TPPA.Main;

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
            GameStateController GSC = getCurrentController();
            GSC.setCurrentController(new GameStateController());
            StartState SS = new StartState(GSC);
            GSC.setCurrentGameState(SS);
            return SS;
        } else if (ActionString.equals(InternalCommandsDictionary.StartCommand)) {
            return new StartState(getCurrentController());
        } else {
            Main.ErrorStream.println("Got unknown command " + ActionString + " on GameOverPhase");
            return this;
        }
    }
}
