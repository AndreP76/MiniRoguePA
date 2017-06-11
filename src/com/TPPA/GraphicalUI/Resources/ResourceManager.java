package com.TPPA.GraphicalUI.Resources;

import com.TPPA.GameLogic.Internals.Deck;
import com.TPPA.Main;

import javax.imageio.ImageIO;
import java.applet.AudioClip;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

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
    public static AudioClip ThemeSong;
    public static AudioClip BattleThemeSong;
    public static AudioClip BossBattleSong;
    public static AudioClip RestSong;
    public static AudioClip TrapSong;
    public static AudioClip EventFX;
    public static AudioClip TrapFX;
    public static AudioClip IceSpellFX;
    public static AudioClip FireSpellFX;
    public static AudioClip PoisonSpellFX;
    public static AudioClip HealSpellFX;
    private static String ResourceFolderPath = "./src/com/TPPA/GraphicalUI/Resources/";
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
    private static String ThemeSongPath;
    private static String BattleThemeSongPath;
    private static String BossBattleSongPath;
    private static String RestSongPath;
    private static String TrapSongPath;
    private static String EventFXPath;
    private static String TrapFXPath;
    private static String IceSpellFXPath;
    private static String FireSpellFXPath;
    private static String PoisonSpellFXPath;
    private static String HealSpellFXPath;

    private static HashMap<String, Image> CardToImageMap;
    private static HashMap<Integer, Image> DieRollToImage;

    static {
        try {
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
        } catch (IOException iox) {
            Main.ErrorStream.println(Paths.get("./").toAbsolutePath().toString());
            iox.printStackTrace();
            Main.ErrorStream.println(iox.toString());
            Main.ErrorStream.println(iox.getCause());
            Main.ErrorStream.println(iox.fillInStackTrace());
        } catch (FontFormatException e) {
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

    public static void Init() {

    }
}
