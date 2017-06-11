package com.TPPA.GraphicalUI;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GraphicalUI.Resources.ResourceManager;

import javax.swing.*;
import java.awt.*;

/**
 * Created by andre on 5/20/17.
 */
public class GraphicalGameOverView extends GraphicalStateView {
    public GraphicalGameOverView(GameStateController GS) {
        super(GS);
    }

    @Override
    public void Render() {
        this.setMinimumSize(new Dimension((int) (ScreenSize.getWidth() * 0.75), (int) (ScreenSize.getHeight() * 0.75)));
        this.setPreferredSize(new Dimension((int) (ScreenSize.getWidth() * 0.75), (int) (ScreenSize.getHeight() * 0.75)));
        this.setMaximumSize(new Dimension((int) (ScreenSize.getWidth() * 0.75), (int) (ScreenSize.getHeight() * 0.75)));
        this.setContentPane(new GameOverPanel(this.getSize()));
        JLabel ScoreLabel = new JLabel("Score : " + GS.CalculateScore());
        Font F = null;
        F = ResourceManager.YouDiedFont;
        if (F == null)
            F = ScoreLabel.getFont();
        F = F.deriveFont(48.0f);
        ScoreLabel.setFont(F);
        ScoreLabel.setForeground(Color.RED);
        this.getContentPane().add(ScoreLabel);
        //JButton JB = new JButton("Back to main menu");
        /*JB.addActionListener(actionEvent -> {
            GS.RelayAction(InternalCommandsDictionary.QuitCommand);
        });*/
        //this.getContentPane().add(JB);
        SpringLayout SL = (SpringLayout) this.getContentPane().getLayout();
        SL.putConstraint(SpringLayout.NORTH, ScoreLabel, (int) (this.getHeight() * 0.7), SpringLayout.NORTH, this.getContentPane());
        SL.putConstraint(SpringLayout.WEST, ScoreLabel, (int) (this.getWidth() * 0.65), SpringLayout.WEST, this.getContentPane());

        /*
        SL.putConstraint(SpringLayout.NORTH, JB, 0, SpringLayout.NORTH, ScoreLabel);
        SL.putConstraint(SpringLayout.EAST, JB, -55, SpringLayout.WEST, ScoreLabel);
        SL.putConstraint(SpringLayout.SOUTH, JB, 0, SpringLayout.SOUTH, ScoreLabel);*/
        //SL.putConstraint(SpringLayout.EAST,this.getContentPane(),(int)(ScreenSize.getWidth() * 0.75),SpringLayout.WEST,this.getContentPane());
        //SL.putConstraint(SpringLayout.SOUTH,this.getContentPane(),(int)(ScreenSize.getHeight() * 0.75),SpringLayout.NORTH,this.getContentPane());
        this.setVisible(true);
    }

    @Override
    public void DestroyView() {
        this.dispose();
    }

    private class GameOverPanel extends JPanel {
        Dimension PanelSize;
        GameOverPanel(Dimension Size) {
            super();
            this.setLayout(new SpringLayout());
            PanelSize = Size;
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Image I = ResourceManager.GameOver.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
            Image J = ResourceManager.YouDied.getScaledInstance((int) (this.getWidth() * 0.65), (int) (this.getHeight() * 0.5), Image.SCALE_SMOOTH);
            graphics.drawImage(I, 0, 0, null);
            graphics.drawImage(J, (int) (getWidth() * 0.35), 0, null);
        }
    }
}
