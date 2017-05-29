package com.TPPA.GraphicalUI;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Internals.InternalCommandsDictionary;
import com.TPPA.GameLogic.Internals.Player;
import com.TPPA.GraphicalUI.Resources.ResourceManager;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Lídia on 29/05/2017.
 */
public class GraphicalFeatView extends GraphicalStateView {

    private SpringLayout Layout;
    private JLabel PhaseLabel;
    private JPanel ContentPanel;
    private JButton[] PlayerDice;   //show Roll results
    private JButton skipButton;

    GraphicalFeatView(GameStateController GS) {
        super(GS);
    }

    private void Draw() {
        Player P = GS.getCurrentPlayer();
        int startWidth = 0;
        int startHeight = 0;
        int Width = 0;
        int Height = 0;

        Width = (int) (ScreenSize.getWidth() * 0.75);
        Height = (int) (ScreenSize.getHeight() * 0.75);
        startHeight = (int) (ScreenSize.getHeight() * 0.125);
        startWidth = (int) (ScreenSize.getWidth() * 0.125);


        Layout = new SpringLayout();
        ContentPanel = new JPanel();
        ContentPanel.setLayout(Layout);
        this.setContentPane(ContentPanel);

        PhaseLabel = new JLabel();
        String phaseName = "FeatPhase";
        this.PhaseLabel.setText(phaseName);
        ContentPanel.add(PhaseLabel);

        skipButton = new JButton("Skip");
        ContentPanel.add(skipButton);


        PlayerDice = new JButton[4];
        for (int i = 0; i < PlayerDice.length; i++) {
            PlayerDice[i] = new JButton();
            ContentPanel.add(PlayerDice[i]);
            if (P.getUnlockedDice().size() >= i + 1) {
                PlayerDice[i].setIcon(new ImageIcon(ResourceManager.ResolveDieRollImage(P.getUnlockedDice().get(i).getLastRoll())));
                PlayerDice[i].setPreferredSize(new Dimension(60, 60));
                PlayerDice[i].setVisible(true);    //dice will only be visible if unlocked by player
                if (P.getUnlockedDice().get(i).getLastRoll() == 6)
                    PlayerDice[i].setEnabled(true);
                else
                    PlayerDice[i].setEnabled(false);
            } else
                PlayerDice[i].setVisible(false);

        }

        Layout.putConstraint(SpringLayout.WEST, PhaseLabel, Width / 2 - phaseName.length(), SpringLayout.WEST, ContentPanel);
        Layout.putConstraint(SpringLayout.NORTH, PhaseLabel, 30, SpringLayout.NORTH, ContentPanel);

        Layout.putConstraint(SpringLayout.WEST, PlayerDice[0], 5, SpringLayout.WEST, ContentPanel);
        Layout.putConstraint(SpringLayout.NORTH, PlayerDice[0], Height - 260, SpringLayout.NORTH, ContentPanel);
        Layout.putConstraint(SpringLayout.WEST, PlayerDice[1], 5, SpringLayout.EAST, PlayerDice[0]);
        Layout.putConstraint(SpringLayout.NORTH, PlayerDice[1], Height - 260, SpringLayout.NORTH, ContentPanel);
        Layout.putConstraint(SpringLayout.WEST, PlayerDice[2], 5, SpringLayout.EAST, PlayerDice[1]);
        Layout.putConstraint(SpringLayout.NORTH, PlayerDice[2], Height - 260, SpringLayout.NORTH, ContentPanel);
        Layout.putConstraint(SpringLayout.WEST, PlayerDice[3], 5, SpringLayout.EAST, PlayerDice[2]);
        Layout.putConstraint(SpringLayout.NORTH, PlayerDice[3], Height - 260, SpringLayout.NORTH, ContentPanel);

        Layout.putConstraint(SpringLayout.WEST, skipButton, 20, SpringLayout.WEST, ContentPanel);
        Layout.putConstraint(SpringLayout.NORTH, skipButton, 20, SpringLayout.SOUTH, PlayerDice[0]);

        this.setLocation(startWidth, startHeight);
        this.setPreferredSize(new Dimension(Width, Height));
        this.pack();
        this.setVisible(true);
    }

    private void HookListeners() {
        PlayerDice[0].addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.ReRollDice + " " + 0));
        PlayerDice[1].addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.ReRollDice + " " + 1));
        PlayerDice[2].addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.ReRollDice + " " + 2));
        PlayerDice[3].addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.ReRollDice + " " + 3));

        skipButton.addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.EndRollPhase));
    }

    @Override
    public void Render() {

    }

    @Override
    public void DestroyView() {
        this.dispose();
    }
}
