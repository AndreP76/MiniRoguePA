package com.TPPA.GraphicalUI;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Internals.Monster;
import com.TPPA.GraphicalUI.Resources.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Lídia on 30/05/2017.
 */
public class DungeonCardPanel extends JPanel implements Observer {
    private Image backgroundImage;
    private GameStateController GS;
    private Monster M;
    private SpringLayout Layout;
    private JPanel RoomPanel;
    private SpringLayout RoomLayout;
    private JPanel HpPanel;
    private SpringLayout HpLayout;

    public DungeonCardPanel(GameStateController GS) {
        super();

        this.GS = GS;
        this.GS.addObserver(this);
        M = this.GS.getCurrentMonster();

        Layout = new SpringLayout();
        this.setLayout(Layout);
        this.setPreferredSize(new Dimension(GraphicalConstants.PANEL_WIDTH, GraphicalConstants.PANEL_HEIGHT));

        backgroundImage = ResourceManager.getDungeonCardBig();

        startHpPanel();
        startRoomPanel();

        this.setVisible(true);
    }


    private void startHpPanel() {
        this.HpPanel = new JPanel();
        this.HpLayout = new SpringLayout();

        HpPanel.setLayout(HpLayout);

        HpPanel.setPreferredSize(new Dimension(GraphicalConstants.DUNGEON_HP_WIDTH, GraphicalConstants.DUNGEON_HP_HEIGHT));

        this.add(HpPanel);
        HpPanel.setOpaque(false);

        Layout.putConstraint(SpringLayout.EAST, HpPanel, -GraphicalConstants.DUNGEON_RIGHT_PADD, SpringLayout.EAST, this);
        Layout.putConstraint(SpringLayout.NORTH, HpPanel, GraphicalConstants.DUNGEON_TOP_PADD, SpringLayout.NORTH, this);
    }

    private void startRoomPanel() {
        this.RoomPanel = new JPanel();
        this.RoomLayout = new SpringLayout();

        RoomPanel.setLayout(RoomLayout);

        RoomPanel.setPreferredSize(new Dimension(GraphicalConstants.DUNGEON_ROOM_WIDTH, GraphicalConstants.DUNGEON_ROOM_HEIGHT));

        this.add(RoomPanel);
        RoomPanel.setOpaque(false);

        Layout.putConstraint(SpringLayout.WEST, RoomPanel, GraphicalConstants.DUNGEON_LEFT_PADD, SpringLayout.WEST, this);
        Layout.putConstraint(SpringLayout.SOUTH, RoomPanel, -GraphicalConstants.DUNGEON_BOTTOM_PADD, SpringLayout.SOUTH, this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);
    }

    @Override
    public void update(Observable observable, Object o) {
        drawHpPanel();
        drawRoomPanel();
    }

    private void drawRoomPanel() {
        int room, zone;
        room = this.GS.getCurrentRoom();
        zone = this.GS.getCurrentZone();

        Token mark = new Token("" + GS.getTrueRoom());
        RoomPanel.add(mark);

        int x = 0;
        x += (room - 1) * (GraphicalConstants.SMALL_PANEL_WIDTH + GraphicalConstants.DUNGEON_HP_INTER_PADD);

        int y = 0;
        y += (zone - 1) * (GraphicalConstants.SMALL_PANEL_WIDTH + GraphicalConstants.DUNGEON_ROOM_INTER_PADD);

        RoomLayout.putConstraint(SpringLayout.WEST, mark, x, SpringLayout.WEST, RoomPanel);
        RoomLayout.putConstraint(SpringLayout.NORTH, mark, y, SpringLayout.NORTH, RoomPanel);
    }

    private void drawHpPanel() {
        int HP = M.getHPCurr();
        if (HP < 0)
            HP = 0;

        Token mark = new Token("" + HP);
        HpPanel.add(mark);

        int x = 0;
        if (HP <= 10)
            x += 2 * (GraphicalConstants.SMALL_PANEL_WIDTH + GraphicalConstants.DUNGEON_HP_INTER_PADD);
        else if (HP <= 20)
            x += GraphicalConstants.SMALL_PANEL_WIDTH + GraphicalConstants.DUNGEON_HP_INTER_PADD;

        int y = 0;
        if ((HP == 10) || (HP == 20) || (HP == 30))    //para token não ficar na base do painel quando HP%10 = 0
            y = 11;
        else
            y += (HP % 10) + 1;

        HpLayout.putConstraint(SpringLayout.WEST, mark, x, SpringLayout.WEST, HpPanel);
        HpLayout.putConstraint(SpringLayout.NORTH, mark, GraphicalConstants.DUNGEON_HP_HEIGHT - ((GraphicalConstants.SMALL_PANEL_WIDTH + GraphicalConstants.DUNGEON_HP_INTER_PADD) * y), SpringLayout.NORTH, HpPanel);
    }
}
