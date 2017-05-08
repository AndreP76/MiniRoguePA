package com.TPPA.GameLogic.Spells;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Main;

/**
 * Created by andre on 4/20/17.
 */
public class FireSpell extends SpellBase {
    public FireSpell(String ID) {
        super(ID);
    }

//    @Override
//    public IState Effect() {
//        Main.ErrorStream.println("FireSpell effect called");
//        GameStateController.getCurrentController().getCurrentBattle().getMonster.incHP(-8);
//        return new RollPhase();
//    }

    @Override
    public void Effect() {
        GameStateController GSC = GameStateController.getCurrentController();
        Main.ErrorStream.println("FireSpell effect called");
        GSC.MessageStack.push("FireSpell effect called - inflicted +8 damage to " + GSC.getCurrentMonster().getName());

        GSC.getCurrentMonster().incHPCurr(-8);
    }

}
