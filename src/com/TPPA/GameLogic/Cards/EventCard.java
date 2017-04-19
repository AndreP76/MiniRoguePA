package com.TPPA.GameLogic.Cards;

import com.TPPA.GameLogic.AwaitCardSelectionState;
import com.TPPA.GameLogic.IState;
import com.TPPA.GameLogic.Main;

/**
 * Created by andre on 4/19/17.
 */
public class EventCard extends CardBase {
    public EventCard(String ID) {
        super(ID);
    }

    @Override
    public IState Effect() {
        Main.ErrorStream.println("EventCard effect called!");
        return new AwaitCardSelectionState();
    }
}
