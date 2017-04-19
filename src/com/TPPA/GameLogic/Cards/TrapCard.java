package com.TPPA.GameLogic.Cards;

import com.TPPA.GameLogic.AwaitCardSelectionState;
import com.TPPA.GameLogic.IState;
import com.TPPA.GameLogic.Main;

/**
 * Created by andre on 4/19/17.
 */
public class TrapCard extends CardBase {
    public TrapCard(String ID) {
        super(ID);
    }

    @Override
    public IState Effect() {
        Main.ErrorStream.println("TrapCard effect called!");
        return new AwaitCardSelectionState();
    }
}
