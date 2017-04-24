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
        if (GameStateController.getCurrentController().getCurrentPlayer().getSpellsInventory().isEmpty())
            return false;
        else
            return true;
    }

    @Override
    public IState Action(String ActionString) {
        String[] SSplit = ActionString.split(" ");

        if (CanUseSpell() == false || SSplit[0].equals(InternalCommandsDictionary.EndSpellPhase))
            return new DefensePhase();

        if (SSplit[0].equals(InternalCommandsDictionary.UseSpell)) {
            try {
                int index;
                index = Integer.parseInt(SSplit[1]);
                try {
                    SpellBase spellToUse = GameStateController.getCurrentController().getCurrentPlayer().getSpellsInventory().remove(index);
                    return spellToUse.Effect();
                } catch (IndexOutOfBoundsException e) {
                    ErrorStream.println("Second argument of " + InternalCommandsDictionary.SellSpell + " is not a valid index");
                    return this;
                }
            } catch (NumberFormatException e) {
                ErrorStream.println("Second argument of " + InternalCommandsDictionary.SellSpell + " is not a valid number");
                return this;
            }
        }
        return this;
    }

}
