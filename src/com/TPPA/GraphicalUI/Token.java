package com.TPPA.GraphicalUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Lídia on 31/05/2017.
 */

public class Token extends JPanel {
    JLabel mark;
    String text;
    Dimension dim;

    public Token(int size) {
        this.dim = new Dimension(size, size);
        this.setOpaque(false);
        this.text = "";
        mark = new JLabel();
        mark.setForeground(Color.yellow);
        this.setLayout(new BorderLayout());
        this.add(mark, BorderLayout.CENTER);
        mark.setHorizontalAlignment(JLabel.CENTER);
        this.setPreferredSize(dim);
    }

    public void setTokenText(String Text) {
        this.text = Text;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        mark.setText(text);
        g.setColor(Color.black);
        g.fillOval(0, 0, dim.width, dim.height);
    }
}