package com.TPPA.GraphicalUI;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Internals.InternalCommandsDictionary;
import com.TPPA.GameLogic.Internals.Monster;
import com.TPPA.GameLogic.Internals.Player;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Lídia on 29/05/2017.
 */
//TODO: finish this!!! -> Yay, more homework for me
public class GraphicalSpellView extends GraphicalStateView {
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
    private Monster M;
    private String phaseName;
    private PlayerCardPanel playerStats;
    private DungeonCardPanel currMonster;
    private JTextArea monsterInfo;
    private Font boldFont;
    private JComboBox chooseSpell;
    private JLabel chooseSpellLabel;

    public GraphicalSpellView(GameStateController GS) {
        super(GS);

        P = GS.getCurrentPlayer();
        M = GS.getCurrentMonster();

        Width = (int) (ScreenSize.getWidth() * 0.75);
        Height = (int) (ScreenSize.getHeight() * 0.75);
        startHeight = (int) (ScreenSize.getHeight() * 0.125);
        startWidth = (int) (ScreenSize.getWidth() * 0.125);


        Layout = new SpringLayout();
        ContentPanel = new JPanel();
        ContentPanel.setLayout(Layout);
        this.setContentPane(ContentPanel);

        PhaseLabel = new JLabel();
        phaseName = "FeatPhase";
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

        playerStats = new PlayerCardPanel(this.GS);
        ContentPanel.add(playerStats);

        currMonster = new DungeonCardPanel(this.GS);
        ContentPanel.add(currMonster);

        monsterInfo = new JTextArea();
        monsterInfo.setOpaque(false);
        monsterInfo.setEditable(false);
        ContentPanel.add(monsterInfo);

        boldFont = monsterInfo.getFont();
        monsterInfo.setFont(boldFont.deriveFont(Font.BOLD));

        chooseSpellLabel = new JLabel("Choose spell:");
        ContentPanel.add(chooseSpellLabel);
//        String []featModes = new String[2];
//        featModes[0] = "Consume 2HP";
//        featModes[1] = "Consume 1XP";
//        chooseFeatMode = new JComboBox(featModes);
//        chooseFeatMode.setSelectedIndex(0);
//        ContentPanel.add(chooseFeatMode);

    }

    public void Draw() {
        if (!GS.getCurrentGameState().CanUseSpell())
            GS.RelayAction(InternalCommandsDictionary.EndSpellPhase);

        Layout.putConstraint(SpringLayout.WEST, PhaseLabel, Width / 2 - phaseName.length(), SpringLayout.WEST, ContentPanel);
        Layout.putConstraint(SpringLayout.NORTH, PhaseLabel, 30, SpringLayout.NORTH, ContentPanel);

        this.setLocation(startWidth, startHeight);
        this.setPreferredSize(new Dimension(Width, Height));
        this.pack();
        this.setVisible(true);

    }

    public void HookListeners() {
        skipButton.addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.EndSpellPhase));
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
