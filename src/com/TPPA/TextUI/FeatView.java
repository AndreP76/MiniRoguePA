package com.TPPA.TextUI;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Internals.Action;
import com.TPPA.GameLogic.Internals.InternalCommandsDictionary;
import com.TPPA.Main;

import java.util.Arrays;

/**
 * Created by andre on 5/3/17.
 * GODDAMN WARNINGS!
 */
public class FeatView extends StateView {
    FeatView(GameStateController GS) {
        super(GS);
    }
    @Override
    public void Render() {
        String Text = "";
        TextDrawHelper.ClearScreen();
        GameStateController GSC = GS;

        String ActionString = "";

        Text += GSC.getCurrentMonster().toString() + "\n" + GSC.getCurrentPlayer().toString() + "\n" + GSC.getCurrentPlayer().getUnlockedDiceDescription();

        while (!GSC.MessageStack.empty())
            Text += GSC.MessageStack.pop() + "\n";


        if (GSC.getCurrentPlayer().getHasUsedFeat() && GSC.getCurrentGameState().CanReRollDice()) {
            Text += "Re-roll a die with critical damage?\n\t1) Yes\n\t2) No";
            Main.OutputStream.println(Text);

            int Selected = -1;
            while (Selected <= 0 || Selected > 2) {
                while (!TextDrawHelper.InputScanner.hasNextInt()) ;
                Selected = TextDrawHelper.InputScanner.nextInt();
            }

            if (Selected == 1) {
                ActionString = InternalCommandsDictionary.ReRollDice;

                Text = "Which die?\n" + GSC.getCurrentPlayer().getCriticalDiceDescription() + "\n\nChoose: ";

                Main.OutputStream.print(Text);
                Selected = -1;
                //what is this ?
                String[] SSplit = GSC.getCurrentPlayer().getCriticalDiceDescription().split("\n");
                Integer[] validIndexes = new Integer[SSplit.length];
                for (int i = 0; i < validIndexes.length; i++) {
                    validIndexes[i] = Character.getNumericValue(SSplit[i].charAt(0));
                }

                while (!Arrays.asList(validIndexes).contains(Selected)) {
                    while (!TextDrawHelper.InputScanner.hasNextInt()) ;
                    Selected = TextDrawHelper.InputScanner.nextInt();
                    TextDrawHelper.InputScanner.nextLine();//stuff
                }

                ActionString += " " + (Selected - 1);
                GSC.RelayAction(ActionString);
            }

        }

        Text += "Do you wish to use a feat?\n\t1) Yes, by losing 2 HP\n\t2) Yes, by losing 1 XP\n\t3) No, skip to next phase";
        Main.OutputStream.println(Text);

        int Selected = -1;
        while (Selected <= 0 || Selected > 3) {
            while (!TextDrawHelper.InputScanner.hasNextInt()) ;
            Selected = TextDrawHelper.InputScanner.nextInt();
            TextDrawHelper.InputScanner.nextLine();//stuff
        }

        Action[] ActionsAvailable = GSC.getCurrentGameState().GetActions();

        ActionString = ActionsAvailable[Selected - 1].getActionString();


        if (Selected < 3) {
            Selected = -1;
            Text = "Which die do you want to roll again?\n" + GSC.getCurrentPlayer().getUnlockedDiceDescription();
            Main.OutputStream.println(Text);
            while (Selected < 1 || Selected > GSC.getCurrentPlayer().getUnlockedDice().size()) {
                while (!TextDrawHelper.InputScanner.hasNextInt()) ;
                Selected = TextDrawHelper.InputScanner.nextInt();
                TextDrawHelper.InputScanner.nextLine();//stuff
            }
            ActionString += " " + (Selected - 1);
        }

        GSC.RelayAction(ActionString);
    }
}
