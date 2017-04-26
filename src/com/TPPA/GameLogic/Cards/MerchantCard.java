package com.TPPA.GameLogic.Cards;

import com.TPPA.GameLogic.IState;
import com.TPPA.GameLogic.Main;
import com.TPPA.GameLogic.TradingState;

/**
 * Created by andre on 4/19/17.
 */
public class MerchantCard extends CardBase {
    public MerchantCard(String ID) {
        super(ID, "Merchant Card");
    }

    @Override
    public IState Effect() {
        Main.ErrorStream.println("TradingCard effect called");
        return new TradingState();
    }
}
