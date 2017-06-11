package com.TPPA.GraphicalUI;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Internals.InternalCommandsDictionary;

import javax.swing.*;
import java.awt.*;

/**
 * Created by andre on 5/20/17.
 */

public class GraphicalTradeView extends GraphicalStateView {
    int startWidth = 0;
    int startHeight = 0;
    int Width = 0;
    int Height = 0;
    int PaddingWidth = 0;
    int ButtonWidth = 0;
    int ButtonHeight = 0;
    private TablePanel ContentPanel;
    private TablePanel SellPanel;
    private TablePanel BuyPanel;
    private JLabel BuyPanelTitle;
    private JLabel SellPanelTitle;
    private SpringLayout L;
    private BoxLayout SPL;
    private BoxLayout BPL;
    private JButton SellArmorBtn;
    private JButton SellSpellBtn;
    private JButton BuyRationBtn;
    private JButton BuySpellBtn;
    private JButton BuyHealthBtn;
    private JButton BuyHealthBigBtn;
    private JButton BuyArmorBtn;
    private JButton EndBtn;
    private PlayerCardPanel PCP;
    private MyMenu myMenu;

    public GraphicalTradeView(GameStateController GS) {
        super(GS);
    }

    private JButton CreateButton(String text, int Width, int Height, int PrePadWidth, JPanel Parent) {
        /*Dimension D = new Dimension(PrePadWidth,Height);
        Parent.add(new Box.Filler(D,D,D));*/
        //Parent.add(Box.createRigidArea(new Dimension(PrePadWidth,Height)));
        /*JPanel Jp = new JPanel();
        Jp.setLayout(new FlowLayout());
        Jp.add(new Box.Filler(D,D,D));*/
        JButton J = new JButton(text);
        J.setPreferredSize(new Dimension(Width, Height));
        J.setMinimumSize(new Dimension(Width, Height));
        J.setMaximumSize(new Dimension(Width, Height));
        //Jp.add(J);
        Parent.add(J);

        return J;
    }

    private void Draw() {
        Width = (int) (ScreenSize.getWidth() * 0.75);
        Height = (int) (ScreenSize.getHeight() * 0.75);
        startHeight = (int) (ScreenSize.getHeight() * 0.125);
        startWidth = (int) (ScreenSize.getWidth() * 0.125);
        ButtonWidth = (int) ((Width / 2) * 0.75);
        ButtonHeight = Height / 8;
        PaddingWidth = (int) ((Width / 2) * 0.125);

        L = new SpringLayout();
        ContentPanel = new TablePanel();
        ContentPanel.setLayout(L);
        this.setContentPane(ContentPanel);

        myMenu = new MyMenu(this, GS.getCurrentGameState());
        ContentPanel.add(myMenu);
        L.putConstraint(SpringLayout.WEST, myMenu, 0, SpringLayout.WEST, this);
        L.putConstraint(SpringLayout.NORTH, myMenu, 0, SpringLayout.NORTH, this);

        SellPanel = new TablePanel();
        SPL = new BoxLayout(SellPanel, BoxLayout.PAGE_AXIS);
        SellPanel.setLayout(SPL);
        ContentPanel.add(SellPanel);

        BuyPanel = new TablePanel();
        BPL = new BoxLayout(BuyPanel, BoxLayout.PAGE_AXIS);
        BuyPanel.setLayout(BPL);
        ContentPanel.add(BuyPanel);

        BuyPanelTitle = new JLabel("Buy");
        BuyPanelTitle.setForeground(Color.WHITE);
        BuyPanel.add(BuyPanelTitle);

        int PlayerGold = GS.getCurrentPlayer().getGold();
        int PlayerArmor = GS.getCurrentPlayer().getArmor();
        int PlayerFood = GS.getCurrentPlayer().getFood();
        int PlayerHP = GS.getCurrentPlayer().getHP();
        BuyArmorBtn = CreateButton("Buy Armor (+1 Armor, -6 Gold)", ButtonWidth, ButtonHeight, PaddingWidth, BuyPanel);
        if (PlayerGold < 6 || PlayerArmor >= 5)
            BuyArmorBtn.setEnabled(false);
        BuyRationBtn = CreateButton("Buy Ration (+1 Food, -1 Gold)", ButtonWidth, ButtonHeight, PaddingWidth, BuyPanel);
        if (PlayerGold < 1 || PlayerFood >= 6)
            BuyRationBtn.setEnabled(false);
        BuyHealthBtn = CreateButton("Buy Health Potion (+1HP, -1 Gold)", ButtonWidth, ButtonHeight, PaddingWidth, BuyPanel);
        if (PlayerGold < 1 || PlayerHP >= 20)
            BuyHealthBtn.setEnabled(false);
        BuyHealthBigBtn = CreateButton("Buy Big Health Potion (+4 HP, -3 Gold)", ButtonWidth, ButtonHeight, PaddingWidth, BuyPanel);
        if (PlayerGold < 3 || PlayerHP >= 20)
            BuyHealthBigBtn.setEnabled(false);
        BuySpellBtn = CreateButton("Buy A spell (-8 Gold)", ButtonWidth, ButtonHeight, PaddingWidth, BuyPanel);
        if (PlayerGold < 8)
            BuySpellBtn.setEnabled(false);

        SellPanelTitle = new JLabel("Sell");
        SellPanelTitle.setForeground(Color.WHITE);
        SellPanel.add(SellPanelTitle);
        ContentPanel.add(SellPanel);

        SellArmorBtn = CreateButton("Sell Armor (-1 Armor, +3 Gold)", ButtonWidth, ButtonHeight, PaddingWidth, SellPanel);
        if (PlayerArmor < 1 || PlayerGold == 20)
            SellArmorBtn.setEnabled(false);
        SellSpellBtn = CreateButton("Sell a Spell", ButtonWidth, ButtonHeight, PaddingWidth, SellPanel);
        if (GS.getCurrentPlayer().getSpellsInventory().size() < 1)
            SellSpellBtn.setEnabled(false);
        PCP = new PlayerCardPanel(GS);
        PCP.setMaximumSize(new Dimension(Width, Height));
        PCP.setPreferredSize(new Dimension(Width / 2, Height / 2));
        SellPanel.add(PCP);

        EndBtn = CreateButton("Finish trading", ButtonWidth, ButtonHeight, PaddingWidth, BuyPanel);
        L.putConstraint(SpringLayout.NORTH, SellPanel, 0, SpringLayout.NORTH, ContentPanel);
        L.putConstraint(SpringLayout.SOUTH, SellPanel, Height / 2, SpringLayout.SOUTH, ContentPanel);
        L.putConstraint(SpringLayout.EAST, SellPanel, 0, SpringLayout.EAST, ContentPanel);
        L.putConstraint(SpringLayout.WEST, SellPanel, Width / 2, SpringLayout.WEST, ContentPanel);

        L.putConstraint(SpringLayout.NORTH, BuyPanel, 30, SpringLayout.NORTH, ContentPanel);
        L.putConstraint(SpringLayout.SOUTH, BuyPanel, 0, SpringLayout.SOUTH, ContentPanel);
        L.putConstraint(SpringLayout.EAST, BuyPanel, 0, SpringLayout.WEST, SellPanel);
        L.putConstraint(SpringLayout.WEST, BuyPanel, 0, SpringLayout.WEST, ContentPanel);

        //L.putConstraint(SpringLayout.NORTH, PCP, 10, SpringLayout.SOUTH, SellSpellBtn);
        L.putConstraint(SpringLayout.SOUTH, PCP, 10, SpringLayout.SOUTH, ContentPanel);
        L.putConstraint(SpringLayout.EAST, PCP, 0, SpringLayout.EAST, ContentPanel);
        //L.putConstraint(SpringLayout.WEST, PCP, 10, SpringLayout.EAST, BuyPanel);

        /*L.putConstraint(SpringLayout.EAST, EndBtn, 5, SpringLayout.EAST, ContentPanel);
        L.putConstraint(SpringLayout.SOUTH, EndBtn, 5, SpringLayout.SOUTH, ContentPanel);
        L.putConstraint(SpringLayout.WEST,EndBtn,0,SpringLayout.EAST,PCP);
        L.putConstraint(SpringLayout.NORTH,EndBtn,50,SpringLayout.SOUTH,ContentPanel);*/
        this.setLocation(startWidth, startHeight);
        this.setSize(Width, Height);
        this.setVisible(true);
    }

    public void HookListeners() {
        BuyArmorBtn.addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.BuyArmorPiece));
        BuyRationBtn.addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.BuyRation));
        BuyHealthBtn.addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.BuyHealthPotion));
        BuySpellBtn.addActionListener(actionEvent -> {
            GS.RelayAction(InternalCommandsDictionary.BuySpell + " " + SpellSelector.GetBuySpell(BuyPanel));
        });
        BuyHealthBigBtn.addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.BuyBigHealthPotion));

        SellSpellBtn.addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.SellSpell + " " + SpellSelector.GetSpellIndex(BuyPanel, GS.getCurrentPlayer())));
        SellArmorBtn.addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.SellArmorPiece));

        EndBtn.addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.EndTradingState));
    }

    @Override
    public void Render() {
        Draw();
        HookListeners();
    }

    @Override
    public void DestroyView() {
        this.dispose();
    }
}
