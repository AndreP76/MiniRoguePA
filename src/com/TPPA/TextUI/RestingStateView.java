package com.TPPA.TextUI;

import com.TPPA.GameLogic.Action;
import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Main;

/**
 * Created by andre on 5/1/17.
 */
public class RestingStateView extends StateView {
    @Override
    public void Render() {
        String Text = "";
        GameStateController GSC = GameStateController.getCurrentController();
        while (!GSC.MessageStack.empty())
            Text += GSC.MessageStack.pop() + "\n";

        Text += "\n==== Finally, some time to rest... ===\n\n";

        Text += "You have 3 choices : ";
        Action[] ActionsAvailable = GSC.getCurrentGameState().GetActions();
        Text += "\n\t1) Reinforce your weapon (+2 XP)\n\t2) Search for Ration (+1 Food)\n\t3) Rest and heal (+2 HP)\n\n\nChoose : ";

        Main.OutputStream.print(Text);
        int Selected = -1;
        while (Selected <= 0 || Selected > 3) {
            while (!TextDrawHelper.InputScanner.hasNextInt()) ;
            Selected = TextDrawHelper.InputScanner.nextInt();
        }

        GSC.RelayAction(ActionsAvailable[Selected - 1].getActionString());
    }
}
