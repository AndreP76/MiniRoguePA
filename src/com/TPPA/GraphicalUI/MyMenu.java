package com.TPPA.GraphicalUI;

import com.TPPA.GameLogic.FileUtilities;
import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.IState;
import com.TPPA.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static javax.swing.JFileChooser.APPROVE_OPTION;

/**
 * Created by Lídia on 02/06/2017.
 */
public class MyMenu extends JMenuBar {
    private JMenu menu;
    private JMenuItem loadGame;
    private JMenuItem saveGame;
    private JMenuItem exitGame;
    private GraphicalStateView parentView;
    private IState GS;

    public MyMenu(GraphicalStateView parent, IState GS) {
        this.parentView = parent;
        this.GS = GS;

        menu = new JMenu("Menu");
        loadGame = new JMenuItem("Load Game");
        saveGame = new JMenuItem("Save Game");
        exitGame = new JMenuItem("Exit");


        menu.add(loadGame);
        menu.add(saveGame);
        menu.addSeparator();
        menu.add(exitGame);
        this.add(menu);

        loadGame.addActionListener(new loadListener());
        saveGame.addActionListener(new saveListener());
        exitGame.addActionListener(new exitListener());
    }

    public void handleLoadGame() {
        JFileChooser fc = new JFileChooser("./saves");
        int result = fc.showOpenDialog(parentView);
        if (result == APPROVE_OPTION) {
            try {
                Main.ErrorStream.println("Retrieving saved game");
                File fl = fc.getSelectedFile();
                GS.setCurrentController((GameStateController) FileUtilities.retrieveGameFromFile(fl));
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleSaveGame() {
        JFileChooser fc = new JFileChooser("./saves");

        int result = fc.showSaveDialog(parentView);
        if (result == APPROVE_OPTION) {
            try {
                File fw = fc.getSelectedFile();
                FileUtilities.saveGameToFile(GS.getCurrentController(), fw);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class loadListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleLoadGame();
        }
    }

    class saveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleSaveGame();
        }
    }

    class exitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
