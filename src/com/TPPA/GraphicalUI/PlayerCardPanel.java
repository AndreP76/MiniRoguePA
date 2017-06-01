package com.TPPA.GraphicalUI;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Internals.InternalCommandsDictionary;
import com.TPPA.GameLogic.Internals.Player;
import com.TPPA.GraphicalUI.Resources.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Lídia on 30/05/2017.
 */

public class PlayerCardPanel extends JPanel implements Observer {

    private Image backgroundImage;
    private GameStateController GS;
    private Player P;
    private SpringLayout Layout;
    private JPanel RankPanel;
    private SpringLayout RankLayout;
    private Token RankToken;
    private JPanel ArmorPanel;
    private SpringLayout ArmorLayout;
    private Token ArmorToken;
    private JPanel SpellPanel;
    private SpringLayout SpellLayout;
    private Token[] SpellTokens;
    private JPanel HpPanel;
    private SpringLayout HpLayout;
    private Token HpToken;
    private JPanel GoldPanel;
    private SpringLayout GoldLayout;
    private Token GoldToken;
    private JPanel FoodPanel;
    private SpringLayout FoodLayout;
    private Token FoodToken;


    public PlayerCardPanel(GameStateController GS) {
        super();

        this.GS = GS;
        this.GS.addObserver(this);
        P = GS.getCurrentPlayer();

        Layout = new SpringLayout();
        backgroundImage = ResourceManager.getPlayerCardBig();
        this.setPreferredSize(new Dimension(GraphicalConstants.PANEL_WIDTH, GraphicalConstants.PANEL_HEIGHT));
        this.setLayout(Layout);

        startRankPanel();
        startArmorPanel();
        startSpellPanel();
        startHpPanel();
        startGoldPanel();
        startFoodPanel();
        this.setVisible(true);
        Draw();
    }

    private void Draw() //Para corrigir bug
    {
        drawRankPanel();
        drawArmorPanel();
        drawSpellPanel();
        drawHpPanel();
        drawGoldPanel();
        drawFoodPanel();
    }

    private void startRankPanel() {
        RankPanel = new JPanel();
        RankPanel.setPreferredSize(new Dimension(GraphicalConstants.RANK_PANEL_WIDTH, GraphicalConstants.RANK_PANEL_HEIGHT));
        RankPanel.setOpaque(false);
        RankLayout = new SpringLayout();
        RankPanel.setLayout(RankLayout);

        RankToken = new Token();
        RankPanel.add(RankToken);

        this.add(RankPanel);
        this.Layout.putConstraint(SpringLayout.WEST, RankPanel, GraphicalConstants.PANEL_SIDE_PADDING, SpringLayout.WEST, this);
        this.Layout.putConstraint(SpringLayout.NORTH, RankPanel, GraphicalConstants.PANEL_TOP_PADDING, SpringLayout.NORTH, this);
    }

    private void startArmorPanel() {
        ArmorPanel = new JPanel();
        ArmorPanel.setPreferredSize(new Dimension(GraphicalConstants.SMALL_PANEL_WIDTH, GraphicalConstants.ARMOR_PANEL_HEIGHT));
        ArmorPanel.setOpaque(false);
        ArmorLayout = new SpringLayout();
        ArmorPanel.setLayout(ArmorLayout);
        ArmorToken = new Token();
        ArmorPanel.add(ArmorToken);
        this.add(ArmorPanel);
        this.Layout.putConstraint(SpringLayout.WEST, ArmorPanel, GraphicalConstants.RANK_PANEL_SIDE_PADDING, SpringLayout.EAST, RankPanel);
        this.Layout.putConstraint(SpringLayout.NORTH, ArmorPanel, GraphicalConstants.PANEL_TOP_PADDING, SpringLayout.NORTH, this);
    }

    private void startSpellPanel() {
        SpellPanel = new JPanel();
        SpellPanel.setPreferredSize(new Dimension(GraphicalConstants.SMALL_PANEL_WIDTH, GraphicalConstants.SPELL_PANEL_HEIGHT));
        SpellPanel.setOpaque(false);
        SpellLayout = new SpringLayout();
        SpellPanel.setLayout(SpellLayout);
        SpellTokens = new Token[2];
        SpellTokens[0] = new Token();
        SpellTokens[1] = new Token();
        SpellPanel.add(SpellTokens[0]);
        SpellPanel.add(SpellTokens[1]);
        this.add(SpellPanel);
        this.Layout.putConstraint(SpringLayout.WEST, SpellPanel, GraphicalConstants.RANK_PANEL_SIDE_PADDING, SpringLayout.EAST, RankPanel);
        this.Layout.putConstraint(SpringLayout.NORTH, SpellPanel, GraphicalConstants.SPELL_PANEL_TOP_PADDING, SpringLayout.SOUTH, ArmorPanel);
    }

    private void startHpPanel() {
        HpPanel = new JPanel();
        HpPanel.setPreferredSize(new Dimension(GraphicalConstants.SMALL_PANEL_WIDTH * 2 + GraphicalConstants.SMALL_PANEL_INTER_PADDING, GraphicalConstants.HP_PANEL_HEIGHT));
        HpPanel.setOpaque(false);
        HpLayout = new SpringLayout();
        HpPanel.setLayout(HpLayout);
        HpToken = new Token();
        HpPanel.add(HpToken);
        this.add(HpPanel);
        this.Layout.putConstraint(SpringLayout.WEST, HpPanel, GraphicalConstants.SMALL_PANEL_SIDE_PADDING, SpringLayout.EAST, ArmorPanel);
        this.Layout.putConstraint(SpringLayout.NORTH, HpPanel, GraphicalConstants.PANEL_TOP_PADDING, SpringLayout.NORTH, this);
    }

    private void startGoldPanel() {
        GoldPanel = new JPanel();
        GoldPanel.setPreferredSize(new Dimension(GraphicalConstants.SMALL_PANEL_WIDTH * 2 + GraphicalConstants.SMALL_PANEL_INTER_PADDING, GraphicalConstants.GOLD_PANEL_HEIGHT));
        GoldPanel.setOpaque(false);
        GoldLayout = new SpringLayout();
        GoldPanel.setLayout(GoldLayout);
        GoldToken = new Token();
        GoldPanel.add(GoldToken);
        this.add(GoldPanel);
        this.Layout.putConstraint(SpringLayout.WEST, GoldPanel, GraphicalConstants.SMALL_PANEL_SIDE_PADDING, SpringLayout.EAST, HpPanel);
        this.Layout.putConstraint(SpringLayout.NORTH, GoldPanel, GraphicalConstants.PANEL_TOP_PADDING, SpringLayout.NORTH, this);
    }

    private void startFoodPanel() {
        FoodPanel = new JPanel();
        FoodPanel.setPreferredSize(new Dimension(GraphicalConstants.SMALL_PANEL_WIDTH, GraphicalConstants.FOOD_PANEL_HEIGHT));
        FoodPanel.setOpaque(false);
        FoodLayout = new SpringLayout();
        FoodPanel.setLayout(FoodLayout);
        FoodToken = new Token();
        FoodPanel.add(FoodToken);
        this.add(FoodPanel);
        this.Layout.putConstraint(SpringLayout.WEST, FoodPanel, GraphicalConstants.SMALL_PANEL_SIDE_PADDING, SpringLayout.EAST, GoldPanel);
        this.Layout.putConstraint(SpringLayout.NORTH, FoodPanel, GraphicalConstants.PANEL_TOP_PADDING, SpringLayout.NORTH, this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);
    }

    @Override
    public void update(Observable observable, Object o) {
        Draw();
    }

    private void drawRankPanel() {
        int rank, xp;
        rank = P.getRank();
        xp = P.getXP();

        RankToken.setTokenText(xp + "XP");

        RankLayout.putConstraint(SpringLayout.WEST, RankToken, 0, SpringLayout.WEST, RankPanel);
        RankLayout.putConstraint(SpringLayout.NORTH, RankToken, GraphicalConstants.RANK_PANEL_HEIGHT - (GraphicalConstants.RANK_CELL_HEIGHT * rank), SpringLayout.NORTH, RankPanel);
    }

    private void drawArmorPanel() {
        int armor = P.getArmor();

        ArmorToken.setTokenText("" + armor);
        armor++;
        ArmorLayout.putConstraint(SpringLayout.WEST, ArmorToken, 0, SpringLayout.WEST, ArmorPanel);
        ArmorLayout.putConstraint(SpringLayout.NORTH, ArmorToken, GraphicalConstants.ARMOR_PANEL_HEIGHT - (GraphicalConstants.SMALL_CELL_HEIGHT * armor), SpringLayout.NORTH, ArmorPanel);

    }

    private void drawSpellPanel() {
        int nSpells = P.getSpellsInventory().size();

        if (nSpells == 0) {
            SpellTokens[0].setVisible(false);
            SpellTokens[1].setVisible(false);
            return;
        }

        String s = "";

        if ((nSpells == 2) && P.getSpellsInventory().get(0).getSpellID().equals(P.getSpellsInventory().get(1).getSpellID())) {
            s += "x2";
        }

        for (int i = 0; i < nSpells; i++) {
            SpellTokens[i].setTokenText(s);
            SpellTokens[i].setVisible(true);
            int index = getSpellIndex(P.getSpellsInventory().get(i).getSpellID());
            SpellLayout.putConstraint(SpringLayout.WEST, SpellTokens[i], 0, SpringLayout.WEST, SpellPanel);
            SpellLayout.putConstraint(SpringLayout.NORTH, SpellTokens[i], GraphicalConstants.SPELL_PANEL_HEIGHT - (GraphicalConstants.SMALL_CELL_HEIGHT * index), SpringLayout.NORTH, SpellPanel);
        }

        if (nSpells == 1)
            SpellTokens[1].setVisible(false);

    }

    private int getSpellIndex(String SpellID) {
        int index = 0;
        switch (SpellID) {
            case InternalCommandsDictionary.FireSpellID:
                index = 4;
                break;
            case InternalCommandsDictionary.IceSpellID:
                index = 3;
                break;
            case InternalCommandsDictionary.PoisonSpellID:
                index = 2;
                break;
            case InternalCommandsDictionary.HealSpellID:
                index = 1;
                break;
        }
        return index;
    }

    private void drawHpPanel() {
        int HP = P.getHP();

        if (HP < 0)
            HP = 0;

        HpToken.setTokenText("" + HP);

        int x = 0;
        if (HP <= 10)
            x += GraphicalConstants.SMALL_CELL_HEIGHT;

        int y = 0;
        if ((HP == 10) || (HP == 20))    //para token não ficar na base do painel quando HP%10 = 0
            y = 11;
        else
            y += (HP % 10) + 1;

        HpLayout.putConstraint(SpringLayout.WEST, HpToken, x, SpringLayout.WEST, HpPanel);
        HpLayout.putConstraint(SpringLayout.NORTH, HpToken, GraphicalConstants.HP_PANEL_HEIGHT - (GraphicalConstants.SMALL_CELL_HEIGHT * y), SpringLayout.NORTH, HpPanel);
    }

    private void drawGoldPanel() {
        int gold = P.getGold();

        if (gold < 0)
            gold = 0;

        GoldToken.setTokenText("" + gold);

        int x = 0;
        if (gold <= 10)
            x += GraphicalConstants.SMALL_CELL_HEIGHT;

        int y = 0;
        if ((gold == 10) || (gold == 20))
            y = 11;
        else
            y += (gold % 10) + 1;

        GoldLayout.putConstraint(SpringLayout.WEST, GoldToken, x, SpringLayout.WEST, GoldPanel);
        GoldLayout.putConstraint(SpringLayout.NORTH, GoldToken, GraphicalConstants.GOLD_PANEL_HEIGHT - (GraphicalConstants.SMALL_CELL_HEIGHT * y), SpringLayout.NORTH, GoldPanel);
    }

    private void drawFoodPanel() {
        int food = P.getFood();

        FoodToken.setTokenText("" + food);
        food++;
        FoodLayout.putConstraint(SpringLayout.WEST, FoodToken, 0, SpringLayout.WEST, FoodPanel);
        FoodLayout.putConstraint(SpringLayout.NORTH, FoodToken, GraphicalConstants.FOOD_PANEL_HEIGHT - (GraphicalConstants.SMALL_CELL_HEIGHT * food), SpringLayout.NORTH, FoodPanel);

    }

}
