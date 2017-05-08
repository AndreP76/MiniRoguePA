package com.TPPA.GameLogic.Spells;

import java.io.Serializable;

/**
 * Created by andre on 4/20/17.
 */
public abstract class SpellBase implements Serializable {
    private String SpellID;

    protected SpellBase(String ID) {
        SpellID = ID;
    }

    //public abstract IState Effect();
    public abstract void Effect();

    public String getSpellID() {
        return this.SpellID;
    }
}
