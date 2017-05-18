package com.TPPA.GameLogic.Spells;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Main;

/**
 * Created by andre on 4/20/17.
 */
public class PoisonSpell extends SpellBase {
    public PoisonSpell(GameStateController GSC, String ID) {
        super(GSC, ID);
    }

//    @Override
//    public IState Effect() {
//        Main.ErrorStream.println("PoisonSpell effect called");
//        return null;
//    }

    @Override
    public void Effect() {
        Main.ErrorStream.println("PoisonSpell effect called");

        //GameStateController GSC = GameStateController.getCurrentController();

        String s = "";
        GSC.getCurrentMonster().setPoisoned(true);
        GSC.getCurrentMonster().incHPCurr(-5);

        s += "PoisonSpell effect called - " + GSC.getCurrentMonster().getName() + " received +5 damage\n";
        s += "(" + GSC.getCurrentMonster().getName() + " will receive +5 extra damage per turn until the end of the battle)";
        GSC.MessageStack.push(s);
    }
}
