package com.TPPA.GameLogic.Cards;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.IState;
import com.TPPA.GameLogic.Internals.Dice;
import com.TPPA.GameLogic.Internals.InternalCommandsDictionary;
import com.TPPA.GameLogic.Main;
import com.TPPA.GameLogic.Spells.FireSpell;
import com.TPPA.GameLogic.Spells.HealSpell;
import com.TPPA.GameLogic.Spells.IceSpell;
import com.TPPA.GameLogic.Spells.PoisonSpell;
import com.TPPA.GameLogic.States.AwaitCardSelectionState;
import com.TPPA.GameLogic.States.FeatPhase;
import com.TPPA.GameLogic.States.SpellPhase;

/**
 * Created by andre on 4/19/17.
 */
public class TreasureCard extends CardBase {
    public TreasureCard(GameStateController GSC, String ID) {
        super(GSC, ID, "Treasure Card");
    }

    @Override
    public IState Effect() {
        Main.ErrorStream.println("Treasure card effect was called!");
        //GameStateController GSC = GameStateController.getCurrentController();

        if (!(GSC.getCurrentGameState() instanceof FeatPhase) && !(GSC.getCurrentGameState() instanceof SpellPhase)) {
            if (GSC.getBattledInThisRoom()) {
                Main.ErrorStream.println("Got 2 gold");
                GSC.MessageStack.push("You got 2 gold");
                GSC.getCurrentPlayer().incGold(2);
            } else {
                Main.ErrorStream.println("Got 1 gold");
                GSC.MessageStack.push("You got 1 gold");
                GSC.getCurrentPlayer().incGold(1);
            }
        }

        Dice d = new Dice();

        if (d.Roll() < 5 && !(GSC.getCurrentGameState() instanceof FeatPhase) && !(GSC.getCurrentGameState() instanceof SpellPhase))
            return new AwaitCardSelectionState(GSC);

        // só é dada outra recompensa ao jogador se o primeiro dado rodado der 5 ou mais
        switch (d.Roll()) {
            case 1:
                GSC.getCurrentPlayer().incArmor(1);
                GSC.MessageStack.push("Got an Armor Piece");
                Main.ErrorStream.println("Got 1 Armor");
                break;
            case 2:
                GSC.getCurrentPlayer().incXP(2);
                GSC.MessageStack.push("Got an 2 XP");
                Main.ErrorStream.println("Got 2 XP");
                break;
            case 3:
                GSC.getCurrentPlayer().getSpellsInventory().add(new FireSpell(GSC, InternalCommandsDictionary.FireSpellID));
                GSC.MessageStack.push("Got a Fire Spell!");
                Main.ErrorStream.println("Got FireSpell");
                break;
            case 4:
                GSC.getCurrentPlayer().getSpellsInventory().add(new IceSpell(GSC, InternalCommandsDictionary.IceSpellID));
                GSC.MessageStack.push("Got an Ice Spell!");
                Main.ErrorStream.println("Got IceSpell");
                break;
            case 5:
                GSC.getCurrentPlayer().getSpellsInventory().add(new PoisonSpell(GSC, InternalCommandsDictionary.PoisonSpellID));
                GSC.MessageStack.push("Got a Poisoning Spell!");
                Main.ErrorStream.println("Got PoisonSpell");
                break;
            case 6:
                GSC.getCurrentPlayer().getSpellsInventory().add(new HealSpell(GSC, InternalCommandsDictionary.HealSpellID));
                GSC.MessageStack.push("Got an Healing Spell!");
                Main.ErrorStream.println("Got HealSpell");
                break;
            default:
                Main.ErrorStream.println("Unknown dice roll. Got nothing");
        }
        return new AwaitCardSelectionState(GSC);
    }
}
