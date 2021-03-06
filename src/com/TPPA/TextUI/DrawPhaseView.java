package com.TPPA.TextUI;

import com.TPPA.GameLogic.Action;
import com.TPPA.GameLogic.Cards.CardBase;
import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Main;

/**
 * Created by andre on 4/12/17.
 */
public class DrawPhaseView extends StateView {
    @Override
    public void Render() {
        super.Render();
        String Text = "";

        GameStateController GSC = GameStateController.getCurrentController();

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
            Text += CardsInRoom[GSC.getCurrentStageInRoom()][i].toString() + "\n";
        }
        TAppend += "/S(ave)/Q(uit)";
        Text += "\n\n\nEscolha (" + TAppend + "): ";

        Main.OutputStream.println(Text);
        TextDrawHelper.InputScanner.reset();
        String UserCommand = TextDrawHelper.InputScanner.nextLine();
        while (UserCommand.isEmpty()) {
            UserCommand = TextDrawHelper.InputScanner.nextLine();
        }
            if (UserCommand.toLowerCase().equals("s") || UserCommand.toLowerCase().equals("save"))//Save
                GSC.RelayAction(AvailableActions[1].getActionString());
            else if (UserCommand.toLowerCase().equals("q") || UserCommand.toLowerCase().equals("quit"))//Quit
                GSC.RelayAction(AvailableActions[2].getActionString());
            else
                GSC.RelayAction(AvailableActions[0].getActionString() + " " + UserCommand);
    }

}
