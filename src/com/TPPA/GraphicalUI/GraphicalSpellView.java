package com.TPPA.GraphicalUI;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Internals.InternalCommandsDictionary;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Lídia on 29/05/2017.
 */
//TODO: finish this!!!
public class GraphicalSpellView extends GraphicalStateView {
    private SpringLayout Layout;
    private JLabel PhaseLabel;
    private JPanel ContentPanel;
    private JButton skipButton;
    private int startWidth;
    private int startHeight;
    private int Width;
    private int Height;
    private String phaseName;

    public GraphicalSpellView(GameStateController GS) {
        super(GS);

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

    }

    public void Draw() {
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
