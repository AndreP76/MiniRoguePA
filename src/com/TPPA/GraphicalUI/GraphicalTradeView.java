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
    private JPanel ContentPanel;
    private JPanel SellPanel;
    private JPanel BuyPanel;
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
    private PlayerCardPanel PCP;
    private Menu menu;

    public GraphicalTradeView(GameStateController GS) {
        super(GS);
    }

    private JButton CreateButton(String text, int Width, int Height, int PrePadWidth, JPanel Parent) {
        /*Dimension D = new Dimension(Width,Height);
        Parent.add(new Box.Filler(D,D,D));*/
        //Parent.add(Box.createRigidArea(new Dimension(PrePadWidth,Height)));
        JButton J = new JButton(text);
        J.setPreferredSize(new Dimension(Width, Height));
        J.setMinimumSize(new Dimension(Width, Height));
        J.setMaximumSize(new Dimension(Width, Height));
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
        ContentPanel = new JPanel();
        ContentPanel.setLayout(L);
        this.setContentPane(ContentPanel);

        menu = new Menu(this, GS.getCurrentGameState());
        ContentPanel.add(menu);
        L.putConstraint(SpringLayout.WEST, menu, 0, SpringLayout.WEST, this);
        L.putConstraint(SpringLayout.NORTH, menu, 0, SpringLayout.NORTH, this);

        SellPanel = new JPanel();
        SPL = new BoxLayout(SellPanel, BoxLayout.PAGE_AXIS);
        SellPanel.setLayout(SPL);
        ContentPanel.add(SellPanel);

        BuyPanel = new JPanel();
        BPL = new BoxLayout(BuyPanel, BoxLayout.PAGE_AXIS);
        BuyPanel.setLayout(BPL);
        ContentPanel.add(BuyPanel);

        BuyPanelTitle = new JLabel("Buy");
        BuyPanel.add(BuyPanelTitle);

        BuyArmorBtn = CreateButton("Buy Armor", ButtonWidth, ButtonHeight, PaddingWidth, BuyPanel);
        BuyRationBtn = CreateButton("Buy Ration", ButtonWidth, ButtonHeight, PaddingWidth, BuyPanel);
        BuyHealthBtn = CreateButton("Buy Health Potion", ButtonWidth, ButtonHeight, PaddingWidth, BuyPanel);
        BuyHealthBigBtn = CreateButton("Buy Big Health Potion", ButtonWidth, ButtonHeight, PaddingWidth, BuyPanel);
        BuySpellBtn = CreateButton("Buy A spell", ButtonWidth, ButtonHeight, PaddingWidth, BuyPanel);

        SellPanelTitle = new JLabel("Sell");
        SellPanel.add(SellPanelTitle);
        ContentPanel.add(SellPanel);

        SellArmorBtn = CreateButton("Sell Armor", ButtonWidth, ButtonHeight, PaddingWidth, SellPanel);
        SellSpellBtn = CreateButton("Sell a Spell", ButtonWidth, ButtonHeight, PaddingWidth, SellPanel);

        PCP = new PlayerCardPanel(GS);
        PCP.setMaximumSize(new Dimension(Width, Height));
        PCP.setPreferredSize(new Dimension(Width / 2, Height / 2));
        SellPanel.add(PCP);

        L.putConstraint(SpringLayout.NORTH, SellPanel, 0, SpringLayout.NORTH, ContentPanel);
        L.putConstraint(SpringLayout.SOUTH, SellPanel, Height / 2, SpringLayout.SOUTH, ContentPanel);
        L.putConstraint(SpringLayout.EAST, SellPanel, 0, SpringLayout.EAST, ContentPanel);
        L.putConstraint(SpringLayout.WEST, SellPanel, Width / 2, SpringLayout.WEST, ContentPanel);

        L.putConstraint(SpringLayout.NORTH, BuyPanel, 0, SpringLayout.NORTH, ContentPanel);
        L.putConstraint(SpringLayout.SOUTH, BuyPanel, 0, SpringLayout.SOUTH, ContentPanel);
        L.putConstraint(SpringLayout.EAST, BuyPanel, 0, SpringLayout.WEST, SellPanel);
        L.putConstraint(SpringLayout.WEST, BuyPanel, 0, SpringLayout.WEST, ContentPanel);

        L.putConstraint(SpringLayout.NORTH, PCP, 10, SpringLayout.SOUTH, SellSpellBtn);
        L.putConstraint(SpringLayout.SOUTH, PCP, 10, SpringLayout.SOUTH, ContentPanel);
        L.putConstraint(SpringLayout.EAST, PCP, 0, SpringLayout.EAST, ContentPanel);
        L.putConstraint(SpringLayout.WEST, PCP, 10, SpringLayout.EAST, BuyPanel);

        this.setLocation(startWidth, startHeight);
        this.setSize(Width, Height);
        this.setVisible(true);
    }

    public void HookListeners() {
        BuyArmorBtn.addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.BuyArmorPiece));
        BuyRationBtn.addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.BuyRation));
        BuyHealthBtn.addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.BuyHealthPotion));
        BuySpellBtn.addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.BuySpell));//TODO : Select spell modal
        BuyHealthBigBtn.addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.BuyBigHealthPotion));

        SellSpellBtn.addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.SellSpell));//TODO: Also here
        SellArmorBtn.addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.SellArmorPiece));
    }

    @Override
    public void Render() {
        Draw();
        HookListeners();
    }

    @Override
    public void DestroyView() {

    }
}
