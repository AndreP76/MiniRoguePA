package com.TPPA.GameLogic.Cards;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.IState;
import com.TPPA.GameLogic.TradingState;

/**
 * Created by andre on 4/19/17.
 */
public class MerchantCard extends CardBase {
    public MerchantCard(String ID) {
        super(ID);
    }

    @Override
    public IState Effect() {
        return new TradingState();
    }
}
