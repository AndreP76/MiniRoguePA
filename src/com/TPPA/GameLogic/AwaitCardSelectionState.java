package com.TPPA.GameLogic;

import com.TPPA.GameLogic.Cards.BossMonsterCard;
import com.TPPA.GameLogic.Cards.CardBase;

/**
 * Created by andre on 4/5/17.
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
            for (int i = 1; i < GSC.getMaxCardsInStage(); ++i)
                Rooms[GSC.getMaxStagesInRoom() - 1][i] = null;

            GSC.setRoomStages(Rooms);
        }
    }

    @Override
    public Action[] GetActions() {
        Action DrawAction = new Action(InternalCommandsDictionary.DrawCommand, "Draw a stage card");
        Action[] AvAc = new Action[1];
        AvAc[0] = DrawAction;
        return AvAc;
    }


    @Override
    public IState Action(String ActionString) {
        GameStateController GSC = GameStateController.getCurrentController();

        String[] SStr = ActionString.split(" ");
        if (SStr[0].equals(InternalCommandsDictionary.DrawCommand)) {
            int CardIndex = Integer.parseInt(SStr[SStr.length - 1]);
            if (GSC.getRoomStages()[GSC.getCurrentStageInRoom()][CardIndex - 1] == null) {
                Main.ErrorStream.println("User selected empty card index. Ignoring.");
                return this;
            } else {
                CardBase RoomCard = GSC.getRoomStages()[GSC.getCurrentRoom()][CardIndex - 1];
                Main.ErrorStream.println("User drew " + RoomCard);
                GSC.setCurrentStageInRoom(GSC.getCurrentStageInRoom() + 1);
                return RoomCard.Effect();
            }
        } else {
            Main.ErrorStream.println("Unexpected command on AwaitCardSelectionStation : " + ActionString);
            return this;
        }
    }
}
