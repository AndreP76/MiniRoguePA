package com.TPPA.TextUI;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.Internals.InternalCommandsDictionary;
import com.TPPA.Main;
import org.fusesource.jansi.Ansi.Color;
import org.fusesource.jansi.AnsiConsole;

import static org.fusesource.jansi.Ansi.ansi;


/**
 * Created by andre on 5/3/17.
 */
public class GameOverView extends StateView {
    GameOverView(GameStateController GS) {
        super(GS);
    }

    @Override
    public void Render() {
        AnsiConsole.systemInstall();
        GameStateController GSC = GS;
        String Text = "";
        while (!GSC.MessageStack.empty())
            Text += GSC.MessageStack.pop() + "\n";

        Text += ("               ...") + "\n";
        Text += ("             ;::::;") + "\n";
        Text += ("           ;::::; :;") + "\n";
        Text += ("         ;:::::'   :;") + "\n";
        Text += ("        ;:::::;     ;.") + "\n";
        Text += ("       ,:::::'       ;           OOO\\") + "\n";
        Text += ("       ::::::;       ;          OOOOO\\") + "\n";
        Text += ("       ;:::::;       ;         OOOOOOOO") + "\n";
        Text += ("      ,;::::::;     ;'         / OOOOOOO") + "\n";
        Text += ("    ;:::::::::`. ,,,;.        /  / DOOOOOO") + "\n";
        Text += ("  .';:::::::::::::::::;,     /  /     DOOOO") + "\n";
        Text += (" ,::::::;::::::;;;;::::;,   /  /        DOOO") + "\n";
        Text += (";`::::::`'::::::;;;::::: ,#/  /          DOOO") + "\n";
        Text += (":`:::::::`;::::::;;::: ;::#  /            DOOO") + "\n";
        Text += ("::`:::::::`;:::::::: ;::::# /              DOO") + "\n";
        Text += ("`:`:::::::`;:::::: ;::::::#/               DOO") + "\n";
        Text += (" :::`:::::::`;; ;:::::::::##                OO") + "\n";
        Text += (" ::::`:::::::`;::::::::;:::#                OO") + "\n";
        Text += (" `:::::`::::::::::::;'`:;::#                O") + "\n";
        Text += ("  `:::::`::::::::;' /  / `:#") + "\n";
        Text += ("   ::::::`:::::;'  /  /   `#") + "\n";

        Text += "\n\t\t\t\tYou died... \n\t\t\t\tScore : " + GSC.CalculateScore();


        Main.OutputStream.println(ansi().eraseScreen().fg(Color.RED).a(Text));
        AnsiConsole.systemUninstall();
        //Main.OutputStream.println(Text);

        TextDrawHelper.InputScanner.nextLine();
        GSC.RelayAction(InternalCommandsDictionary.QuitCommand);
    }
}
