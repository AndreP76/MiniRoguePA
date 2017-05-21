package com.TPPA.GameLogic.Cards;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.IState;
import com.TPPA.GameLogic.Internals.Deck;
import com.TPPA.GameLogic.Internals.Dice;
import com.TPPA.GameLogic.States.AwaitCardSelectionState;
import com.TPPA.Main;

/**
 * Created by andre on 4/19/17.
 */
public class EventCard extends CardBase {
    public EventCard(GameStateController GSC, String ID) {
        super(GSC, ID, "Event Card");
    }

    @Override
    public IState Effect() {
        Main.ErrorStream.println("EventCard effect called");
        Dice d = new Dice();
        switch (d.Roll()) {
            case 1: {
                GSC.getCurrentPlayer().incFood(1);
                GSC.MessageStack.push("Found Food");
                Main.ErrorStream.println("Found ration");
                break;
            }
            case 2: {
                GSC.getCurrentPlayer().incHP(1);
                GSC.MessageStack.push("Found Health Potion (+1HP)");
                Main.ErrorStream.println("Found Health Potion");
                break;
            }
            case 3: {
                GSC.getCurrentPlayer().incGold(1);
                GSC.MessageStack.push("Found Loot (+1Gold)");
                Main.ErrorStream.println("Found Loot");
                break;
            }
            case 4: {
                GSC.getCurrentPlayer().incXP(2);
                GSC.MessageStack.push("Found Whetstone (+2XP)");
                Main.ErrorStream.println("Found Whetstone");
                break;
            }
            case 5: {
                GSC.getCurrentPlayer().incArmor(1);
                GSC.MessageStack.push("Found Armor");
                Main.ErrorStream.println("Found Armor");
                break;
            }
            case 6: {
                Main.ErrorStream.println("Found Monster");
                GSC.MessageStack.push("Found Monster");
                return (new MonsterCard(GSC, Deck.MonsterCardID)).Effect(GSC.getTrueRoom() + d.Roll(), 2, 0, GSC.getCurrentZone(), false);
            }

        }
        return new AwaitCardSelectionState(GSC);
    }
}
