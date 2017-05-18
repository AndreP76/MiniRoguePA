package com.TPPA.GameLogic.States;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.IState;
import com.TPPA.GameLogic.Internals.Action;
import com.TPPA.GameLogic.Internals.InternalCommandsDictionary;
import com.TPPA.GameLogic.Spells.SpellBase;

import static com.TPPA.GameLogic.Main.ErrorStream;

/**
 * Created by andre on 4/5/17.
 */
public class SpellPhase extends GameState {

    public SpellPhase(GameStateController GSC) {
        super(GSC);
    }

    @Override
    public Action[] GetActions() {
        Action[] Act = new Action[2];
        Act[0] = new Action(InternalCommandsDictionary.UseSpell, "Use a spell");
        Act[1] = new Action(InternalCommandsDictionary.EndSpellPhase, "Skip to next phase");

        return Act;
    }

    @Override
    public Boolean CanUseSpell() {
        return !getCurrentController().getCurrentPlayer().getSpellsInventory().isEmpty();
    }

    @Override
    public IState Action(String ActionString) {
        String[] SSplit = ActionString.split(" ");

        if (!CanUseSpell() || SSplit[0].equals(InternalCommandsDictionary.EndSpellPhase)) {

            return DefendFromMonster();
        }

        if (SSplit[0].equals(InternalCommandsDictionary.UseSpell) && SSplit.length >= 2) {
            try {
                int index;
                index = Integer.parseInt(SSplit[1]);
                try {
                    SpellBase spellToUse = getCurrentController().getCurrentPlayer().getSpellsInventory().remove(index);
                    return UseSpell(spellToUse);
                } catch (IndexOutOfBoundsException e) {
                    ErrorStream.println("Second argument of " + InternalCommandsDictionary.UseSpell + " is not a valid index: " + e);
                    return this;
                }
            } catch (NumberFormatException e) {
                ErrorStream.println("Second argument of " + InternalCommandsDictionary.UseSpell + " is not a valid number: " + e);
                return this;
            }
        }
        return this;
    }

    @Override
    public IState UseSpell(SpellBase spellToUse) {
        GameStateController GSC = getCurrentController();

        spellToUse.Effect();
        if (GSC.getCurrentMonster().getHPCurr() <= 0)
            return OnDefeatingMonster();

        return DefendFromMonster();
    }

    @Override
    public IState DefendFromMonster() {
        GameStateController GSC = getCurrentController();

        String s = "";
        if (!GSC.getCurrentMonster().getCanAttack()) {
            GSC.getCurrentMonster().setCanAttack(true);
            s += GSC.getCurrentMonster().getName() + " can't attack this turn";
            GSC.MessageStack.push(s);

            return new RollPhase(getCurrentController());
        }

        int damage;

        if (GSC.getCurrentMonster().getStrength() > GSC.getCurrentPlayer().getArmor()) {
            damage = GSC.getCurrentMonster().getStrength() - GSC.getCurrentPlayer().getArmor();
            GSC.getCurrentPlayer().incHP(-damage);
            s += GSC.getCurrentMonster().getName() + " inflicted +" + (damage) + " to Player!";
        } else {
            s += GSC.getCurrentMonster().getName() + " doesn't have enough power to damage through Player's armor";
        }

        GSC.MessageStack.push(s);

        if (GSC.getCurrentPlayer().getHP() <= 0) {
            s = GSC.getCurrentMonster().getName() + " has defeated you!";
            GSC.MessageStack.push(s);
            return new GameOverState(getCurrentController());
        }

        return new RollPhase(getCurrentController());
    }

}
