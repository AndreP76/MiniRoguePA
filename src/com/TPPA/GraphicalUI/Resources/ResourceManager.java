package com.TPPA.GraphicalUI.Resources;

import com.TPPA.GameLogic.Internals.Deck;
import com.TPPA.Main;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by andre on 5/20/17.
 */
public class ResourceManager {
    public static Image HPIcon;
    public static Image GoldIcon;
    public static Image XPIcon;
    public static Image ArmorIcon;
    public static Image FoodIcon;
    public static Image CardBack;
    public static Image MonsterCardFace;
    public static Image BossMonsterCardFace;
    public static Image EventCardFace;
    public static Image TrapCardFace;
    public static Image RestCardFace;
    public static Image TreasureCardFace;
    public static Image MerchantCardFace;
    public static Image DieFace1;
    public static Image DieFace2;
    public static Image DieFace3;
    public static Image DieFace4;
    public static Image DieFace5;
    public static Image DieFace6;
    public static Image GameOver;
    public static Image YouDied;
    public static Image TitleImage;
    public static Image TableImage;

    public static Image PlayerCardBig;
    public static Image DungeonCardBig;

    public static Font YouDiedFont;
    public static Clip ThemeSong;
    public static Clip BattleThemeSong;
    public static Clip BossBattleSong;
    public static Clip RestSong;
    public static Clip EventFX;
    public static Clip TrapFX;
    public static Clip IceSpellFX;
    public static Clip FireSpellFX;
    public static Clip PoisonSpellFX;
    public static Clip HealSpellFX;
    public static Clip GameOverFX;
    public static Clip GameOverGong;
    public static Clip[] MonsterHurtFX;
    public static Clip[] MonsterAttackFX;
    public static Clip[] PlayerAttackFX;
    public static Clip[] TradeFX;
    private static String ResourceFolderPath = "./res/";
    private static String TitleImagePath = ResourceFolderPath + "MiniRogueTitle.png";
    private static String HPIconPath = ResourceFolderPath + "HPIcon.png";
    private static String GoldIconPath = ResourceFolderPath + "GoldIcon.png";
    private static String XPIconPath = ResourceFolderPath + "XPIcon.png";
    private static String ArmorIconPath = ResourceFolderPath + "ArmorIcon.png";
    private static String FoodIconPath = ResourceFolderPath + "FoodIcon.png";
    private static String CardBackPath = ResourceFolderPath + "CardBack.png";
    private static String MonsterCardFacePath = ResourceFolderPath + "MonsterCardFace.png";
    private static String BossMonsterCardFacePath = ResourceFolderPath + "BossMonsterCardFace.png";
    private static String EventCardFacePath = ResourceFolderPath + "EventCardFace.png";
    private static String TrapCardFacePath = ResourceFolderPath + "TrapCardFace.png";
    private static String RestCardFacePath = ResourceFolderPath + "RestingCardFace.png";
    private static String TreasureCardFacePath = ResourceFolderPath + "TreasureCardFace.png";
    private static String MerchantCardFacePath = ResourceFolderPath + "MerchantCardFace.png";
    private static String DieFace1Path = ResourceFolderPath + "DieFace1.png";
    private static String DieFace2Path = ResourceFolderPath + "DieFace2.png";
    private static String DieFace3Path = ResourceFolderPath + "DieFace3.png";
    private static String DieFace4Path = ResourceFolderPath + "DieFace4.png";
    private static String DieFace5Path = ResourceFolderPath + "DieFace5.png";
    private static String DieFace6Path = ResourceFolderPath + "DieFace6.png";
    private static String GameOverPath = ResourceFolderPath + "GameOver.jpg";
    private static String YouDiedPath = ResourceFolderPath + "YouDied.png";
    private static String PlayerCardBigPath = ResourceFolderPath + "PlayerCard.png";
    private static String DungeonCardBigPath = ResourceFolderPath + "DungeonCard.png";
    private static String YouDiedFontPath = ResourceFolderPath + "OptimusPrinceps.ttf";
    private static String TableImagePath = ResourceFolderPath + "WoodTable.jpg";
    private static String SoundFolderPath = ResourceFolderPath + "SoundResources/";
    private static String ThemeSongPath = SoundFolderPath + "MainTheme.wav";
    private static String BattleThemeSongPath = SoundFolderPath + "BattleLoop.wav";
    private static String BossBattleSongPath = SoundFolderPath + "BattleLoop.wav";
    private static String RestSongPath = SoundFolderPath + "DrawTheme.wav";
    private static String EventFXPath = SoundFolderPath + "EventFX.wav";
    private static String TrapFXPath = SoundFolderPath + "PlayerAttack1.wav";
    private static String IceSpellFXPath = SoundFolderPath + "IceSpell.wav";
    private static String FireSpellFXPath = SoundFolderPath + "FireBall.wav";
    private static String PoisonSpellFXPath = SoundFolderPath + "Poison.wav";
    private static String HealSpellFXPath = SoundFolderPath + "HealSpell.wav";
    private static String GameOverFXPath = SoundFolderPath + "GameOver.wav";
    private static String GameOverGongPath = SoundFolderPath + "GameOverGong.wav";
    private static String[] MonsterAttackFXPath = {SoundFolderPath + "MonsterAttack1.wav", SoundFolderPath + "MonsterAttack2.wav", SoundFolderPath + "MonsterAttack3.wav"};
    private static String[] MonsterHurtFXPath = {SoundFolderPath + "MonsterHurt1.wav", SoundFolderPath + "MonsterHurt2.wav", SoundFolderPath + "MonsterHurt3.wav", SoundFolderPath + "MonsterHurt4.wav", SoundFolderPath + "MonsterHurt5.wav"};
    private static String[] PlayerAttackFXPath = {SoundFolderPath + "PlayerAttack1.wav", SoundFolderPath + "PlayerAttack2.wav", SoundFolderPath + "PlayerAttack3.wav", SoundFolderPath + "PlayerAttack4.wav", SoundFolderPath + "PlayerAttack5.wav"};
    private static String[] TradeFXPath = {SoundFolderPath + "Trade1.wav", SoundFolderPath + "Trade2.wav", SoundFolderPath + "Trade3.wav"};

    private static HashMap<String, Image> CardToImageMap;
    private static HashMap<Integer, Image> DieRollToImage;
    private static Random SR;
    static {
        SR = new Random();
        try {
            Mixer.Info[] Lines = AudioSystem.getMixerInfo();
            for (Mixer.Info L : Lines) {
                Main.ErrorStream.println("Line : " + L);
            }
            HPIcon = ImageIO.read(new File(HPIconPath));
            GoldIcon = ImageIO.read(new File(GoldIconPath));
            XPIcon = ImageIO.read(new File(XPIconPath));
            ArmorIcon = ImageIO.read(new File(ArmorIconPath));
            FoodIcon = ImageIO.read(new File(FoodIconPath));

            CardBack = ImageIO.read(new File(CardBackPath));
            EventCardFace = ImageIO.read(new File(EventCardFacePath));
            MonsterCardFace = ImageIO.read(new File(MonsterCardFacePath));
            BossMonsterCardFace = ImageIO.read(new File(BossMonsterCardFacePath));
            TreasureCardFace = ImageIO.read(new File(TreasureCardFacePath));
            MerchantCardFace = ImageIO.read(new File(MerchantCardFacePath));
            TrapCardFace = ImageIO.read(new File(TrapCardFacePath));
            RestCardFace = ImageIO.read(new File(RestCardFacePath));

            DieFace1 = ImageIO.read(new File(DieFace1Path));
            DieFace2 = ImageIO.read(new File(DieFace2Path));
            DieFace3 = ImageIO.read(new File(DieFace3Path));
            DieFace4 = ImageIO.read(new File(DieFace4Path));
            DieFace5 = ImageIO.read(new File(DieFace5Path));
            DieFace6 = ImageIO.read(new File(DieFace6Path));

            PlayerCardBig = ImageIO.read(new File(PlayerCardBigPath));
            DungeonCardBig = ImageIO.read(new File(DungeonCardBigPath));
            GameOver = ImageIO.read(new File(GameOverPath));
            YouDied = ImageIO.read(new File(YouDiedPath));
            YouDiedFont = Font.createFont(Font.TRUETYPE_FONT, new File(YouDiedFontPath));
            TitleImage = ImageIO.read(new File(TitleImagePath));
            TableImage = ImageIO.read(new File(TableImagePath));

            MonsterAttackFX = new Clip[MonsterAttackFXPath.length];
            for (int i = 0; i < MonsterAttackFXPath.length; ++i) {
                MonsterAttackFX[i] = LoadAudioClip(MonsterAttackFXPath[i]);
            }

            MonsterHurtFX = new Clip[MonsterHurtFXPath.length];
            for (int i = 0; i < MonsterHurtFXPath.length; ++i) {
                MonsterHurtFX[i] = LoadAudioClip(MonsterHurtFXPath[i]);
            }

            PlayerAttackFX = new Clip[PlayerAttackFXPath.length];
            for (int i = 0; i < PlayerAttackFXPath.length; ++i) {
                PlayerAttackFX[i] = LoadAudioClip(PlayerAttackFXPath[i]);
            }

            TradeFX = new Clip[TradeFXPath.length];
            for (int i = 0; i < TradeFXPath.length; ++i) {
                TradeFX[i] = LoadAudioClip(TradeFXPath[i]);
            }

            ThemeSong = LoadAudioClip(ThemeSongPath);
            BattleThemeSong = LoadAudioClip(BattleThemeSongPath);
            BossBattleSong = LoadAudioClip(BossBattleSongPath);
            RestSong = LoadAudioClip(RestSongPath);
            EventFX = LoadAudioClip(EventFXPath);
            TrapFX = LoadAudioClip(TrapFXPath);
            IceSpellFX = LoadAudioClip(IceSpellFXPath);
            FireSpellFX = LoadAudioClip(FireSpellFXPath);
            PoisonSpellFX = LoadAudioClip(PoisonSpellFXPath);
            HealSpellFX = LoadAudioClip(HealSpellFXPath);
            GameOverFX = LoadAudioClip(GameOverFXPath);
            GameOverGong = LoadAudioClip(GameOverGongPath);
        } catch (IOException iox) {
            Main.ErrorStream.println(Paths.get("./").toAbsolutePath().toString());
            iox.printStackTrace();
            Main.ErrorStream.println(iox.toString());
            Main.ErrorStream.println(iox.getCause());
            Main.ErrorStream.println(iox.fillInStackTrace());
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        CardToImageMap = new HashMap<>();
        CardToImageMap.put(Deck.BossMonsterCardID, BossMonsterCardFace);
        CardToImageMap.put(Deck.EventCardID, EventCardFace);
        CardToImageMap.put(Deck.MerchantCardID, MerchantCardFace);
        CardToImageMap.put(Deck.MonsterCardID, MonsterCardFace);
        CardToImageMap.put(Deck.RestCardID, RestCardFace);
        CardToImageMap.put(Deck.TrapCardID, TrapCardFace);
        CardToImageMap.put(Deck.TreasureCardID, TreasureCardFace);

        DieRollToImage = new HashMap<>();
        DieRollToImage.put(1, DieFace1);
        DieRollToImage.put(2, DieFace2);
        DieRollToImage.put(3, DieFace3);
        DieRollToImage.put(4, DieFace4);
        DieRollToImage.put(5, DieFace5);
        DieRollToImage.put(6, DieFace6);
    }

    public static Image ResolveCardImage(String CardID) {
        return CardToImageMap.get(CardID);
    }

    public static Image ResolveDieRollImage(Integer DieRoll) {
        return DieRollToImage.get(DieRoll);
    }

    public static Image getPlayerCardBig() {
        return PlayerCardBig;
    }

    public static Image getDungeonCardBig() {
        return DungeonCardBig;
    }

    public static Clip LoadAudioClip(String FilePath) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        Clip in;
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(FilePath));
        in = AudioSystem.getClip();
        in.open(audioIn);
        return in;
    }

    public static Clip getNextMonsterAttack() {
        int R = SR.nextInt(MonsterAttackFX.length);
        return MonsterAttackFX[R];
    }

    public static Clip getNextMonsterHurt() {
        int R = SR.nextInt(MonsterHurtFX.length);
        return MonsterHurtFX[R];
    }

    public static Clip getNextTrade() {
        int R = SR.nextInt(TradeFX.length);
        return TradeFX[R];
    }

    public static Clip getNextPlayerAttack() {
        int R = SR.nextInt(PlayerAttackFX.length);
        return PlayerAttackFX[R];
    }

    public static void Init() {

    }
}
