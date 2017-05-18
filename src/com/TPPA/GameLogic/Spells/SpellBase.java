package com.TPPA.GameLogic.Spells;

import com.TPPA.GameLogic.GameStateController;

import java.io.Serializable;

/**
 * Created by andre on 4/20/17.
 */
public abstract class SpellBase implements Serializable {
    protected GameStateController GSC;
    private String SpellID;

    protected SpellBase(GameStateController GSC, String ID) {
        this.GSC = GSC;
        SpellID = ID;
    }

    //public abstract IState Effect();
    public abstract void Effect();

    public String getSpellID() {
        return this.SpellID;
    }
}
