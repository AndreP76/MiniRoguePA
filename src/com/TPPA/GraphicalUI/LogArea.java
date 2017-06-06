package com.TPPA.GraphicalUI;

import com.TPPA.GameLogic.GameStateController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by andre on 5/31/17.
 */
public class LogArea extends JPanel {
    GameStateController GS;
    JTextArea myTextArea;
    Font f;
    LogArea(GameStateController GS) {
        this.GS = GS;
        myTextArea = new JTextArea();
        String Text = "Dungeon Log:\n";
        while (!GS.MessageStack.empty()) {
            Text += "  " + GS.MessageStack.pop() + "\n";
        }
        myTextArea.setText(Text);

        this.setLayout(new BorderLayout());
        this.add(myTextArea, BorderLayout.PAGE_START);
        this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        //this.setOpaque(false);
        this.setBackground(new Color(146, 201, 147));
        this.setPreferredSize(new Dimension(300, 100));
        myTextArea.setForeground(Color.BLACK);
        f = myTextArea.getFont();
        myTextArea.setFont(f.deriveFont(Font.BOLD));
        f = myTextArea.getFont();
        myTextArea.setEditable(false);
        myTextArea.setOpaque(false);
    }

}
