package com.TPPA.GraphicalUI;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Internals.DifficultyLevelEnum;
import com.TPPA.GameLogic.Internals.InternalCommandsDictionary;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

/**
 * Created by andre on 5/18/17.
 */
public class GraphicalStartStateView extends GraphicalStateView {
    private JSlider DiffSlider;
    private JButton SetDiffButton;
    private JButton StartButton;
    private JButton SetAreaButton;
    private JSpinner StartAreaSpinner;
    private JButton LoadButton;
    private JPanel ContentPanel;
    private SpringLayout Layout;

    private JLabel DifficultyLabel;
    private JLabel AreaLabel;

    private Boolean DiffSliderShowing = false;
    private Boolean AreaSpinnerShowing = false;

    public GraphicalStartStateView(GameStateController GS) {
        super(GS);
    }

    private void Draw() {
        int gapSize = 7;
        int startWidth = 0;
        int startHeight = 0;
        int Width = 0;
        int Height = 0;

        Width = (int) (ScreenSize.getWidth() * 0.5);
        Height = (int) (ScreenSize.getHeight() * 0.75);
        startHeight = (int) (ScreenSize.getHeight() * 0.125);
        startWidth = (int) (ScreenSize.getWidth() * 0.25);

        Layout = new SpringLayout();
        ContentPanel = new JPanel();
        ContentPanel.setLayout(Layout);
        this.setContentPane(ContentPanel);

        DifficultyLabel = new JLabel();
        DifficultyLabel.setText(GS.getGameDifficulty().toString());
        Layout.putConstraint(SpringLayout.WEST, DifficultyLabel, 0, SpringLayout.WEST, ContentPanel);
        Layout.putConstraint(SpringLayout.NORTH, DifficultyLabel, 0, SpringLayout.NORTH, ContentPanel);
        ContentPanel.add(DifficultyLabel);

        AreaLabel = new JLabel();
        AreaLabel.setText(GS.getZoneString(GS.getCurrentZone()));
        Layout.putConstraint(SpringLayout.NORTH, AreaLabel, gapSize, SpringLayout.SOUTH, DifficultyLabel);
        Layout.putConstraint(SpringLayout.WEST, AreaLabel, 0, SpringLayout.WEST, ContentPanel);
        ContentPanel.add(AreaLabel);

        Dimension buttonSize = new Dimension(Width / 3, Height / 7);
        int buttonStartX = gapSize + (Width / 3);
        int buttonStartY = gapSize;
        StartButton = new JButton("Start game");
        StartButton.setPreferredSize(buttonSize);
        Layout.putConstraint(SpringLayout.WEST, StartButton, buttonStartX, SpringLayout.WEST, ContentPanel);
        Layout.putConstraint(SpringLayout.NORTH, StartButton, buttonStartY, SpringLayout.NORTH, ContentPanel);
        ContentPanel.add(StartButton);

        SetDiffButton = new JButton("Set Game Difficulty");
        SetDiffButton.setPreferredSize(buttonSize);
        buttonStartY += gapSize + (Height / 7);
        Layout.putConstraint(SpringLayout.WEST, SetDiffButton, buttonStartX, SpringLayout.WEST, ContentPanel);
        Layout.putConstraint(SpringLayout.NORTH, SetDiffButton, buttonStartY, SpringLayout.NORTH, ContentPanel);
        ContentPanel.add(SetDiffButton);

        DiffSlider = new JSlider();
        DiffSlider.setMajorTickSpacing(1);
        DiffSlider.setMinorTickSpacing(1);
        DiffSlider.setSnapToTicks(true);
        Hashtable<Integer, JLabel> LabelTable = new Hashtable<>();
        for (int i = 0; i < DifficultyLevelEnum.values().length - 1; ++i) {
            LabelTable.put(i + 1, new JLabel(DifficultyLevelEnum.values()[i].toString()));
        }
        DiffSlider.setLabelTable(LabelTable);
        DiffSlider.setPaintLabels(true);
        Layout.putConstraint(SpringLayout.WEST, DiffSlider, gapSize, SpringLayout.EAST, SetDiffButton);
        Layout.putConstraint(SpringLayout.NORTH, DiffSlider, buttonStartY, SpringLayout.NORTH, ContentPanel);
        Layout.putConstraint(SpringLayout.EAST, DiffSlider, gapSize, SpringLayout.EAST, ContentPanel);
        DiffSlider.setMaximum(DifficultyLevelEnum.values().length - 1);
        DiffSlider.setMinimum(1);
        DiffSlider.setVisible(false);
        ContentPanel.add(DiffSlider);

        SetAreaButton = new JButton("Set Starting Area");
        SetAreaButton.setPreferredSize(buttonSize);
        buttonStartY += gapSize + (Height / 7);
        Layout.putConstraint(SpringLayout.WEST, SetAreaButton, buttonStartX, SpringLayout.WEST, ContentPanel);
        Layout.putConstraint(SpringLayout.NORTH, SetAreaButton, buttonStartY, SpringLayout.NORTH, ContentPanel);
        ContentPanel.add(SetAreaButton);

        StartAreaSpinner = new JSpinner();
        Layout.putConstraint(SpringLayout.WEST, StartAreaSpinner, gapSize, SpringLayout.EAST, SetAreaButton);
        Layout.putConstraint(SpringLayout.NORTH, StartAreaSpinner, (buttonSize.height / 2) - StartAreaSpinner.getHeight() / 2, SpringLayout.NORTH, SetAreaButton);
        StartAreaSpinner.setValue(GS.getCurrentZone());
        StartAreaSpinner.setVisible(false);
        ContentPanel.add(StartAreaSpinner);

        LoadButton = new JButton("Load game");
        LoadButton.setPreferredSize(buttonSize);
        buttonStartY += gapSize + (Height / 7);
        Layout.putConstraint(SpringLayout.WEST, LoadButton, buttonStartX, SpringLayout.WEST, ContentPanel);
        Layout.putConstraint(SpringLayout.NORTH, LoadButton, buttonStartY, SpringLayout.NORTH, ContentPanel);
        ContentPanel.add(LoadButton);

        this.setLocation(startWidth, startHeight);
        this.setPreferredSize(new Dimension(Width, Height));
        this.pack();
        this.setVisible(true);

    }

    private void HookListeners() {
        //Lambdas Master Race
        SetDiffButton.addActionListener(actionEvent -> {
            if (DiffSliderShowing) {
                DiffSliderShowing = false;
                DiffSlider.setVisible(false);
                GS.RelayAction(InternalCommandsDictionary.SetDifficultyCommand + " " + (DiffSlider.getValue()));
            } else {
                DiffSliderShowing = true;
                DiffSlider.setVisible(true);
            }
        });
        DiffSlider.addChangeListener(changeEvent -> DifficultyLabel.setText(DifficultyLevelEnum.values()[DiffSlider.getValue() - 1].toString()));
        StartButton.addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.StartCommand));
        StartAreaSpinner.addChangeListener(changeEvent -> {
            int spinnerArea = (int) StartAreaSpinner.getValue();
            if (spinnerArea < 1) {
                spinnerArea = 1;
                StartAreaSpinner.setValue(spinnerArea);
            }
            if (spinnerArea > 5) {
                spinnerArea = 5;
                StartAreaSpinner.setValue(spinnerArea);
            }
            AreaLabel.setText(GS.getZoneString(spinnerArea));
        });
        SetAreaButton.addActionListener(actionEvent -> {
            if (AreaSpinnerShowing) {
                StartAreaSpinner.setVisible(false);
                AreaSpinnerShowing = false;
                GS.RelayAction(InternalCommandsDictionary.SetAreaCommand + " " + StartAreaSpinner.getValue());
            } else {
                StartAreaSpinner.setVisible(true);
                AreaSpinnerShowing = true;
            }
        });
        LoadButton.addActionListener(actionEvent -> {
            JFileChooser JFC = new JFileChooser();
            JFC.showOpenDialog(ContentPanel);
            GS.RelayAction(InternalCommandsDictionary.LoadCommand + " " + (JFC.getSelectedFile()));
        });
    }

    public void DestroyView() {
        this.dispose();
    }

    @Override
    public void Render() {
        Draw();
        HookListeners();
    }
}
