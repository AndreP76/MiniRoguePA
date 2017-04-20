package com.TPPA.GameLogic.Spells;

import com.TPPA.GameLogic.DefensePhase;
import com.TPPA.GameLogic.IState;
import com.TPPA.GameLogic.Main;

/**
 * Created by andre on 4/20/17.
 */
public class FireSpell extends SpellBase {
    public FireSpell(String ID) {
        super(ID);
    }

    @Override
    public IState Effect() {
        Main.ErrorStream.println("FireSpell effect called");
        //GameStateController.getCurrentController().getCurrentBattle().getMonster.incHP(-8);
        return new DefensePhase();
    }
}
