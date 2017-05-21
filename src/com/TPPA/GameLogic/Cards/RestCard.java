package com.TPPA.GameLogic.Cards;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.IState;
import com.TPPA.GameLogic.States.RestingState;
import com.TPPA.Main;

/**
 * Created by andre on 4/19/17.
 */
public class RestCard extends CardBase {
    public RestCard(GameStateController GSC, String ID) {
        super(GSC, ID, "Resting Card");
    }

    @Override
    public IState Effect() {
        Main.ErrorStream.println("RestCard effect called!");
        return new RestingState(GSC);
    }
}
