package com.TPPA.GameLogic;

import com.TPPA.GameLogic.Spells.SpellBase;

import static com.TPPA.GameLogic.Main.ErrorStream;

/**
 * Created by andre on 4/5/17.
 */
public class SpellPhase extends GameState {

    @Override
    public Action[] GetActions() {
        Action[] Act = new Action[2];
        Act[0] = new Action(InternalCommandsDictionary.UseSpell, "Usar um feitiço");
        Act[1] = new Action(InternalCommandsDictionary.EndSpellPhase, "Passar à fase seguinte");

        return Act;
    }

    @Override
    public Boolean CanUseSpell() {
        return !GameStateController.getCurrentController().getCurrentPlayer().getSpellsInventory().isEmpty();
    }

    @Override
    public IState Action(String ActionString) {
        String[] SSplit = ActionString.split(" ");

        if (CanUseSpell() == false || SSplit[0].equals(InternalCommandsDictionary.EndSpellPhase)) {
            //calculateDefenseResult();
            return new RollPhase();
        }

        if (SSplit[0].equals(InternalCommandsDictionary.UseSpell) && SSplit.length >= 2) {
            try {
                int index;
                index = Integer.parseInt(SSplit[1]);
                try {
                    SpellBase spellToUse = GameStateController.getCurrentController().getCurrentPlayer().getSpellsInventory().remove(index);
                    return spellToUse.Effect();
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

}
