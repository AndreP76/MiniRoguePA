package com.TPPA.TextUI;

import com.TPPA.GameLogic.Action;
import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Main;

import java.io.File;

/**
 * Created by andre on 4/11/17.
 */
public class StartStateView extends StateView {
    StartStateView(){
        super();//regista este observador no GameStateController
    }

    @Override
    public void Render() {
        GameStateController GSC = GameStateController.getCurrentController();
        TextDrawHelper.ClearScreen();
        Action[] ActionsInState = GSC.getCurrentGameState().GetActions();
        Main.OutputStream.println("Dificuldade : " + GSC.getGameDifficulty().name() + "\n Area : " + GSC.getCurrentZone());
        for (Action ac : ActionsInState) {
            Main.OutputStream.println(ac.getActionString() + " ==> " + ac.getDescriptionString());
        }
        Main.OutputStream.print("\n\nEscolha : ");
        String userCommand = TextDrawHelper.InputScanner.nextLine();
        if (userCommand.equals(ActionsInState[3].getActionString())) {
            TextDrawHelper.ClearScreen();
            File here = new File("./");
            File[] filesHere = here.listFiles((file, s) -> s.toLowerCase().endsWith(".savegamedat"));
            if (filesHere != null && filesHere.length > 0) {
                Main.OutputStream.println("Escolha o ficheiro : ");
                for (int i = 0; i < filesHere.length; ++i) {
                    Main.OutputStream.println("\t" + (i + 1) + ") " + filesHere[i].getName());
                }

                int selected = -1;
                while (selected < 1 || selected > filesHere.length) {
                    while (!TextDrawHelper.InputScanner.hasNextInt()) ;
                    selected = TextDrawHelper.InputScanner.nextInt();
                }
                userCommand += " " + filesHere[selected - 1].getName();
            } else {
                Main.OutputStream.println("No files available in here!");
                TextDrawHelper.InputScanner.nextLine();
                this.Render();
            }
        }
        GSC.RelayAction(userCommand);
    }
}
