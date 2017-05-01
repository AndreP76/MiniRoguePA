package com.TPPA.TextUI;

import com.TPPA.GameLogic.Action;
import com.TPPA.GameLogic.Cards.CardBase;
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
        Text += "\n\n\n";

        Action[] AvailableActions = GSC.getCurrentGameState().GetActions();
        /*for (Action a : AvailableActions) {
            Text += "\n" + a.getActionString() + " ==> " + a.getDescriptionString();
        }*/
        CardBase[][] CardsInRoom = GSC.getRoomStages();
        int max = -1;
        String TAppend = "";
        if (GSC.getCurrentStageInRoom() % 2 == 0) {//even stage
            max = GSC.getCardsInEvenStage();
            TAppend = "1";
        } else {
            max = GSC.getMaxCardsInStage();
            TAppend = "1/2";
        }
        for (int i = 0; i < max; ++i) {
            Text += CardsInRoom[GSC.getCurrentStageInRoom()][i].toString();
        }
        Text += "\n\n\nEscolha (" + TAppend + "): ";

        Main.OutputStream.println(Text);
        String UserCommand = TextDrawHelper.InputScanner.nextLine();
        GSC.RelayAction(AvailableActions[0].getActionString() + " " + UserCommand);
    }

}
