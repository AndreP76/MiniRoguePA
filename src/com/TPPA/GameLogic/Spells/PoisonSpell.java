package com.TPPA.GameLogic.Spells;

import com.TPPA.GameLogic.IState;
import com.TPPA.GameLogic.Main;

/**
 * Created by andre on 4/20/17.
 */
public class PoisonSpell extends SpellBase {
    public PoisonSpell(String ID) {
        super(ID);
    }

    @Override
    public IState Effect() {
        Main.ErrorStream.println("PoisonSpell effect called");
        return null;
    }

}
