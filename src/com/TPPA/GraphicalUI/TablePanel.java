package com.TPPA.GraphicalUI;

import com.TPPA.GraphicalUI.Resources.ResourceManager;

import javax.swing.*;
import java.awt.*;

/**
 * Created by andre on 6/11/17.
 */
public class TablePanel extends JPanel {
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Dimension S = this.getSize();
        graphics.drawImage(ResourceManager.TableImage.getScaledInstance(S.width, S.height, Image.SCALE_SMOOTH), 0, 0, null);
    }
}
