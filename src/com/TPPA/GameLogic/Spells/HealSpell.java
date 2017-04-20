package com.TPPA.GameLogic.Spells;

import com.TPPA.GameLogic.*;

/**
 * Created by andre on 4/20/17.
 */
public class HealSpell extends SpellBase {

    public HealSpell(String ID) {
        super(ID);
    }

    @Override
    public IState Effect() {
        Main.ErrorStream.println("HealSpell effect called");
        GameStateController.getCurrentController().getCurrentPlayer().incHP(8);
        return new DefensePhase();
    }
}
