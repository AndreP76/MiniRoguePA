package com.TPPA.GameLogic;

import com.TPPA.GameLogic.Cards.BossMonsterCard;
import com.TPPA.GameLogic.Cards.CardBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by andre on 4/5/17.
 */
public class AwaitCardSelectionState extends GameState {

    @Override
    public Action[] GetActions() {
        //git is stupid
        Action DrawAction = new Action(InternalCommandsDictionary.DrawCommand, "Draw a stage card");
        return new Action[0];
    }


    @Override
    public IState Action(String ActionString) {
        GameStateController GSC = GameStateController.getCurrentController();
        if (GSC.getRoomStages() == null) {
            Deck CurrentDeck = GSC.getCurrentDeck();
            CurrentDeck.CollectionsShuffle();
            int DeckIndex = 0;
            CardBase[][] Rooms = new CardBase[GSC.getMaxStagesInRoom()][GSC.getMaxCardsInStage()];

            for (int i = 0; i < GSC.getMaxStagesInRoom(); ++i) {
                if (i % 2 == 0) {//even rooms,  one card
                    int j = 0;
                    for (; j < GSC.getCardsInEvenStage(); ++j) {
                        Rooms[i][j] = CurrentDeck.getCard(DeckIndex);
                        Main.ErrorStream.println("Added new card to stage " + i + " : " + Rooms[i][j]);
                    }
                    for (; j < GSC.getMaxCardsInStage(); ++j) {
                        Main.ErrorStream.println("Added null card to stage " + i);
                        Rooms[i][j] = null;
                    }
                } else {//odd rooms, max cards
                    for (int j = 0; j < GSC.getMaxCardsInStage(); ++i) {
                        Main.ErrorStream.println("Added new card to stage " + i + " : " + Rooms[i][j]);
                        Rooms[i][j] = CurrentDeck.getCard(j);
                    }
                }
            }
            Rooms[GSC.getMaxStagesInRoom() - 1][0] = new BossMonsterCard(Deck.BossMonsterCardID);
            for (int i = 1; i < GSC.getMaxCardsInStage(); ++i)
                Rooms[GSC.getMaxStagesInRoom()][i] = null;

            GSC.setRoomStages(Rooms);
        }

        String[] SStr = ActionString.split(" ");
        if (SStr[0].equals(InternalCommandsDictionary.DrawCommand)) {
            int CardIndex = Integer.parseInt(SStr[SStr.length - 1]);
            if (GSC.getRoomStages()[GSC.getCurrentRoom()][CardIndex] == null) {
                Main.ErrorStream.println("User selected empty card index. Ignoring.");
                return this;
            } else {
                CardBase RoomCard = GSC.getRoomStages()[GSC.getCurrentRoom()][CardIndex];
                Main.ErrorStream.println("User drew " + RoomCard);
                return RoomCard.Effect();
            }
        } else {
            Main.ErrorStream.println("Unexpected command on AwaitCardSelectionStation : " + ActionString);
            return this;
        }
    }
}
