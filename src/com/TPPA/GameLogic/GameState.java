package com.TPPA.GameLogic;

import com.TPPA.GameLogic.Cards.TreasureCard;
import com.TPPA.GameLogic.Spells.SpellBase;

import java.io.Serializable;

/**
 * Created by andre on 4/5/17.
 */
public abstract class GameState implements IState, Serializable {
    abstract public Action[] GetActions();
    @Override
    public Boolean CanDrawCard() {
        return null;
    }

    @Override
    public Boolean CanDefend() {
        return null;
    }

    @Override
    public Boolean CanUseFeat() {
        return null;
    }

    @Override
    public Boolean CanLose() {
        return null;
    }

    @Override
    public Boolean CanRest() {
        return null;
    }

    @Override
    public Boolean CanRollDice() {
        return null;
    }

    @Override
    public Boolean CanUseSpell() {
        return null;
    }

    @Override
    public Boolean CanGoToStart() {
        return null;
    }

    @Override
    public Boolean CanTrade() {
        return null;
    }

    @Override
    public Boolean CanReRollDice() {
        for (Dice currentDie : GameStateController.getCurrentController().getCurrentPlayer().getUnlockedDice()) {
            if (currentDie.getLastRoll() == 6)
                return true;
        }
        return false;
    }


    @Override
    public IState ToDrawPhase() {
        return null;
    }

    @Override
    public IState ToDefensePhase() {
        return null;
    }

    @Override
    public IState ToFeatPhase() {
        return null;
    }

    @Override
    public IState ToGameOver() {
        return null;
    }

    @Override
    public IState ToRestPhase() {
        return null;
    }

    @Override
    public IState ToRollPhase() {
        return null;
    }

    @Override
    public IState ToSpellPhase() {
        return null;
    }

    @Override
    public IState ToStart() {
        return null;
    }

    @Override
    public IState ToTradePhase() {
        return null;
    }

    @Override
    public IState Action(String ActionString) {
        Main.ErrorStream.println("Unspecialized action called!");
        Main.ErrorStream.println("\t" + this.getClass() + "\n\t" + ActionString);
        return this;
    }
    @Override
    public IState AttackMonster() {
        return this;
    }

    @Override
    public IState UseSpell(SpellBase spellToUse) {
        return this;
    }

    @Override
    public IState OnDefeatingMonster() {
        GameStateController GSC = GameStateController.getCurrentController();

        if (GSC.getCurrentMonster().getBoss()) {
            int currentZone = GSC.getCurrentZone();
            if (currentZone == GSC.getMaxZones()) {
                GSC.MessageStack.push("==== Final Boss defeated! ====\nYou've won Og's Blood!\n");

                GSC.getCurrentPlayer().resetDice();
                return new StartState();
            } else {
                GSC.getCurrentPlayer().incGold(GSC.getCurrentMonster().getGoldReward());
                GSC.getCurrentPlayer().incXP(GSC.getCurrentMonster().getXPReward());
                GSC.setBattledInThisRoom(false);
                GSC.MessageStack.push("Congratulations! You have defeated " + GSC.getCurrentMonster().getName() + "\n");
                GSC.MessageStack.push("Your rewards: " + GSC.getCurrentMonster().getXPReward() + "XP and " + GSC.getCurrentMonster().getGoldReward() + " Gold\n");

                GSC.setCurrentRoom(GSC.getCurrentRoom() + 1);
                GSC.setCurrentStageInRoom(1); // Qual é a sala inicial???
                GSC.setRoomStages(null);//para depois ser recriada pelo AwaitCardSelectionState
                GSC.getCurrentPlayer().resetDice();
                return (new TreasureCard(Deck.TreasureCardID)).Effect();
            }
        } else {

            GSC.getCurrentPlayer().incXP(GSC.getCurrentMonster().getXPReward());
            GSC.setBattledInThisRoom(true); // para calcular o tesouro depois
            GSC.MessageStack.push("Congratulations! You have defeated " + GSC.getCurrentMonster().getName() + "\n");
            GSC.MessageStack.push("Your rewards: " + GSC.getCurrentMonster().getXPReward() + "XP\n");
            GSC.setCurrentStageInRoom(GSC.getCurrentStageInRoom() + 1);

            GSC.getCurrentPlayer().resetDice();
            return new AwaitCardSelectionState();
        }
    }

    @Override
    public IState DefendFromMonster() {
        return this;
    }
}
