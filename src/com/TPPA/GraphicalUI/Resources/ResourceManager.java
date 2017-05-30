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

    public static Image PlayerCardBig;
    public static Image DungeonCardBig;

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
    private static String HPIconPath = "./src/com/TPPA/GraphicalUI/Resources/HPIcon.png";
    private static String GoldIconPath = "./src/com/TPPA/GraphicalUI/Resources/GoldIcon.png";
    private static String XPIconPath = "./src/com/TPPA/GraphicalUI/Resources/XPIcon.png";
    private static String ArmorIconPath = "./src/com/TPPA/GraphicalUI/Resources/ArmorIcon.png";
    private static String FoodIconPath = "./src/com/TPPA/GraphicalUI/Resources/FoodIcon.png";
    private static String CardBackPath = "./src/com/TPPA/GraphicalUI/Resources/CardBack.png";
    private static String MonsterCardFacePath = "./src/com/TPPA/GraphicalUI/Resources/MonsterCardFace.png";
    private static String BossMonsterCardFacePath = "./src/com/TPPA/GraphicalUI/Resources/BossMonsterCardFace.png";
    private static String EventCardFacePath = "./src/com/TPPA/GraphicalUI/Resources/EventCardFace.png";
    private static String TrapCardFacePath = "./src/com/TPPA/GraphicalUI/Resources/TrapCardFace.png";
    private static String RestCardFacePath = "./src/com/TPPA/GraphicalUI/Resources/RestingCardFace.png";
    private static String TreasureCardFacePath = "./src/com/TPPA/GraphicalUI/Resources/TreasureCardFace.png";
    private static String MerchantCardFacePath = "./src/com/TPPA/GraphicalUI/Resources/MerchantCardFace.png";
    private static String DieFace1Path = "./src/com/TPPA/GraphicalUI/Resources/DieFace1.png";
    private static String DieFace2Path = "./src/com/TPPA/GraphicalUI/Resources/DieFace2.png";
    private static String DieFace3Path = "./src/com/TPPA/GraphicalUI/Resources/DieFace3.png";
    private static String DieFace4Path = "./src/com/TPPA/GraphicalUI/Resources/DieFace4.png";
    private static String DieFace5Path = "./src/com/TPPA/GraphicalUI/Resources/DieFace5.png";
    private static String DieFace6Path = "./src/com/TPPA/GraphicalUI/Resources/DieFace6.png";

    private static String PlayerCardBigPath = "./src/com/TPPA/GraphicalUI/Resources/PlayerCard.png";
    private static String DungeonCardBigPath = "./src/com/TPPA/GraphicalUI/Resources/DungeonCard.png";

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

        } catch (IOException iox) {
            Main.ErrorStream.println(Paths.get("./").toAbsolutePath().toString());
            iox.printStackTrace();
            Main.ErrorStream.println(iox.toString());
            Main.ErrorStream.println(iox.getCause());
            Main.ErrorStream.println(iox.fillInStackTrace());
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
