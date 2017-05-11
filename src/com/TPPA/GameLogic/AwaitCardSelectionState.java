package com.TPPA.GameLogic;

import com.TPPA.GameLogic.Cards.BossMonsterCard;
import com.TPPA.GameLogic.Cards.CardBase;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;

/**
 * Created by andre on 4/5/17.
 * Time to d-d-d-duel
 */

public class AwaitCardSelectionState extends GameState {
    public AwaitCardSelectionState() {
        super();
        GameStateController GSC = GameStateController.getCurrentController();
        if (GSC.getRoomStages() == null) {
            Deck CurrentDeck = GSC.getCurrentDeck();
            CurrentDeck.CollectionsShuffle();
            int DeckIndex = 0;
            CardBase[][] Rooms = new CardBase[GSC.getMaxStagesInRoom()][GSC.getMaxCardsInStage()];

            for (int i = 0; i < GSC.getMaxStagesInRoom() - 1; ++i) {
                if (i % 2 == 0) {//even rooms,  one card
                    int j = 0;
                    for (; j < GSC.getCardsInEvenStage(); ++j) {
                        Rooms[i][j] = CurrentDeck.getCard(DeckIndex);
                        Main.ErrorStream.println("Added new card to stage " + i + " : " + Rooms[i][j]);
                        DeckIndex++;
                    }
                    for (; j < GSC.getMaxCardsInStage(); ++j) {
                        Main.ErrorStream.println("Added null card to stage " + i);
                        Rooms[i][j] = null;
                    }
                } else {//odd rooms, max cards
                    for (int j = 0; j < GSC.getMaxCardsInStage(); ++j) {
                        Rooms[i][j] = CurrentDeck.getCard(DeckIndex);
                        Main.ErrorStream.println("Added new card to stage " + i + " : " + Rooms[i][j]);
                        DeckIndex++;
                    }
                }
            }
            Rooms[GSC.getMaxStagesInRoom() - 1][0] = new BossMonsterCard(Deck.BossMonsterCardID);
            Main.ErrorStream.println("Added new card to stage " + (GSC.getMaxStagesInRoom() - 1) + " : " + Rooms[GSC.getMaxStagesInRoom() - 1][0]);
            for (int i = 1; i < GSC.getMaxCardsInStage(); ++i)
                Rooms[GSC.getMaxStagesInRoom() - 1][i] = null;

            GSC.setRoomStages(Rooms);
        }
    }

    @Override
    public Action[] GetActions() {
        Action[] AvAc = new Action[3];
        AvAc[0] = new Action(InternalCommandsDictionary.DrawCommand, "Draw a stage card");
        AvAc[1] = new Action(InternalCommandsDictionary.SaveCommand, "Save a game ");
        AvAc[2] = new Action(InternalCommandsDictionary.QuitCommand, "Exit to main menu");
        return AvAc;
    }


    @Override
    public IState Action(String ActionString) {
        GameStateController GSC = GameStateController.getCurrentController();

        String[] SStr = ActionString.split(" ");
        if (SStr[0].equals(InternalCommandsDictionary.DrawCommand)) {
            int CardIndex = -1;
            try {
                CardIndex = Integer.parseInt(SStr[SStr.length - 1]);
            } catch (InputMismatchException Ime) {
                Main.ErrorStream.println("Error parsing int from string\n\tString : " + ActionString + "\n\t" + Ime.fillInStackTrace());
            }
            if (GSC.getRoomStages()[GSC.getCurrentStageInRoom()][CardIndex - 1] == null) {
                Main.ErrorStream.println("User selected empty card index. Ignoring.");
                return this;
            } else {
                CardBase RoomCard = GSC.getRoomStages()[GSC.getCurrentStageInRoom()][CardIndex - 1];
                Main.ErrorStream.println("User drew " + RoomCard);
                GSC.setCurrentStageInRoom(GSC.getCurrentStageInRoom() + 1);
                return RoomCard.Effect();
            }
        } else if (SStr[0].equals(InternalCommandsDictionary.QuitCommand)) {
            return new StartState();
        } else if (SStr[0].equals(InternalCommandsDictionary.SaveCommand)) {
            try {
                String PathStr = "";
                if (SStr[SStr.length - 1].equals(SStr[0])) {
                    PathStr = Utils.GenerateDateTimeStringNow("./Savegame") + ".savegamedat";
                } else {
                    if (PathStr.contains(".savegamedat"))
                        PathStr = SStr[SStr.length - 1];
                    else
                        PathStr = SStr[SStr.length - 1] + ".savegamedat";
                }
                FileOutputStream FSO = new FileOutputStream(PathStr);
                ObjectOutputStream OOS = new ObjectOutputStream(FSO);
                OOS.writeObject(GameStateController.getCurrentController());
                return this;
            } catch (IOException IOEX) {
                Main.ErrorStream.println("Error serializing object\n\tMessage : " + IOEX.fillInStackTrace());
            }
            return this;
        } else {
            Main.ErrorStream.println("Unexpected command on AwaitCardSelectionStation : " + ActionString);
            return this;
        }
    }
}
