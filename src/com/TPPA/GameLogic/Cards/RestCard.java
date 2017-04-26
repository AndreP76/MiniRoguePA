package com.TPPA.GameLogic.Cards;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.IState;
import com.TPPA.GameLogic.Main;
import com.TPPA.GameLogic.RestingState;

/**
 * Created by andre on 4/19/17.
 */
public class RestCard extends CardBase {
    public RestCard(String ID) {
        super(ID, "Resting Card");
    }

    @Override
    public IState Effect() {
        Main.ErrorStream.println("RestCard effect called!");
        return new RestingState();
    }
}
