package com.TPPA.GraphicalUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Lídia on 31/05/2017.
 */
//TODO: Melhorar tokens
public class Token extends JPanel {
    JLabel mark;
    String text;

    public Token() {
        this.setOpaque(false);
        this.text = "";
        mark = new JLabel();
        mark.setForeground(Color.yellow);
        this.setLayout(new BorderLayout());
        this.add(mark, BorderLayout.NORTH);
        this.setPreferredSize(new Dimension(25, 25));
    }

    public void setTokenText(String Text) {
        this.text = Text;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        mark.setText(text);
        g.setColor(Color.black);
        g.fillOval(0, 0, 25, 25);
    }
}