package com.TPPA.GameLogic.Cards;

import com.TPPA.GameLogic.IState;

import java.io.Serializable;

/**
 * Created by andre on 4/19/17.
 */
public abstract class CardBase implements Serializable {
    protected String CardID;

    //constructors are package-private by default!
    protected CardBase(String ID) {
        this.CardID = ID;
    }

    public abstract IState Effect();

    public String getCardID() {
        return CardID;
    }

    @Override
    public String toString() {
        return CardID;
    }
}
