package com.TPPA.GameLogic.Cards;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.IState;
import com.TPPA.GameLogic.Main;
import com.TPPA.GameLogic.RollPhase;

/**
 * Created by andre on 4/19/17.
 */
public class BossMonsterCard extends MonsterCard {
    public BossMonsterCard(String ID) {
        super(ID);
    }

    @Override
    public IState Effect() {
        Main.ErrorStream.println("BossMonsterCard effect called!");
        //set the monster for battle
        return new RollPhase();
    }
}
