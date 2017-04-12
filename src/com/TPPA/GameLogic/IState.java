package com.TPPA.GameLogic;

/**
 * Created by andre on 4/5/17.
 */
public interface IState {
    public Boolean CanDrawCard();
    public Boolean CanDefend();
    public Boolean CanUseFeat();
    public Boolean CanLose();
    public Boolean CanRest();
    public Boolean CanRollDice();
    public Boolean CanUseSpell();
    public Boolean CanGoToStart();
    public Boolean CanTrade();

    public IState ToDrawPhase();
    public IState ToDefensePhase();
    public IState ToFeatPhase();
    public IState ToGameOver();
    public IState ToRestPhase();
    public IState ToRollPhase();
    public IState ToSpellPhase();
    public IState ToStart();
    public IState ToTradePhase();

    public IState Action(String ActionString);

    public Action[] GetActions();
}
