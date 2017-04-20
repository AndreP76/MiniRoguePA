package com.TPPA.GameLogic.Cards;

import com.TPPA.GameLogic.*;

/**
 * Created by andre on 4/19/17.
 */
public class EventCard extends CardBase {
    public EventCard(String ID) {
        super(ID);
    }

    @Override
    public IState Effect() {
        Main.ErrorStream.println("EventCard effect called");
        Dice d = new Dice();
        switch (d.Roll()) {
            case 1: {
                GameStateController.getCurrentController().getCurrentPlayer().incFood(1);
                Main.ErrorStream.println("Found ration");
                break;
            }
            case 2: {
                GameStateController.getCurrentController().getCurrentPlayer().incHP(1);
                Main.ErrorStream.println("Found Health Potion");
                break;
            }
            case 3: {
                GameStateController.getCurrentController().getCurrentPlayer().incGold(1);
                Main.ErrorStream.println("Found Loot");
                break;
            }
            case 4: {
                GameStateController.getCurrentController().getCurrentPlayer().incXP(2);
                Main.ErrorStream.println("Found Whetstone");
                break;
            }
            case 5: {
                GameStateController.getCurrentController().getCurrentPlayer().incArmor(1);
                Main.ErrorStream.println("Found Armor");
                break;
            }
            case 6: {
                Main.ErrorStream.println("Found Monster");
                //Generate a battle
                return new RollPhase();
            }

        }
        return new AwaitCardSelectionState();
    }
}
