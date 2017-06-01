package com.TPPA.GraphicalUI;

import com.TPPA.GameLogic.GameStateController;

import javax.swing.*;

/**
 * Created by andre on 5/31/17.
 */
public class LogArea extends JTextArea {
    GameStateController GS;

    LogArea(GameStateController GS) {
        this.GS = GS;
        String Text = "";
        while (!GS.MessageStack.empty()) {
            Text += GS.MessageStack.pop();
        }
        this.setText(Text);
    }
}
