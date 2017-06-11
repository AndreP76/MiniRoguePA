package com.TPPA.TextUI;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Internals.Action;
import com.TPPA.GameLogic.Internals.InternalCommandsDictionary;
import com.TPPA.Main;

/**
 * Created by andre on 5/1/17.
 */
public class TradingStateView extends StateView {
    TradingStateView(GameStateController GS) {
        super(GS);
    }

    @Override
    public void Render() {
        String Text = "";
        String ActionString = "";
        GameStateController GSC = GS;
        super.Render();

        Text += "\n==== Found a Merchant ====\n\n";
        Text += GSC.getCurrentPlayer().toString() + "\n\n";
        Text += "What would you like to do?\n";

        Action[] ActionsAvailable = GSC.getCurrentGameState().GetActions();

        for (int i = 0; i < ActionsAvailable.length; i++) {
            Text += "\t" + (i + 1) + ") " + ActionsAvailable[i].getDescriptionString() + "\n";
        }

        Text += "\n\nChoose: ";

        Main.OutputStream.println(Text);
        int Selected = -1;
        while (Selected <= 0 || Selected > ActionsAvailable.length) {
            while (!TextDrawHelper.InputScanner.hasNextInt()) ;
            Selected = TextDrawHelper.InputScanner.nextInt();
        }

        ActionString += ActionsAvailable[Selected - 1].getActionString();

        if (ActionString.equalsIgnoreCase(InternalCommandsDictionary.BuySpell)) {
            Text = "\nWhich spell would you like to buy?\n\t1) Fire Spell\n\t2) Heal Spell\n\t3)Ice Spell\n\t4) Poison Spell\n\n\nChoose: ";

            Main.OutputStream.println(Text);

            Selected = -1;
            while (Selected <= 0 || Selected > 4) {
                while (!TextDrawHelper.InputScanner.hasNextInt()) ;
                Selected = TextDrawHelper.InputScanner.nextInt();
            }

            switch (Selected) {
                case 1:
                    ActionString += " " + InternalCommandsDictionary.FireSpellID;
                case 2:
                    ActionString += " " + InternalCommandsDictionary.HealSpellID;
                case 3:
                    ActionString += " " + InternalCommandsDictionary.IceSpellID;
                case 4:
                    ActionString += " " + InternalCommandsDictionary.PoisonSpellID;
            }
        } else if (ActionString.equalsIgnoreCase(InternalCommandsDictionary.SellSpell)) {
            if (!GSC.getCurrentPlayer().getSpellsInventory().isEmpty()) {
                Text = "\nWhich spell would you like to sell?\n" + GSC.getCurrentPlayer().getSpellsDescription() + "\n\nChoose: ";
                Main.OutputStream.println(Text);

                Selected = -1;
                while (Selected <= 0 || Selected > GSC.getCurrentPlayer().getSpellsInventory().size()) {
                    while (!TextDrawHelper.InputScanner.hasNextInt()) ;
                    Selected = TextDrawHelper.InputScanner.nextInt();
                }

                ActionString += " " + (Selected - 1);
            } else {
                Text = "\nNo spells available to sell";
                Main.OutputStream.println(Text);
            }

        }

        GSC.RelayAction(ActionString);
    }
}
