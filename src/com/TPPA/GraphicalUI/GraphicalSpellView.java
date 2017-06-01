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
    private JButton skipButton;
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
    private JLabel SpellLabel;
    private JButton useSpell;

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
        phaseName = "SpellPhase";
        this.PhaseLabel.setText(phaseName);
        ContentPanel.add(PhaseLabel);

        skipButton = new JButton("Skip");
        ContentPanel.add(skipButton);

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

        startChooseSpell();

        addListeners();

    }

    private void startChooseSpell() {
        int nSpells = P.getSpellsInventory().size();

        SpellLabel = new JLabel("Choose Spell:");
        ContentPanel.add(SpellLabel);

        chooseSpell = new JComboBox();
        if (nSpells >= 1) {
            String[] spells = new String[nSpells];
            for (int i = 0; i < nSpells; i++) {
                spells[i] = P.getSpellsInventory().get(i).getSpellID();
                chooseSpell.addItem(spells[i]);
            }
        }

        ContentPanel.add(chooseSpell);

        useSpell = new JButton("Use Spell");
        ContentPanel.add(useSpell);
    }

    private void addListeners() //impedir que os eventos sejam chamados múltiplas vezes!!!
    {
        if (P.getSpellsInventory().size() >= 1) {
            int index = chooseSpell.getSelectedIndex();
            useSpell.addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.UseSpell + " " + index));
        }

        skipButton.addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.EndSpellPhase));
    }

    public void Draw() {

        if (!GS.getCurrentGameState().CanUseSpell()) {
            GS.RelayAction(InternalCommandsDictionary.EndSpellPhase);
            return;
        }

        monsterInfo.setText(M.getName() + (M.getBoss() ? "(BOSS)" : "") + "\n" + M.toString());

        Layout.putConstraint(SpringLayout.WEST, playerStats, GraphicalConstants.FRAME_SIDE_PADDING, SpringLayout.WEST, ContentPanel);
        Layout.putConstraint(SpringLayout.NORTH, playerStats, 20, SpringLayout.NORTH, ContentPanel);

        Layout.putConstraint(SpringLayout.WEST, PhaseLabel, Width / 2 - phaseName.length(), SpringLayout.WEST, ContentPanel);
        Layout.putConstraint(SpringLayout.NORTH, PhaseLabel, 20, SpringLayout.NORTH, ContentPanel);

        Layout.putConstraint(SpringLayout.WEST, SpellLabel, 20, SpringLayout.WEST, playerStats);
        Layout.putConstraint(SpringLayout.NORTH, SpellLabel, 20, SpringLayout.SOUTH, playerStats);

        Layout.putConstraint(SpringLayout.WEST, chooseSpell, 0, SpringLayout.WEST, SpellLabel);
        Layout.putConstraint(SpringLayout.NORTH, chooseSpell, 5, SpringLayout.SOUTH, SpellLabel);

        Layout.putConstraint(SpringLayout.WEST, useSpell, 20, SpringLayout.EAST, chooseSpell);
        Layout.putConstraint(SpringLayout.NORTH, useSpell, 0, SpringLayout.NORTH, chooseSpell);

        Layout.putConstraint(SpringLayout.WEST, skipButton, 0, SpringLayout.WEST, chooseSpell);
        Layout.putConstraint(SpringLayout.NORTH, skipButton, 20, SpringLayout.SOUTH, chooseSpell);

        Layout.putConstraint(SpringLayout.EAST, currMonster, -GraphicalConstants.FRAME_SIDE_PADDING, SpringLayout.EAST, ContentPanel);
        Layout.putConstraint(SpringLayout.NORTH, currMonster, 20, SpringLayout.NORTH, ContentPanel);

        Layout.putConstraint(SpringLayout.WEST, monsterInfo, 0, SpringLayout.WEST, currMonster);
        Layout.putConstraint(SpringLayout.NORTH, monsterInfo, 20, SpringLayout.SOUTH, currMonster);


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
