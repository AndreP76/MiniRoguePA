package com.TPPA.GameLogic.Spells;

import com.TPPA.GameLogic.IState;
import com.TPPA.GameLogic.Main;
import com.TPPA.GameLogic.RollPhase;

/**
 * Created by andre on 4/20/17.
 */
public class IceSpell extends SpellBase {
    public IceSpell(String ID) {
        super(ID);
    }

    @Override
    public IState Effect() {
        Main.ErrorStream.println("IceSpell effect called");
        return new RollPhase();
    }


}
