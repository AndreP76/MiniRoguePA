package com.TPPA.GraphicalUI;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Internals.InternalCommandsDictionary;
import com.TPPA.GameLogic.Internals.Player;

import javax.swing.*;
import java.awt.*;

/**
 * Created by andre on 5/20/17.
 */
public class GraphicalRestingStateView extends GraphicalStateView {
    private JButton gainXP;
    private JButton gainHP;
    private JButton gainFood;
    private JPanel ContentPanel;
    private SpringLayout Layout;
    private JLabel PhaseLabel;
    private Player P;
    private String phaseName;
    private PlayerCardPanel playerStats;
    private MyMenu myMenu;
    private int startWidth;
    private int startHeight;
    private int Width;
    private int Height;
    private int ButtonWidth;
    private int ButtonHeight;
    private int PaddingWidth;

    GraphicalRestingStateView(GameStateController GS) {
        super(GS);

        P = GS.getCurrentPlayer();

        Width = (int) (ScreenSize.getWidth() * 0.75);
        Height = (int) (ScreenSize.getHeight() * 0.75);
        startHeight = (int) (ScreenSize.getHeight() * 0.125);
        startWidth = (int) (ScreenSize.getWidth() * 0.125);
        ButtonWidth = (int) ((Width / 2) * 0.75);
        ButtonHeight = Height / 8;
        PaddingWidth = 20;


        Layout = new SpringLayout();
        ContentPanel = new JPanel();
        ContentPanel.setLayout(Layout);
        this.setContentPane(ContentPanel);

        PhaseLabel = new JLabel();
        phaseName = "Resting State";
        this.PhaseLabel.setText(phaseName);
        ContentPanel.add(PhaseLabel);

        playerStats = new PlayerCardPanel(this.GS);
        ContentPanel.add(playerStats);

        myMenu = new MyMenu(this, GS.getCurrentGameState());
        ContentPanel.add(myMenu);
        Layout.putConstraint(SpringLayout.WEST, myMenu, 0, SpringLayout.WEST, this);
        Layout.putConstraint(SpringLayout.NORTH, myMenu, 0, SpringLayout.NORTH, this);

        gainXP = new JButton("Reinforce your weapon: +1XP");
        gainHP = new JButton("Heal: +2HP");
        gainFood = new JButton("Search for ration: +1Food");
        gainXP.setPreferredSize(new Dimension(ButtonWidth, ButtonHeight));
        gainHP.setPreferredSize(new Dimension(ButtonWidth, ButtonHeight));
        gainFood.setPreferredSize(new Dimension(ButtonWidth, ButtonHeight));
        ContentPanel.add(gainXP);
        ContentPanel.add(gainFood);
        ContentPanel.add(gainHP);

        addListeners();

        Draw();
    }

    private void addListeners() {
        gainXP.addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.ReinforceWeapon));
        gainFood.addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.SearchRation));
        gainHP.addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.Heal));
    }

    public void Draw() {
        Layout.putConstraint(SpringLayout.WEST, playerStats, GraphicalConstants.FRAME_SIDE_PADDING, SpringLayout.WEST, this);
        Layout.putConstraint(SpringLayout.NORTH, playerStats, 40, SpringLayout.NORTH, this);

        Layout.putConstraint(SpringLayout.WEST, gainXP, GraphicalConstants.FRAME_SIDE_PADDING * 2, SpringLayout.EAST, playerStats);
        Layout.putConstraint(SpringLayout.NORTH, gainXP, 40, SpringLayout.SOUTH, PhaseLabel);

        Layout.putConstraint(SpringLayout.WEST, gainFood, GraphicalConstants.FRAME_SIDE_PADDING * 2, SpringLayout.EAST, playerStats);
        Layout.putConstraint(SpringLayout.NORTH, gainFood, PaddingWidth, SpringLayout.SOUTH, gainXP);

        Layout.putConstraint(SpringLayout.WEST, gainHP, GraphicalConstants.FRAME_SIDE_PADDING * 2, SpringLayout.EAST, playerStats);
        Layout.putConstraint(SpringLayout.NORTH, gainHP, PaddingWidth, SpringLayout.SOUTH, gainFood);

        Layout.putConstraint(SpringLayout.WEST, PhaseLabel, Width / 2 - phaseName.length(), SpringLayout.WEST, this);
        Layout.putConstraint(SpringLayout.NORTH, PhaseLabel, 20, SpringLayout.NORTH, this);

        this.setLocation(startWidth, startHeight);
        this.setPreferredSize(new Dimension(Width, Height));
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void Render() {
        Draw();
    }

    @Override
    public void DestroyView() {
        this.dispose();
    }
}
