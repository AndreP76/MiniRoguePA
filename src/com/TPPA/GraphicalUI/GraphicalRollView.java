package com.TPPA.GraphicalUI;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Internals.InternalCommandsDictionary;
import com.TPPA.GameLogic.Internals.Player;
import com.TPPA.GraphicalUI.Resources.ResourceManager;

import javax.swing.*;
import java.awt.*;

/**
 * Created by andre on 5/20/17.
 */
//TODO: Falta mostrar monster/dungeon card
public class GraphicalRollView extends GraphicalStateView {

    private SpringLayout Layout;
    private JLabel PhaseLabel;
    private JPanel ContentPanel;
    private JButton[] PlayerDice;   //show Roll results
    private JButton skipButton;
    private JLabel[] DieSum;
    private JLabel TotalDiceSum;
    private int startWidth;
    private int startHeight;
    private int Width;
    private int Height;
    private Player P;
    private String phaseName;
    private PlayerCardPanel playerStats;


    GraphicalRollView(GameStateController GS) {
        super(GS);

        P = GS.getCurrentPlayer();

        Width = (int) (ScreenSize.getWidth() * 0.75);
        Height = (int) (ScreenSize.getHeight() * 0.75);
        startHeight = (int) (ScreenSize.getHeight() * 0.125);
        startWidth = (int) (ScreenSize.getWidth() * 0.125);


        Layout = new SpringLayout();
        ContentPanel = new JPanel();
        ContentPanel.setLayout(Layout);
        this.setContentPane(ContentPanel);

        PhaseLabel = new JLabel();
        phaseName = "RollPhase";
        this.PhaseLabel.setText(phaseName);
        ContentPanel.add(PhaseLabel);

        skipButton = new JButton("Skip");
        ContentPanel.add(skipButton);

        TotalDiceSum = new JLabel();
        ContentPanel.add(TotalDiceSum);

        PlayerDice = new JButton[GraphicalConstants.MAX_UNLOCKED_DICE];
        DieSum = new JLabel[GraphicalConstants.MAX_UNLOCKED_DICE];

        for (int i = 0; i < GraphicalConstants.MAX_UNLOCKED_DICE; i++) {
            PlayerDice[i] = new JButton();
            DieSum[i] = new JLabel();
            PlayerDice[i].setPreferredSize(new Dimension(60, 60));
            ContentPanel.add(PlayerDice[i]);
            ContentPanel.add(DieSum[i]);
        }

        playerStats = new PlayerCardPanel(GS);
        ContentPanel.add(playerStats);

    }

    private void Draw() {


        if (!P.hasRolledDice())  //roll dice automatically when entering this phase
            GS.RelayAction(InternalCommandsDictionary.RollDice);


        for (int i = 0; i < GraphicalConstants.MAX_UNLOCKED_DICE; i++) {
            if (P.getUnlockedDice().size() >= i + 1) {
                PlayerDice[i].setIcon(new ImageIcon(ResourceManager.ResolveDieRollImage(P.getUnlockedDice().get(i).getLastRoll())));
                PlayerDice[i].setVisible(true);    //dice will only be visible if unlocked by player
                DieSum[i].setText("Sum: " + P.getUnlockedDice().get(i).getRollSum());
                DieSum[i].setVisible(true);

                PlayerDice[i].setEnabled(P.getUnlockedDice().get(i).getLastRoll() == 6);
            } else {
                PlayerDice[i].setVisible(false);
                DieSum[i].setVisible(false);
            }

        }

        TotalDiceSum.setText("Total dice sum: " + P.getTotalDiceSum());

        Layout.putConstraint(SpringLayout.WEST, playerStats, 20, SpringLayout.WEST, ContentPanel);
        Layout.putConstraint(SpringLayout.NORTH, playerStats, 20, SpringLayout.NORTH, ContentPanel);

        Layout.putConstraint(SpringLayout.WEST, PhaseLabel, Width / 2 - phaseName.length(), SpringLayout.WEST, ContentPanel);
        Layout.putConstraint(SpringLayout.NORTH, PhaseLabel, 20, SpringLayout.NORTH, ContentPanel);

        Layout.putConstraint(SpringLayout.WEST, PlayerDice[0], 0, SpringLayout.WEST, playerStats);
        Layout.putConstraint(SpringLayout.NORTH, PlayerDice[0], 10, SpringLayout.SOUTH, playerStats);
        Layout.putConstraint(SpringLayout.WEST, DieSum[0], 0, SpringLayout.WEST, PlayerDice[0]);
        Layout.putConstraint(SpringLayout.NORTH, DieSum[0], 5, SpringLayout.SOUTH, PlayerDice[0]);
        for (int i = 1; i < GraphicalConstants.MAX_UNLOCKED_DICE; i++) {
            Layout.putConstraint(SpringLayout.WEST, PlayerDice[i], 5, SpringLayout.EAST, PlayerDice[i - 1]);
            Layout.putConstraint(SpringLayout.NORTH, PlayerDice[i], 10, SpringLayout.SOUTH, playerStats);
            Layout.putConstraint(SpringLayout.WEST, DieSum[i], 0, SpringLayout.WEST, PlayerDice[i]);
            Layout.putConstraint(SpringLayout.NORTH, DieSum[i], 5, SpringLayout.SOUTH, PlayerDice[i]);
        }

        Layout.putConstraint(SpringLayout.WEST, TotalDiceSum, 0, SpringLayout.WEST, DieSum[0]);
        Layout.putConstraint(SpringLayout.NORTH, TotalDiceSum, 5, SpringLayout.SOUTH, DieSum[0]);

        Layout.putConstraint(SpringLayout.WEST, skipButton, 5, SpringLayout.EAST, PlayerDice[GraphicalConstants.MAX_UNLOCKED_DICE - 1]);
        Layout.putConstraint(SpringLayout.NORTH, skipButton, -5, SpringLayout.NORTH, TotalDiceSum);

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
        Draw();
        HookListeners();
    }

    @Override
    public void DestroyView() {
        this.dispose();
    }
}
