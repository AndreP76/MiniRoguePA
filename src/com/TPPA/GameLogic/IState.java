package com.TPPA.GameLogic;

import com.TPPA.GameLogic.Internals.Action;
import com.TPPA.GameLogic.Spells.SpellBase;

/**
 * Created by andre on 4/5/17.
 */
public interface IState {
    Boolean CanDrawCard();

    Boolean CanDefend();

    Boolean CanUseFeat();

    Boolean CanLose();

    Boolean CanRest();

    Boolean CanRollDice();

    Boolean CanUseSpell();

    Boolean CanGoToStart();

    Boolean CanTrade();

    Boolean CanReRollDice();

    IState ToDrawPhase();

    IState ToDefensePhase();

    IState ToFeatPhase();

    IState ToGameOver();

    IState ToRestPhase();

    IState ToRollPhase();

    IState ToSpellPhase();

    IState ToStart();

    IState ToTradePhase();

    IState Action(String ActionString);

    Action[] GetActions();

    IState UseSpell(SpellBase spellToUse);

    IState AttackMonster();

    IState OnDefeatingMonster();

    IState DefendFromMonster();

    GameStateController getCurrentController();

    void setCurrentController(GameStateController currentController);
}
