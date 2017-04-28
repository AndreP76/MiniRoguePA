package com.TPPA.GameLogic;

import com.TPPA.GameLogic.Cards.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

/**
 * Created by andre on 4/10/17.
 */
public class Deck {
    public final static String TrapCardID = "TRAPCARD";
    public final static String MonsterCardID = "MONSTERCARD";
    public final static String EventCardID = "EVENTCARD";
    public final static String TreasureCardID = "TREASURECARD";
    public final static String MerchantCardID = "MERCHANTCARD";
    public final static String RestCardID = "RESTCARD";
    public final static String BossMonsterCardID = "BOSSCARD";
    private Random SR = new Random();
    private int DeckLength;
    private ArrayList<CardBase> Cards;
    Deck(Deck original){
        this.DeckLength = original.getDeckLength();
        Cards = new ArrayList<>();
        Cards.addAll(original.getCards());
    }
    Deck(int DeckSize){
        this.DeckLength = DeckSize;
        Cards = new ArrayList<>();
        SR.setSeed(System.nanoTime());
        /*for(int i = 0; i < DeckSize;++i){
            int R = SR.nextInt(6);
            switch (R){
                case 0:
                    Cards.add(new TrapCard(TrapCardID));
                    break;
                case 1:
                    Cards.add(new MonsterCard(MonsterCardID));
                    break;
                case 2:
                    Cards.add(new EventCard(EventCardID));
                    break;
                case 3:
                    Cards.add(new TreasureCard(TreasureCardID));
                    break;
                case 4:
                    Cards.add(new MerchantCard(MerchantCardID));
                    break;
                case 5:
                    Cards.add(new RestCard(RestCardID));
                    break;
            }
        }*/
        Cards.add(new TrapCard(TrapCardID));
        Cards.add(new MonsterCard(MonsterCardID));
        Cards.add(new EventCard(EventCardID));
        Cards.add(new TreasureCard(TreasureCardID));
        Cards.add(new MerchantCard(MerchantCardID));
        Cards.add(new RestCard(RestCardID));
        this.CollectionsShuffle();
    }

    public CardBase getCard(int index) throws IndexOutOfBoundsException {
        return Cards.get(index);
    }

    public int getDeckLength() {
        return DeckLength;
    }

    public void CollectionsShuffle(){
        Collections.shuffle(Cards,SR);
    }

    public void DebugPrintDeck(){
        for (CardBase s : Cards)
            System.out.println(s);
    }

    public ArrayList<CardBase> getCards() {
        return Cards;
    }
}
