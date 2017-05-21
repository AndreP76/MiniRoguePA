package com.TPPA.GameLogic.Cards;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.IState;
import com.TPPA.GameLogic.States.TradingState;
import com.TPPA.Main;

/**
 * Created by andre on 4/19/17.
 */
public class MerchantCard extends CardBase {
    public MerchantCard(GameStateController GSC, String ID) {
        super(GSC, ID, "Merchant Card");
    }

    @Override
    public IState Effect() {
        Main.ErrorStream.println("TradingCard effect called");
        return new TradingState(GSC);
    }
}
