package com.TPPA.GameLogic.Cards;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.IState;

import java.io.Serializable;

/**
 * Created by andre on 4/19/17.
 */
public abstract class CardBase implements Serializable {
    protected String CardID;
    protected String CardName;
    protected GameStateController GSC;
    //constructors are package-private by default!
    protected CardBase(GameStateController GSC, String ID, String Name) {
        this.GSC = GSC;
        this.CardID = ID;
        this.CardName = Name;
    }

    public abstract IState Effect();

    public String getCardID() {
        return CardID;
    }

    @Override
    public String toString() {
        return CardName;
    }
}
