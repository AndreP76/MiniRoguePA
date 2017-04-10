package com.TPPA.GameLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

/**
 * Created by andre on 4/10/17.
 */
public class Deck {
    private Random SR = new Random();
    public final static String TrapCardID = "TRAPCARD";
    public final static String MonsterCardID = "MONSTERCARD";
    public final static String EventCardID = "EVENTCARD";
    public final static String TreasureCardID = "TREASURECARD";
    public final static String MerchantCardID = "MERCHANTCARD";
    public final static String RestCardID = "RESTCARD";

    public final static String BossMonsterCardID = "BOSSCARD";

    private ArrayList<String> Cards;
    Deck(Deck original){
        Cards = new ArrayList<>();
        Cards.addAll(original.getCards());
    }
    Deck(int DeckSize){
        Cards = new ArrayList<>();
        SR.setSeed(System.nanoTime());
        for(int i = 0; i < DeckSize;++i){
            int R = SR.nextInt(6);
            switch (R){
                case 0: Cards.add(TrapCardID);break;
                case 1: Cards.add(MonsterCardID);break;
                case 2: Cards.add(EventCardID);break;
                case 3: Cards.add(TreasureCardID);break;
                case 4: Cards.add(MerchantCardID);break;
                case 5: Cards.add(RestCardID);break;
            }
        }
    }

    public int Shuffle(){
        int flips = 0;
        while (flips < Cards.size()){//guarantee we shuffle once
            int CardIndex = SR.nextInt(Cards.size());
            String CardID = Cards.get(CardIndex);
            int CardIndex2 = SR.nextInt(Cards.size());
            while (CardIndex2 == CardIndex)
                CardIndex2 = SR.nextInt(Cards.size());

            Cards.set(CardIndex,Cards.get(CardIndex2));
            Cards.set(CardIndex2,CardID);
            flips++;
        }
        return flips;
    }

    public void CollectionsShuffle(){
        Collections.shuffle(Cards,SR);
    }

    public void DebugPrintDeck(){
        for(String s: Cards)
            System.out.println(s);
    }

    public ArrayList<String> getCards() {
        return Cards;
    }
}
