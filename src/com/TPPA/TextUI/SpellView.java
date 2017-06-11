package com.TPPA.TextUI;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Internals.InternalCommandsDictionary;
import com.TPPA.Main;

/**
 * Created by andre on 5/3/17.
 */
public class SpellView extends StateView {
    SpellView(GameStateController GS) {
        super(GS);
    }

    @Override
    public void Render() {
        String Text = "";
        String ActionString = "";
        GameStateController GSC = GS;
        super.Render();

        if (!GSC.getCurrentGameState().CanUseSpell()) {
            Main.OutputStream.println("You have no available spells to use");
            GSC.RelayAction(InternalCommandsDictionary.EndSpellPhase);
        }

        Text += GSC.getCurrentPlayer().getSpellsDescription();
        int numberOfChoices = GSC.getCurrentPlayer().getSpellsInventory().size() + 1;
        Text += numberOfChoices + ") Skip to next stage\n\n\nChoose: ";
        Main.OutputStream.print(Text);

        int Selected = -1;
        while (Selected <= 0 || Selected > numberOfChoices) {
            while (!TextDrawHelper.InputScanner.hasNextInt()) ;
            Selected = TextDrawHelper.InputScanner.nextInt();
        }

        if (Selected == numberOfChoices) {
            ActionString += InternalCommandsDictionary.EndSpellPhase;
        } else {
            ActionString += InternalCommandsDictionary.UseSpell + " " + (Selected - 1);
        }

        GSC.RelayAction(ActionString);
    }
}
