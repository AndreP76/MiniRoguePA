package com.TPPA.GraphicalUI;

import com.TPPA.GameLogic.GameStateController;

/**
 * Created by Lídia on 29/05/2017.
 */
public class GraphicalSpellView extends GraphicalStateView {
    public GraphicalSpellView(GameStateController GS) {
        super(GS);
    }

    @Override
    public void Render() {

    }

    @Override
    public void DestroyView() {
        this.dispose();
    }
}
