package com.TPPA.GameLogic.Cards;

import com.TPPA.GameLogic.*;
import com.TPPA.GameLogic.Spells.*;

/**
 * Created by andre on 4/19/17.
 */
public class TreasureCard extends CardBase {
    public TreasureCard(String ID) {
        super(ID, "Treasure Card");
    }

    @Override
    public IState Effect() {
        Main.ErrorStream.println("Treasure card effect was called!");
        GameStateController GSC = GameStateController.getCurrentController();
        if (GSC.getBattledInThisRoom()) {
            Main.ErrorStream.println("Got 2 gold");
            GSC.getCurrentPlayer().incGold(2);
        } else {
            Main.ErrorStream.println("Got 1 gold");
            GSC.getCurrentPlayer().incGold(1);
        }

        Dice d = new Dice();
        switch (d.Roll()) {
            case 1: /*Communicate with view, Somehow...*/
                GSC.getCurrentPlayer().incArmor(1);
                Main.ErrorStream.println("Got 1 Armor");
                break;
            case 2:
                GSC.getCurrentPlayer().incXP(2);
                Main.ErrorStream.println("Got 2 XP");
                break;
            case 3:
                GSC.getCurrentPlayer().getSpellsInventory().add(new FireSpell(InternalCommandsDictionary.FireSpellID));
                Main.ErrorStream.println("Got FireSpell");
                break;
            case 4:
                GSC.getCurrentPlayer().getSpellsInventory().add(new IceSpell(InternalCommandsDictionary.IceSpellID));
                Main.ErrorStream.println("Got IceSpell");
                break;
            case 5:
                GSC.getCurrentPlayer().getSpellsInventory().add(new PoisonSpell(InternalCommandsDictionary.PoisonSpellID));
                Main.ErrorStream.println("Got PoisonSpell");
                break;
            case 6:
                GSC.getCurrentPlayer().getSpellsInventory().add(new HealSpell(InternalCommandsDictionary.HealSpellID));
                Main.ErrorStream.println("Got HealSpell");
                break;
            default:
                Main.ErrorStream.println("Unknown dice roll. Got nothing");
        }
        return new AwaitCardSelectionState();
    }
}
