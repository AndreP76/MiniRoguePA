package com.TPPA.GameLogic;

/**
 * Created by andre on 4/5/17.
 */
public class StartState extends GameState {
    StartState(){

    }

    @Override
    public String GetActionsString() {
        return null;
    }

    @Override
    public String GetActionsDescription() {
        return null;
    }

    @Override
    public Boolean CanDrawCard() {
        return true;
    }

    @Override
    public IState ToStart() {
        return this;
    }

    @Override
    public IState ToDrawPhase() {
        return new AwaitCardSelectionState();
    }
}
