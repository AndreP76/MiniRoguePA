package com.TPPA.GameLogic.Internals;

/**
 * Created by andre on 4/10/17.
 * this saves commands for easy comparison
 */
public class InternalCommandsDictionary { //Isto so guarda os comandos e as strings correspondentes. Nao deve ser instanciado
    public final static String SetDifficultyCommand = "SETDIFF";
    public final static String SetAreaCommand = "SETAREA";
    public final static String StartCommand = "GAMESTART";
    public final static String DrawCommand = "DRAWCARD";
    public final static String QuitCommand = "QUITGAME";
    public final static String LoadCommand = "LOADGAME";
    public final static String SaveCommand = "SAVEGAME";

    //Comandos para RestingState
    public final static String ReinforceWeapon = "REINFORCE";
    public final static String SearchRation = "SEARCHRATION";
    public final static String Heal = "HEAL";

    //ID dos Spells
    public final static String FireSpellID = "FIRESPELL";
    public final static String IceSpellID = "ICESPELL";
    public final static String HealSpellID = "HEALSPELL";
    public final static String PoisonSpellID = "POISONSPELL";

    //Comandos para TradingState
    public final static String BuyRation = "BUYRATION";
    public final static String BuyHealthPotion = "BUYHEALTH";
    public final static String BuyBigHealthPotion = "BUYBIGHEALTH";
    public final static String BuyArmorPiece = "BUYARMOR";
    public final static String BuySpell = "BUYSPELL";
    public final static String SellArmorPiece = "SELLARMOR";
    public final static String SellSpell = "SELLSPELL";
    public final static String EndTradingState = "ENDTRADING";

    //Comandos para SpellPhase
    public final static String UseSpell = "USESPELL";
    public final static String EndSpellPhase = "ENDSPELL";

    //Comandos para RollPhase
    public final static String RollDice = "ROLLDICE";
    public final static String ReRollDice = "REROLLDICE";
    public final static String EndRollPhase = "ENDROLLPHASE";

    //Comandos para FeatPhase
    public final static String UseFeatLosingHP = "USEFEATHP";
    public final static String UseFeatLosingXP = "USEFEATXP";
    public final static String EndFeatPhase = "ENDFEATPHASE";

    //Comandos para GameOverPhase
    public final static String Skip = "GAMEOVERSKIP";

    /*public final static int CakewalkIndex = 0;
    public final static int EzPzIndex = 1;
    public final static int EasyIndex = 2;
    public final static int NormalIndex = 3;
    public final static int HardIndex = 4;
    public final static int VeryHardIndex = 5;
    public final static int InsaneIndex = 6;*/
    //Inutil. DifficultyLevelEnum.values()[i] faz o mesmo
}
