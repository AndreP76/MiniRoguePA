package com.TPPA.TextUI;

import com.TPPA.GameLogic.Action;
import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Main;

import java.util.Arrays;

/**
 * Created by andre on 5/3/17.
 * Roll roll roll
 */
public class RollView extends StateView {
    @Override
    public void Render() {
        //TODO : IMPLEMENT THIS
        Main.OutputStream.println("IMPLEMENT THIS YA LAZY BASTARDS");

        String Text = "";
        GameStateController GSC = GameStateController.getCurrentController();
        while (!GSC.MessageStack.empty())
            Text += GSC.MessageStack.pop() + "\n";

        Action[] ActionsAvailable = GSC.getCurrentGameState().GetActions();

        String ActionString = "";

        if (!GSC.getCurrentPlayer().hasRolledDice()) {
            //Tem obrigatoriamente de rodar os dados para passar à fase seguinte
            Text += "\n1) Rodar dados\n\n\nEscolha: ";

            Main.OutputStream.print(Text);
            int Selected = -1;
            while (Selected != 1) {
                while (!TextDrawHelper.InputScanner.hasNextInt()) ;
                Selected = TextDrawHelper.InputScanner.nextInt();
            }
            ActionString += ActionsAvailable[Selected - 1].getActionString();
        } else if (GSC.getCurrentGameState().CanReRollDice()) {
            Text += "\nRodar novamente um dado com dano crítico?\n\t1) Sim\n\t2) Não\n\n\nEscolha: ";
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
                ActionString += ActionsAvailable[2].getActionString();
            } else {
                ActionString += ActionsAvailable[1].getActionString();

                Text = "Qual dado?\n" + GSC.getCurrentPlayer().getCriticalDiceDescription() + "\n\nEscolha: ";

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
            }
        }

        //Main.OutputStream.println(GSC.getCurrentPlayer().getUnlockedDiceDescription());
        GSC.RelayAction(ActionString);


    }
}
