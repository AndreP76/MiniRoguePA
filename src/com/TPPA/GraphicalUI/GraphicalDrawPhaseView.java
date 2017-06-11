package com.TPPA.GraphicalUI;

import com.TPPA.GameLogic.Cards.CardBase;
import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Internals.InternalCommandsDictionary;
import com.TPPA.GameLogic.Internals.Player;
import com.TPPA.GameLogic.Internals.Utils;
import com.TPPA.GraphicalUI.Resources.ResourceManager;

import javax.swing.*;
import java.awt.*;

/**
 * Created by andre on 5/20/17.
 */

public class GraphicalDrawPhaseView extends GraphicalStateView {
    private SpringLayout Layout;

    private TablePanel ContentPanel;
    private JButton[] CardButtons;

    private MyMenu myMenu;

    private LogArea log;

    private PlayerCardPanel playerStats;
    private DungeonCardPanel dungeonInfo;
    private JPanel dungeonInfoContainer;
    private SpringLayout dungeonLayout;

    GraphicalDrawPhaseView(GameStateController GS) {
        super(GS);
    }

    private void Draw() {
        Player P = GS.getCurrentPlayer();
        int gapSize = 7;
        int startWidth = 0;
        int startHeight = 0;
        int Width = 0;
        int Height = 0;

        Width = (int) (ScreenSize.getWidth() * 0.75);
        Height = (int) (ScreenSize.getHeight() * 0.75);
        startHeight = (int) (ScreenSize.getHeight() * 0.125);
        startWidth = (int) (ScreenSize.getWidth() * 0.125);

        //draw the Stats myMenu
        Layout = new SpringLayout();
        ContentPanel = new TablePanel();
        ContentPanel.setLayout(Layout);
        this.setContentPane(ContentPanel);

        CardBase[] CBLinear = Utils.MakeLinear(GS.getRoomStages());
        CardButtons = new JButton[CBLinear.length];

        log = new LogArea(GS);
        ContentPanel.add(log);

        for (int i = 0; i < CBLinear.length; ++i) {
            CardBase CardHere;
            CardHere = CBLinear[i];
            CardButtons[i] = new JButton();
            CardButtons[i].setIcon(new ImageIcon(ResourceManager.CardBack));
            CardButtons[i].setDisabledIcon(new ImageIcon(ResourceManager.CardBack));
            CardButtons[i].setEnabled(false);

            CardButtons[i].setPreferredSize(new Dimension(GraphicalConstants.CARD_BUTTON_WIDTH, GraphicalConstants.CARD_BUTTON_HEIGHT));
            //It should be Card Back, but let's test first...
            ContentPanel.add(CardButtons[i]);
        }

        //Layout math starts here...
        int majorGap = 9;
        Layout.putConstraint(SpringLayout.NORTH, CardButtons[0], 100, SpringLayout.NORTH, this);
        Layout.putConstraint(SpringLayout.WEST, CardButtons[0], 30, SpringLayout.WEST, this);

        Layout.putConstraint(SpringLayout.WEST, CardButtons[1], majorGap, SpringLayout.EAST, CardButtons[0]);
        Layout.putConstraint(SpringLayout.SOUTH, CardButtons[1], majorGap / 2, SpringLayout.VERTICAL_CENTER, CardButtons[0]);

        Layout.putConstraint(SpringLayout.NORTH, CardButtons[2], majorGap, SpringLayout.SOUTH, CardButtons[1]);
        Layout.putConstraint(SpringLayout.WEST, CardButtons[2], 0, SpringLayout.WEST, CardButtons[1]);
        Layout.putConstraint(SpringLayout.EAST, CardButtons[2], 0, SpringLayout.EAST, CardButtons[1]);

        Layout.putConstraint(SpringLayout.NORTH, CardButtons[3], 0, SpringLayout.NORTH, CardButtons[0]);
        Layout.putConstraint(SpringLayout.WEST, CardButtons[3], majorGap, SpringLayout.EAST, CardButtons[2]);

        Layout.putConstraint(SpringLayout.WEST, CardButtons[4], majorGap, SpringLayout.EAST, CardButtons[3]);
        Layout.putConstraint(SpringLayout.SOUTH, CardButtons[4], majorGap / 2, SpringLayout.VERTICAL_CENTER, CardButtons[3]);

        Layout.putConstraint(SpringLayout.NORTH, CardButtons[5], majorGap, SpringLayout.SOUTH, CardButtons[4]);
        Layout.putConstraint(SpringLayout.WEST, CardButtons[5], 0, SpringLayout.WEST, CardButtons[4]);

        Layout.putConstraint(SpringLayout.NORTH, CardButtons[6], 0, SpringLayout.NORTH, CardButtons[0]);
        Layout.putConstraint(SpringLayout.WEST, CardButtons[6], majorGap, SpringLayout.EAST, CardButtons[4]);
        //Ends here...

        Layout.putConstraint(SpringLayout.EAST, log, -20, SpringLayout.EAST, ContentPanel);
        Layout.putConstraint(SpringLayout.SOUTH, log, -20, SpringLayout.SOUTH, ContentPanel);


        switch (GS.getCurrentStageInRoom()) {
            case 0:
                CardButtons[0].setEnabled(true);
                CardButtons[0].setIcon(new ImageIcon(ResourceManager.ResolveCardImage(CBLinear[0].getCardID())));
                CardButtons[0].setDisabledIcon(new ImageIcon(ResourceManager.ResolveCardImage(CBLinear[0].getCardID())));
                break;
            case 1:
                CardButtons[1].setEnabled(true);
                CardButtons[2].setEnabled(true);
                for (int i = 0; i < 3; ++i) {
                    CardButtons[i].setIcon(new ImageIcon(ResourceManager.ResolveCardImage(CBLinear[i].getCardID())));
                    CardButtons[i].setDisabledIcon(new ImageIcon(ResourceManager.ResolveCardImage(CBLinear[i].getCardID())));
                }
                break;
            case 2:
                CardButtons[3].setEnabled(true);
                for (int i = 0; i < 4; ++i) {
                    CardButtons[i].setIcon(new ImageIcon(ResourceManager.ResolveCardImage(CBLinear[i].getCardID())));
                    CardButtons[i].setDisabledIcon(new ImageIcon(ResourceManager.ResolveCardImage(CBLinear[i].getCardID())));
                }
                break;
            case 3:
                CardButtons[4].setEnabled(true);
                CardButtons[5].setEnabled(true);
                for (int i = 0; i < 6; ++i) {
                    CardButtons[i].setIcon(new ImageIcon(ResourceManager.ResolveCardImage(CBLinear[i].getCardID())));
                    CardButtons[i].setDisabledIcon(new ImageIcon(ResourceManager.ResolveCardImage(CBLinear[i].getCardID())));
                }
                break;
            case 4:
                CardButtons[6].setEnabled(true);
                for (int i = 0; i < 7; ++i) {
                    CardButtons[i].setIcon(new ImageIcon(ResourceManager.ResolveCardImage(CBLinear[i].getCardID())));
                    CardButtons[i].setDisabledIcon(new ImageIcon(ResourceManager.ResolveCardImage(CBLinear[i].getCardID())));
                }

        }

        myMenu = new MyMenu(this, GS.getCurrentGameState());
        ContentPanel.add(myMenu);
        Layout.putConstraint(SpringLayout.WEST, myMenu, 0, SpringLayout.WEST, this);
        Layout.putConstraint(SpringLayout.NORTH, myMenu, 0, SpringLayout.NORTH, this);

        playerStats = new PlayerCardPanel(GS);
        ContentPanel.add(playerStats);
        Layout.putConstraint(SpringLayout.EAST, playerStats, -20, SpringLayout.EAST, this);
        Layout.putConstraint(SpringLayout.NORTH, playerStats, 20, SpringLayout.NORTH, this);

        drawDungeonInfo();

        this.setLocation(startWidth, startHeight);
        this.setPreferredSize(new Dimension(Width, Height));
        this.pack();
        this.setVisible(true);
    }

    private void HookEvents() {
        CardButtons[0].addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.DrawCommand + " " + 1));
        CardButtons[1].addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.DrawCommand + " " + 1));
        CardButtons[2].addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.DrawCommand + " " + 2));
        CardButtons[3].addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.DrawCommand + " " + 1));
        CardButtons[4].addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.DrawCommand + " " + 1));
        CardButtons[5].addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.DrawCommand + " " + 2));
        CardButtons[6].addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.DrawCommand + " " + 1));
    }

    public void drawDungeonInfo() {
        dungeonInfoContainer = new JPanel();
        dungeonLayout = new SpringLayout();
        dungeonInfoContainer.setLayout(dungeonLayout);
        dungeonInfoContainer.setPreferredSize(new Dimension(135, 235));
        dungeonInfo = new DungeonCardPanel(GS);
        dungeonInfoContainer.add(dungeonInfo);
        dungeonLayout.putConstraint(SpringLayout.WEST, dungeonInfo, -15, SpringLayout.WEST, dungeonInfoContainer);
        dungeonLayout.putConstraint(SpringLayout.NORTH, dungeonInfo, -134, SpringLayout.NORTH, dungeonInfoContainer);

        this.add(dungeonInfoContainer);

        Layout.putConstraint(SpringLayout.NORTH, dungeonInfoContainer, 20, SpringLayout.SOUTH, CardButtons[0]);
        Layout.putConstraint(SpringLayout.WEST, dungeonInfoContainer, 20, SpringLayout.WEST, this);
    }

    @Override
    public void Render() {
        Draw();
        HookEvents();
    }

    @Override
    public void DestroyView() {
        this.dispose();
    }
}
