package com.TPPA.GameLogic.Cards;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.IState;
import com.TPPA.GameLogic.Main;
import com.TPPA.GameLogic.RollPhase;

/**
 * Created by andre on 4/19/17.
 */
public class MonsterCard extends CardBase {
    public MonsterCard(String ID) {
        super(ID);
    }

    @Override
    public IState Effect() {//Start a battle with a monster
        Main.ErrorStream.println("MonsterCard effect called!");
        //Set the monster for battle
        return new RollPhase();
    }
}
