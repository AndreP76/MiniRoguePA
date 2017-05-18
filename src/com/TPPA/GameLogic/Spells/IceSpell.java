package com.TPPA.GameLogic.Spells;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Main;

/**
 * Created by andre on 4/20/17.
 */
public class IceSpell extends SpellBase {
    public IceSpell(GameStateController GSC, String ID) {
        super(GSC, ID);
    }

//    @Override
//    public IState Effect() {
//        Main.ErrorStream.println("IceSpell effect called");
//        return new RollPhase();
//    }

    @Override
    public void Effect() {
        Main.ErrorStream.println("IceSpell effect called");

        //GameStateController GSC = GameStateController.getCurrentController();

        GSC.getCurrentMonster().setCanAttack(false);

        GSC.MessageStack.push("IceSpell effect called - " + GSC.getCurrentMonster().getName() + " can't attack on the current turn");
    }

}
