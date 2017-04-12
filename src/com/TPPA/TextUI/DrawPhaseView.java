package com.TPPA.TextUI;

import com.TPPA.GameLogic.Main;

/**
 * Created by andre on 4/12/17.
 */
public class DrawPhaseView extends StateView {
    @Override
    public void Render() {
        Main.OutputStream.println("Haro!");
    }


}
