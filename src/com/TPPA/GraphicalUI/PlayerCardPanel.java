package com.TPPA.GraphicalUI;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Internals.Player;
import com.TPPA.GraphicalUI.Resources.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Lídia on 30/05/2017.
 */
//TODO: desenhar grelha; colocar tokens nas posições corretas
public class PlayerCardPanel extends JPanel implements Observer {
    private static final int PANEL_HEIGHT = 385;
    private static final int PANEL_WIDTH = 275;
    private static final int GRID_ROWS = 11;
    private static final int GRID_COLS = 8;

    private Image backgroundImage;
    private GameStateController GS;
    private Player P;
    private GridLayout Layout;


    public PlayerCardPanel(GameStateController GS) {
        super();

        this.GS = GS;
        P = GS.getCurrentPlayer();
        backgroundImage = ResourceManager.getPlayerCardBig();
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        Layout = new GridLayout(GRID_ROWS, GRID_COLS);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
