package com.TPPA.GameLogic;

/**
 * Created by andre on 4/10/17.
 */
public class InternalCommandsDictionary { //Isto so guarda os comandos e as strings correspondentes. Nao deve ser instranciado
    public final static String SetDifficultyCommand = "SETDIFF";
    public final static String SetAreaCommand = "SETAREA";
    public final static String StartCommand = "GAMESTART";
    public final static String DrawCommand = "DRAWCARD";
    public final static String QuitCommand = "QUITGAME";

    /*public final static String TrapCardID = "TRAPCARD";
    public final static String MonsterCardID = "MONSTERCARD";
    public final static String BossMonsterCardID = "BOSSCARD";
    public final static String EventCardID = "EVENTCARD";
    public final static String TreasureCardID = "TREASURECARD";
    public final static String MerchantCardID = "MERCHANTCARD";
    public final static String RestCardID = "RESTCARD";*/
    //passou para a classe Deck

    /*public final static int CakewalkIndex = 0;
    public final static int EzPzIndex = 1;
    public final static int EasyIndex = 2;
    public final static int NormalIndex = 3;
    public final static int HardIndex = 4;
    public final static int VeryHardIndex = 5;
    public final static int InsaneIndex = 6;*/
    //Inutil. DifficultyLevelEnum.values()[i] faz o mesmo
}
