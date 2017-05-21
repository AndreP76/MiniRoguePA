package com.TPPA.GameLogic.States;

import com.TPPA.GameLogic.Cards.TreasureCard;
import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.IState;
import com.TPPA.GameLogic.Internals.Action;
import com.TPPA.GameLogic.Internals.Deck;
import com.TPPA.GameLogic.Internals.Dice;
import com.TPPA.GameLogic.Spells.SpellBase;
import com.TPPA.Main;

import java.io.Serializable;

/**
 * Created by andre on 4/5/17.
 */
public abstract class GameState implements IState, Serializable {
    GameStateController currentController;

    public GameState(GameStateController GSC) {
        currentController = GSC;
    }

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

    protected GameStateController getCurrentController() {
        return currentController;
    }

    public void setCurrentController(GameStateController currentController) {
        this.currentController = currentController;
    }

    @Override
    public Boolean CanReRollDice() {
        for (Dice currentDie : getCurrentController().getCurrentPlayer().getUnlockedDice()) {
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
        GameStateController GSC = getCurrentController();

        if (GSC.getCurrentMonster().getBoss()) {

            if (GSC.getTrueRoom() >= 14) {//Final room
                GSC.MessageStack.push("==== Final Boss defeated! ====\nYou've won Og's Blood!\n");

                GSC.getCurrentPlayer().resetDice();
                return new StartState(getCurrentController());
            } else {
                GSC.getCurrentPlayer().incGold(GSC.getCurrentMonster().getGoldReward());
                GSC.getCurrentPlayer().incXP(GSC.getCurrentMonster().getXPReward());
                GSC.setBattledInThisRoom(false);
                GSC.MessageStack.push("Congratulations! You have defeated " + GSC.getCurrentMonster().getName() + "\n");
                GSC.MessageStack.push("Your rewards: " + GSC.getCurrentMonster().getXPReward() + "XP and " + GSC.getCurrentMonster().getGoldReward() + " Gold\n");

                int CurrentRoom = GSC.getCurrentRoom();
                GSC.setCurrentStageInRoom(0);
                if ((CurrentRoom == 2 && GSC.getCurrentZone() == 1) || (CurrentRoom == 2 && GSC.getCurrentZone() == 2) || (CurrentRoom == 3 && GSC.getCurrentZone() == 3) || (CurrentRoom == 3 && GSC.getCurrentZone() == 4) || (CurrentRoom == 4 && GSC.getCurrentZone() == 5)) {
                    GSC.setCurrentRoom(1);
                    GSC.setCurrentZone(GSC.getCurrentZone() + 1);
                } else {
                    GSC.setCurrentRoom(GSC.getCurrentRoom() + 1);
                }
                GSC.setRoomStages(null);//para depois ser recriada pelo AwaitCardSelectionState
                GSC.getCurrentPlayer().resetDice();
                return (new TreasureCard(GSC, Deck.TreasureCardID)).Effect();
            }
        } else {

            GSC.getCurrentPlayer().incXP(GSC.getCurrentMonster().getXPReward());
            GSC.setBattledInThisRoom(true); // para calcular o tesouro depois
            GSC.MessageStack.push("Congratulations! You have defeated " + GSC.getCurrentMonster().getName() + "\n");
            GSC.MessageStack.push("Your rewards: " + GSC.getCurrentMonster().getXPReward() + "XP\n");
            //GSC.setCurrentStageInRoom(GSC.getCurrentStageInRoom() + 1); Feito pela vista automaticamente

            GSC.getCurrentPlayer().resetDice();
            return new AwaitCardSelectionState(getCurrentController());
        }
    }

    @Override
    public IState DefendFromMonster() {
        return this;
    }
}
