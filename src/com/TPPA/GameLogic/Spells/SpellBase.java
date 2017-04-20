package com.TPPA.GameLogic.Spells;

import com.TPPA.GameLogic.IState;

import java.io.Serializable;

/**
 * Created by andre on 4/20/17.
 */
public abstract class SpellBase implements Serializable {
    private String SpellID;

    protected SpellBase(String ID) {
        SpellID = ID;
    }

    public abstract IState Effect();
}
