package com.TPPA.TextUI;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Internals.InternalCommandsDictionary;
import com.TPPA.Main;

import java.util.Arrays;

/**
 * Created by andre on 5/3/17.
 */
public class RollView extends StateView {
    RollView(GameStateController GS) {
        super(GS);
    }

    @Override
    public void Render() {
        String Text = "";
        GameStateController GSC = GS;
        while (!GSC.MessageStack.empty())
            Text += GSC.MessageStack.pop() + "\n";


        Text += "==== " + GSC.getCurrentMonster().getName() + (GSC.getCurrentMonster().getBoss() ? " (BOSS)" : "") + " encountered! ====\n";
        Main.OutputStream.println(Text);
        Main.OutputStream.println(GSC.getCurrentMonster().toString());
        Main.OutputStream.println(GSC.getCurrentPlayer().toString());

        Text = "";

        String ActionString = "";

        if (!GSC.getCurrentPlayer().hasRolledDice()) {
            //Tem obrigatoriamente de rodar os dados para passar à fase seguinte
            Text += "\n1) Roll dice\n\n\nChoose: ";

            Main.OutputStream.print(Text);
            int Selected = -1;
            while (Selected != 1) {
                while (!TextDrawHelper.InputScanner.hasNextInt()) ;
                Selected = TextDrawHelper.InputScanner.nextInt();
            }
            ActionString += InternalCommandsDictionary.RollDice;
            GSC.RelayAction(ActionString);



        } else if (GSC.getCurrentGameState().CanReRollDice()) {
            Main.OutputStream.println(GSC.getCurrentPlayer().getUnlockedDiceDescription());

            Text += "\nRe-roll a die with critical damage?\n\t1) Yes\n\t2) No\n\n\nChoose: ";
            //Utilizador tem de selecionar sim ou não
            //Se selecionar sim perguntar qual o dado que quer rodar
            //Se não, ENDROLLPHASE

            Main.OutputStream.print(Text);
            int Selected = -1;
            while (Selected <= 0 || Selected > 2) {
                while (!TextDrawHelper.InputScanner.hasNextInt()) ;
                Selected = TextDrawHelper.InputScanner.nextInt();
            }

            if (Selected == 2) {
                ActionString += InternalCommandsDictionary.EndRollPhase;
                GSC.RelayAction(ActionString);
            } else {
                ActionString += InternalCommandsDictionary.ReRollDice;

                Text = "Which die?\n" + GSC.getCurrentPlayer().getCriticalDiceDescription() + "\n\nChoose: ";

                Main.OutputStream.print(Text);
                Selected = -1;
                String[] SSplit = GSC.getCurrentPlayer().getCriticalDiceDescription().split("\n");
                Integer[] validIndexes = new Integer[SSplit.length];
                for (int i = 0; i < validIndexes.length; i++) {
                    validIndexes[i] = Character.getNumericValue(SSplit[i].charAt(0));
                }

                while (!Arrays.asList(validIndexes).contains(Selected)) {
                    while (!TextDrawHelper.InputScanner.hasNextInt()) ;
                    Selected = TextDrawHelper.InputScanner.nextInt();
                }

                ActionString += " " + (Selected - 1);
                GSC.RelayAction(ActionString);

            }
        } else {
            ActionString += InternalCommandsDictionary.EndRollPhase;
            //Main.OutputStream.println(GSC.getCurrentPlayer().getUnlockedDiceDescription());
            GSC.RelayAction(ActionString);
        }

    }
}
