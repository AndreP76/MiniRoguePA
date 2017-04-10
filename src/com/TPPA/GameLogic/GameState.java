package com.TPPA.GameLogic;

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
        //LOG THIS
        //THIS SHOULD NOT HAPPEN
        return this;
    }
}
