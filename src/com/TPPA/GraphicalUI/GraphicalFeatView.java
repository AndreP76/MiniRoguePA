package com.TPPA.GraphicalUI;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Internals.InternalCommandsDictionary;
import com.TPPA.GameLogic.Internals.Monster;
import com.TPPA.GameLogic.Internals.Player;
import com.TPPA.GraphicalUI.Resources.ResourceManager;
import com.TPPA.Main;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Lídia on 29/05/2017.
 */

public class GraphicalFeatView extends GraphicalStateView {

    private SpringLayout Layout;
    private JLabel PhaseLabel;
    private TablePanel ContentPanel;
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
    private JComboBox chooseFeatMode;
    private JLabel featModeLabel;
    private MyMenu myMenu;
    private LogArea log;
    private Clip BattleClip;

    GraphicalFeatView(GameStateController GS) {
        super(GS);
        P = GS.getCurrentPlayer();
        M = GS.getCurrentMonster();

        Width = (int) (ScreenSize.getWidth() * 0.75);
        Height = (int) (ScreenSize.getHeight() * 0.75);
        startHeight = (int) (ScreenSize.getHeight() * 0.125);
        startWidth = (int) (ScreenSize.getWidth() * 0.125);


        Layout = new SpringLayout();
        ContentPanel = new TablePanel();
        ContentPanel.setLayout(Layout);
        this.setContentPane(ContentPanel);

        PhaseLabel = new JLabel();
        phaseName = "FeatPhase";
        PhaseLabel.setForeground(Color.WHITE);
        this.PhaseLabel.setText(phaseName);
        ContentPanel.add(PhaseLabel);

        skipButton = new JButton("Skip");
        ContentPanel.add(skipButton);

        TotalDiceSum = new JLabel();
        TotalDiceSum.setForeground(Color.WHITE);
        ContentPanel.add(TotalDiceSum);

        PlayerDice = new JButton[GraphicalConstants.MAX_UNLOCKED_DICE];
        DieSum = new JLabel[GraphicalConstants.MAX_UNLOCKED_DICE];

        for (int i = 0; i < GraphicalConstants.MAX_UNLOCKED_DICE; i++) {
            PlayerDice[i] = new JButton();
            DieSum[i] = new JLabel();
            DieSum[i].setForeground(Color.WHITE);
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
        monsterInfo.setForeground(Color.WHITE);
        ContentPanel.add(monsterInfo);

        boldFont = monsterInfo.getFont();
        monsterInfo.setFont(ResourceManager.YouDiedFont.deriveFont(Font.BOLD).deriveFont(boldFont.getSize() * 1.0f));

        featModeLabel = new JLabel("Choose feat mode:");
        featModeLabel.setForeground(Color.WHITE);
        ContentPanel.add(featModeLabel);
        String[] featModes = new String[2];
        featModes[0] = "Consume 2HP";
        featModes[1] = "Consume 1XP";
        chooseFeatMode = new JComboBox(featModes);
        //chooseFeatMode.setSelectedIndex(0);
        ContentPanel.add(chooseFeatMode);

        myMenu = new MyMenu(this, GS.getCurrentGameState());
        ContentPanel.add(myMenu);
        Layout.putConstraint(SpringLayout.WEST, myMenu, 0, SpringLayout.WEST, this);
        Layout.putConstraint(SpringLayout.NORTH, myMenu, 0, SpringLayout.NORTH, this);

        addListeners();
    }

    private void addListeners() //impedir que os eventos sejam chamados múltiplas vezes!!!
    {
        for (int i = 0; i < GraphicalConstants.MAX_UNLOCKED_DICE; i++) {
            PlayerDice[i].addMouseListener(new diceListener(i));
        }
        skipButton.addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.EndFeatPhase));
    }


    private void Draw() {
        if (ResourceManager.SoundAvailable) {
            BattleClip = ResourceManager.BattleThemeSong;
            BattleClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        for (int i = 0; i < GraphicalConstants.MAX_UNLOCKED_DICE; i++) {
            if (P.getUnlockedDice().size() >= i + 1) {
                PlayerDice[i].setIcon(new ImageIcon(ResourceManager.ResolveDieRollImage(P.getUnlockedDice().get(i).getLastRoll())));
                PlayerDice[i].setVisible(true);    //dice will only be visible if unlocked by player
                DieSum[i].setText("Sum: " + P.getUnlockedDice().get(i).getRollSum());
                DieSum[i].setVisible(true);
            } else {
                PlayerDice[i].setVisible(false);
                DieSum[i].setVisible(false);
            }

        }

        TotalDiceSum.setText("Total dice sum: " + P.getTotalDiceSum());

        monsterInfo.setText(M.getName() + (M.getBoss() ? "(BOSS)" : "") + "\n" + M.toString());

        Layout.putConstraint(SpringLayout.WEST, playerStats, GraphicalConstants.FRAME_SIDE_PADDING, SpringLayout.WEST, ContentPanel);
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

        Layout.putConstraint(SpringLayout.WEST, featModeLabel, 10, SpringLayout.WEST, PlayerDice[GraphicalConstants.MAX_UNLOCKED_DICE - 1]);
        Layout.putConstraint(SpringLayout.NORTH, featModeLabel, 0, SpringLayout.NORTH, PlayerDice[GraphicalConstants.MAX_UNLOCKED_DICE - 1]);

        Layout.putConstraint(SpringLayout.WEST, chooseFeatMode, 0, SpringLayout.WEST, featModeLabel);
        Layout.putConstraint(SpringLayout.NORTH, chooseFeatMode, 5, SpringLayout.SOUTH, featModeLabel);

        Layout.putConstraint(SpringLayout.EAST, skipButton, 0, SpringLayout.EAST, chooseFeatMode);
        Layout.putConstraint(SpringLayout.NORTH, skipButton, -5, SpringLayout.NORTH, TotalDiceSum);

        Layout.putConstraint(SpringLayout.EAST, currMonster, -GraphicalConstants.FRAME_SIDE_PADDING, SpringLayout.EAST, ContentPanel);
        Layout.putConstraint(SpringLayout.NORTH, currMonster, 20, SpringLayout.NORTH, ContentPanel);

        Layout.putConstraint(SpringLayout.WEST, monsterInfo, 0, SpringLayout.WEST, currMonster);
        Layout.putConstraint(SpringLayout.NORTH, monsterInfo, 20, SpringLayout.SOUTH, currMonster);

        log = new LogArea(GS);
        ContentPanel.add(log);
        Layout.putConstraint(SpringLayout.WEST, log, 30, SpringLayout.EAST, playerStats);
        Layout.putConstraint(SpringLayout.SOUTH, log, -GraphicalConstants.FRAME_SIDE_PADDING, SpringLayout.SOUTH, playerStats);


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
        if (ResourceManager.SoundAvailable) {
            ResourceManager.getNextPlayerAttack().start();
            ResourceManager.getNextMonsterHurt().start();
            BattleClip.stop();
        }
        this.dispose();
    }

    private class diceListener extends MouseAdapter {
        int index;

        public diceListener(int index) {
            this.index = index;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int feat = chooseFeatMode.getSelectedIndex();

            String command = (feat == 0 ? InternalCommandsDictionary.UseFeatLosingHP : InternalCommandsDictionary.UseFeatLosingXP);
            Main.ErrorStream.println(command);

            if (P.getUnlockedDice().size() > index) {
                if (P.getUnlockedDice().get(index).getLastRoll() == 6)
                    GS.RelayAction(InternalCommandsDictionary.ReRollDice + " " + index);
                else
                    GS.RelayAction(command + " " + index);
            }
        }
    }
}
