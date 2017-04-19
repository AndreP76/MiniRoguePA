package com.TPPA.TextUI;

import com.TPPA.GameLogic.*;

import java.util.Observable;

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
        GSC.RelayAction(userCommand);
    }

    @Override
    public void update(Observable observable, Object o) {//chamado quando muda o estado
        super.update(observable,o);
    }
}
