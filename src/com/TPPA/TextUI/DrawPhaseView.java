package com.TPPA.TextUI;

import com.TPPA.GameLogic.Action;
import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Main;

import java.util.EmptyStackException;

/**
 * Created by andre on 4/12/17.
 */
public class DrawPhaseView extends StateView {
    @Override
    public void Render() {
        TextDrawHelper.ClearScreen();
        GameStateController GSC = GameStateController.getCurrentController();
        String Text = "";

        while (!GSC.MessageStack.empty())
            Text += GSC.MessageStack.pop();

        Text += "Nivel : " + GSC.getTrueRoom();
        int Zone = GSC.getCurrentZone();
        switch (Zone) {
            case 1: {
                Text += "\nBlack Sewers";
                break;
            }
            case 2: {
                Text += "\nPoisonous Dungeon";
                break;
            }
            case 3: {
                Text += "\nUndead Catacombs";
                break;
            }
            case 4: {
                Text += "\nFlaming Underworld";
                break;
            }
            case 5: {
                Text += "\nSunken Keep of Og";
                break;
            }
        }

        Action[] AvailableActions = GSC.getCurrentGameState().GetActions();
        for (Action a : AvailableActions) {
            Text += "\n" + a.getActionString() + " ==> " + a.getDescriptionString();
        }

        Text += "\n\n\nEscolha : ";

        Main.OutputStream.println(Text);
        String UserCommand = TextDrawHelper.InputScanner.nextLine();
        GSC.RelayAction(UserCommand);

    }

}
