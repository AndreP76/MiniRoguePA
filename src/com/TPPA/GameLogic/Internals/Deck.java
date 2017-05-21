package com.TPPA.GameLogic.Internals;

import com.TPPA.GameLogic.Cards.*;
import com.TPPA.GameLogic.GameStateController;
import com.TPPA.Main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by andre on 4/10/17.
 */
public class Deck implements Serializable {
    public final static String TrapCardID = "TRAPCARD";
    public final static String MonsterCardID = "MONSTERCARD";
    public final static String EventCardID = "EVENTCARD";
    public final static String TreasureCardID = "TREASURECARD";
    public final static String MerchantCardID = "MERCHANTCARD";
    public final static String RestCardID = "RESTCARD";
    public final static String BossMonsterCardID = "BOSSCARD";
    private GameStateController GSC;
    private Random SR = new Random();
    private int DeckLength;
    private ArrayList<CardBase> Cards;

    public Deck(GameStateController GSC, int DeckSize) {
        this.GSC = GSC;
        this.DeckLength = DeckSize;
        Cards = new ArrayList<>();
        SR.setSeed(System.nanoTime());
        Cards.add(new TrapCard(GSC, TrapCardID));
        Cards.add(new MonsterCard(GSC, MonsterCardID));
        Cards.add(new EventCard(GSC, EventCardID));
        Cards.add(new TreasureCard(GSC, TreasureCardID));
        Cards.add(new MerchantCard(GSC, MerchantCardID));
        Cards.add(new RestCard(GSC, RestCardID));
        this.CollectionsShuffle();
        this.DebugPrintDeck();
    }

    public CardBase getCard(int index) throws IndexOutOfBoundsException {
        return Cards.get(index);
    }

    public int getDeckLength() {
        return DeckLength;
    }

    public void CollectionsShuffle(){
        Collections.shuffle(Cards);
    }

    public void DebugPrintDeck(){
        Main.ErrorStream.println("Deck : ");
        for (CardBase s : Cards)
            Main.ErrorStream.println("\t" + s);
    }

    public ArrayList<CardBase> getCards() {
        return Cards;
    }
}
