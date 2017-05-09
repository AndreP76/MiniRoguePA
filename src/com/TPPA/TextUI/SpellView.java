package com.TPPA.TextUI;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.InternalCommandsDictionary;
import com.TPPA.GameLogic.Main;

/**
 * Created by andre on 5/3/17.
 * You're a Lizard, harry
 */
public class SpellView extends StateView {
    @Override
    public void Render() {
        String Text = "";
        String ActionString = "";
        GameStateController GSC = GameStateController.getCurrentController();

        while (!GSC.MessageStack.empty())
            Text += GSC.MessageStack.pop() + "\n";


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
