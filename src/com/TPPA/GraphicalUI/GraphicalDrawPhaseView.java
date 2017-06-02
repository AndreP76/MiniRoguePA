package com.TPPA.GraphicalUI;

import com.TPPA.GameLogic.Cards.CardBase;
import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Internals.InternalCommandsDictionary;
import com.TPPA.GameLogic.Internals.Player;
import com.TPPA.GameLogic.Internals.Utils;
import com.TPPA.GraphicalUI.Resources.ResourceManager;

import javax.swing.*;
import java.awt.*;

/**
 * Created by andre on 5/20/17.
 */

//TODO: Aumentar um bocadinho a resolução? Talvez para 1280*720?
public class GraphicalDrawPhaseView extends GraphicalStateView {
    private SpringLayout Layout;
    private JLabel HPLabel;
    private JLabel GoldIconLabel;
    private JLabel XPLabel;
    private JLabel ArmorLabel;
    private JProgressBar HPBar;
    private JProgressBar ArmorBar;
    private JLabel FoodLabel;
    private JTextArea Log;

    private JPanel ContentPanel;
    private JButton[] CardButtons;

    private Menu menu;

    GraphicalDrawPhaseView(GameStateController GS) {
        super(GS);
        Draw();
        HookEvents();
    }

    private void Draw() {
        Player P = GS.getCurrentPlayer();
        int gapSize = 7;
        int startWidth = 0;
        int startHeight = 0;
        int Width = 0;
        int Height = 0;

        Width = (int) (ScreenSize.getWidth() * 0.75);
        Height = (int) (ScreenSize.getHeight() * 0.75);
        startHeight = (int) (ScreenSize.getHeight() * 0.125);
        startWidth = (int) (ScreenSize.getWidth() * 0.125);

        //draw the Stats menu
        Layout = new SpringLayout();
        ContentPanel = new JPanel();
        ContentPanel.setLayout(Layout);
        this.setContentPane(ContentPanel);

        HPLabel = new JLabel();
        HPLabel.setIcon(new ImageIcon(ResourceManager.HPIcon));
        ContentPanel.add(HPLabel);

        HPBar = new JProgressBar();
        HPBar.setMaximum(P.getMaxHP());
        HPBar.setMinimum(0);
        HPBar.setValue(P.getHP());
        ContentPanel.add(HPBar);

        XPLabel = new JLabel();
        XPLabel.setIcon(new ImageIcon(ResourceManager.XPIcon));
        XPLabel.setText("" + P.getArmor());
        ContentPanel.add(XPLabel);

        ArmorLabel = new JLabel();
        ArmorLabel.setIcon(new ImageIcon(ResourceManager.ArmorIcon));
        ContentPanel.add(ArmorLabel);

        GoldIconLabel = new JLabel();
        GoldIconLabel.setIcon(new ImageIcon(ResourceManager.GoldIcon));
        GoldIconLabel.setText("" + P.getGold());
        ContentPanel.add(GoldIconLabel);

        FoodLabel = new JLabel();
        FoodLabel.setIcon(new ImageIcon(ResourceManager.FoodIcon));
        FoodLabel.setText("" + P.getFood());
        ContentPanel.add(FoodLabel);

        CardBase[] CBLinear = Utils.MakeLinear(GS.getRoomStages());
        CardButtons = new JButton[CBLinear.length];


        for (int i = 0; i < CBLinear.length; ++i) {
            CardBase CardHere;
            CardHere = CBLinear[i];
            CardButtons[i] = new JButton();
            CardButtons[i].setIcon(new ImageIcon(ResourceManager.ResolveCardImage(CardHere.getCardID())));
            //It should be Card Back, but let's test first...
            ContentPanel.add(CardButtons[i]);
        }

        //Layout math starts here...
        int minorGap = 3;
        int majorGap = 9;
        Layout.putConstraint(SpringLayout.WEST, HPLabel, minorGap, SpringLayout.WEST, ContentPanel);
        Layout.putConstraint(SpringLayout.NORTH, HPLabel, minorGap, SpringLayout.NORTH, ContentPanel);
        //Layout.putConstraint(SpringLayout.SOUTH,HPLabel,(int)(ContentPanel.getHeight() - HPLabel.getVisibleRect().getFrame().getMaxY()),SpringLayout.SOUTH,ContentPanel);

        Layout.putConstraint(SpringLayout.WEST, GoldIconLabel, minorGap, SpringLayout.WEST, ContentPanel);
        Layout.putConstraint(SpringLayout.NORTH, GoldIconLabel, minorGap, SpringLayout.SOUTH, HPLabel);
        Layout.putConstraint(SpringLayout.WEST, XPLabel, minorGap, SpringLayout.WEST, ContentPanel);
        Layout.putConstraint(SpringLayout.NORTH, XPLabel, minorGap, SpringLayout.SOUTH, GoldIconLabel);
        //this is hurting my head...
        Layout.putConstraint(SpringLayout.WEST, HPBar, minorGap, SpringLayout.EAST, HPLabel);
        Layout.putConstraint(SpringLayout.NORTH, HPBar, 0, SpringLayout.NORTH, HPLabel);
        Layout.putConstraint(SpringLayout.WEST, ArmorLabel, minorGap, SpringLayout.EAST, GoldIconLabel);
        Layout.putConstraint(SpringLayout.NORTH, ArmorLabel, 0, SpringLayout.NORTH, GoldIconLabel);
        Layout.putConstraint(SpringLayout.WEST, FoodLabel, minorGap, SpringLayout.EAST, XPLabel);
        Layout.putConstraint(SpringLayout.NORTH, FoodLabel, 0, SpringLayout.NORTH, XPLabel);
        //somebody send help
        //F̸̡̢̭͉͙̹̝̱̯̒͘͜u̷̡̧̞̱̻͚͍̙͎̖͕͂c̴̡̬̖̼̦̖͚̥͚̣̣̦̦̞̿k̷̡̜͆ ̸̟͎̗̲̪͑̉J̴͔͉̉̒̊̿̌̃a̶̛̛̠̽̄̀͋͂v̶̛͖̦̰̹̪̲̦̽͒̅̽̿͊̎̚͜ͅą̸̛͍̙͈̫̲͇͙̹͇̯̹̎͋̑̂͛͘ ̶̛̠̣͓̈́͂̐̈́̉̍̿̌̚͜͝
        Layout.putConstraint(SpringLayout.NORTH, CardButtons[0], majorGap, SpringLayout.SOUTH, XPLabel);
        Layout.putConstraint(SpringLayout.EAST, CardButtons[0], majorGap, SpringLayout.EAST, HPBar);
        //Layout.putConstraint(SpringLayout.SOUTH,CardButtons[0],majorGap,SpringLayout.SOUTH,ContentPanel);

        Layout.putConstraint(SpringLayout.WEST, CardButtons[1], majorGap, SpringLayout.EAST, CardButtons[0]);
        Layout.putConstraint(SpringLayout.SOUTH, CardButtons[1], majorGap / 2, SpringLayout.VERTICAL_CENTER, CardButtons[0]);
        Layout.putConstraint(SpringLayout.NORTH, CardButtons[1], majorGap, SpringLayout.NORTH, ContentPanel);

        Layout.putConstraint(SpringLayout.NORTH, CardButtons[2], majorGap, SpringLayout.SOUTH, CardButtons[1]);
        Layout.putConstraint(SpringLayout.WEST, CardButtons[2], 0, SpringLayout.WEST, CardButtons[1]);
        Layout.putConstraint(SpringLayout.EAST, CardButtons[2], 0, SpringLayout.EAST, CardButtons[1]);
        //Layout.putConstraint(SpringLayout.SOUTH,CardButtons[2],0,SpringLayout.SOUTH,ContentPanel);

        Layout.putConstraint(SpringLayout.NORTH, CardButtons[3], CardButtons[3].getHeight() / 2 + majorGap, SpringLayout.SOUTH, XPLabel);
        Layout.putConstraint(SpringLayout.WEST, CardButtons[3], majorGap, SpringLayout.EAST, CardButtons[2]);
        //Layout.putConstraint(SpringLayout.SOUTH,CardButtons[0],majorGap,SpringLayout.SOUTH,ContentPanel);

        Layout.putConstraint(SpringLayout.WEST, CardButtons[4], majorGap, SpringLayout.EAST, CardButtons[3]);
        Layout.putConstraint(SpringLayout.SOUTH, CardButtons[4], majorGap / 2, SpringLayout.VERTICAL_CENTER, CardButtons[3]);
        Layout.putConstraint(SpringLayout.NORTH, CardButtons[4], majorGap, SpringLayout.NORTH, ContentPanel);

        Layout.putConstraint(SpringLayout.NORTH, CardButtons[5], majorGap, SpringLayout.SOUTH, CardButtons[4]);
        Layout.putConstraint(SpringLayout.WEST, CardButtons[5], 0, SpringLayout.WEST, CardButtons[4]);
        Layout.putConstraint(SpringLayout.EAST, CardButtons[5], 0, SpringLayout.EAST, CardButtons[4]);

        Layout.putConstraint(SpringLayout.NORTH, CardButtons[6], CardButtons[6].getHeight() / 2 + majorGap, SpringLayout.SOUTH, XPLabel);
        Layout.putConstraint(SpringLayout.WEST, CardButtons[6], majorGap, SpringLayout.EAST, CardButtons[4]);
        //Ends here...


        switch (GS.getCurrentStageInRoom()) {
            case 0:
                CardButtons[1].setEnabled(false);
                CardButtons[2].setEnabled(false);
                CardButtons[1].setIcon(new ImageIcon(ResourceManager.CardBack));
                CardButtons[2].setIcon(new ImageIcon(ResourceManager.CardBack));
            case 1:
                CardButtons[3].setEnabled(false);
                CardButtons[3].setIcon(new ImageIcon(ResourceManager.CardBack));
            case 2:
                CardButtons[4].setEnabled(false);
                CardButtons[5].setEnabled(false);
                CardButtons[4].setIcon(new ImageIcon(ResourceManager.CardBack));
                CardButtons[5].setIcon(new ImageIcon(ResourceManager.CardBack));
            case 3:

                CardButtons[6].setEnabled(false);
                //CardButtons[6].setVisible(false);
        }

        menu = new Menu(this, GS.getCurrentGameState());
        ContentPanel.add(menu);
        Layout.putConstraint(SpringLayout.WEST, menu, 0, SpringLayout.WEST, this);
        Layout.putConstraint(SpringLayout.NORTH, menu, 0, SpringLayout.NORTH, this);

        this.setLocation(startWidth, startHeight);
        this.setPreferredSize(new Dimension(Width, Height));
        this.pack();
        this.setVisible(true);
    }

    private void HookEvents() {
        CardButtons[0].addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.DrawCommand + " " + 1));
        CardButtons[1].addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.DrawCommand + " " + 1));
        CardButtons[2].addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.DrawCommand + " " + 2));
        CardButtons[3].addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.DrawCommand + " " + 1));
        CardButtons[4].addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.DrawCommand + " " + 1));
        CardButtons[5].addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.DrawCommand + " " + 2));
        CardButtons[6].addActionListener(actionEvent -> GS.RelayAction(InternalCommandsDictionary.DrawCommand + " " + 1));
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
