package com.TPPA.GameLogic.Spells;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.Main;

/**
 * Created by andre on 4/20/17.
 */
public class HealSpell extends SpellBase {

    public HealSpell(GameStateController GSC, String ID) {
        super(GSC, ID);
    }

//    @Override
//    public IState Effect() {
//        Main.ErrorStream.println("HealSpell effect called");
//        GameStateController.getCurrentController().getCurrentPlayer().incHP(8);
//        return new RollPhase();
//    }


    @Override
    public void Effect() {
        Main.ErrorStream.println("HealSpell effect called");

        //GameStateController GSC = GameStateController.getCurrentController();
        if (GSC.getCurrentPlayer().incHP(8))
            GSC.MessageStack.push("HealSpell effect called - Player gained HP");
        else
            GSC.MessageStack.push("HealSpell effect called, but Player HP can't go any higher");
    }
}
