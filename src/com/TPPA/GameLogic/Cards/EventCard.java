package com.TPPA.GameLogic.Cards;

import com.TPPA.GameLogic.*;

/**
 * Created by andre on 4/19/17.
 */
public class EventCard extends CardBase {
    public EventCard(String ID) {
        super(ID, "Event Card");
    }

    @Override
    public IState Effect() {
        Main.ErrorStream.println("EventCard effect called");
        Dice d = new Dice();
        switch (d.Roll()) {
            case 1: {
                GameStateController.getCurrentController().getCurrentPlayer().incFood(1);
                GameStateController.getCurrentController().MessageStack.push("Found Food");
                Main.ErrorStream.println("Found ration");
                break;
            }
            case 2: {
                GameStateController.getCurrentController().getCurrentPlayer().incHP(1);
                GameStateController.getCurrentController().MessageStack.push("Found Health Potion (+1HP)");
                Main.ErrorStream.println("Found Health Potion");
                break;
            }
            case 3: {
                GameStateController.getCurrentController().getCurrentPlayer().incGold(1);
                GameStateController.getCurrentController().MessageStack.push("Found Loot (+1Gold)");
                Main.ErrorStream.println("Found Loot");
                break;
            }
            case 4: {
                GameStateController.getCurrentController().getCurrentPlayer().incXP(2);
                GameStateController.getCurrentController().MessageStack.push("Found Whetstone (+2XP)");
                Main.ErrorStream.println("Found Whetstone");
                break;
            }
            case 5: {
                GameStateController.getCurrentController().getCurrentPlayer().incArmor(1);
                GameStateController.getCurrentController().MessageStack.push("Found Armor");
                Main.ErrorStream.println("Found Armor");
                break;
            }
            case 6: {
                Main.ErrorStream.println("Found Monster");
                GameStateController.getCurrentController().MessageStack.push("Found Monster");
                return (new MonsterCard(Deck.MonsterCardID)).Effect(GameStateController.getCurrentController().getTrueRoom() + d.Roll(), 2, 0, GameStateController.getCurrentController().getCurrentZone(), false);
            }

        }
        return new AwaitCardSelectionState();
    }
}
