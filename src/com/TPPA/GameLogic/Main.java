package com.TPPA.GameLogic;
//Sorry Lidia, eu programo tudo em ingles xD
//Se for problema diz que eu mudo xD
//Zone : Nivel
//Room : Area
//Stage : Coluna

import com.TPPA.GameLogic.States.StartState;
import com.TPPA.TextUI.StartStateView;
import com.TPPA.TextUI.StateView;
import com.TPPA.TextUI.TextDrawHelper;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Date;

public class Main {
    public static PrintStream ErrorStream;
    public static PrintStream OutputStream;
    public static InputStream InputStream;

    public static String ErrorLogPath = Paths.get("./ErrorLog.txt").toString();

    public static void main(String[] args) throws Throwable{
        if (!ErrorLogPath.equals("")) {
            ErrorStream = new PrintStream(new FileOutputStream(ErrorLogPath));
            ErrorStream.println("Session started in " + Date.from(Instant.now()).toString());
        }
        else
            ErrorStream = System.err;

        OutputStream = System.out;
        InputStream = System.in;

        TextDrawHelper.Init();
        GameStateController GSC = new GameStateController();
        GSC.setCurrentGameState(new StartState(GSC));
        StateView SV = new StartStateView(GSC);
        //StateView SV = StateView.GenerateView();
        if (SV != null) {
            Main.ErrorStream.println("Starting render cycle");
            SV.Render();
        } else {
            Main.ErrorStream.println("Could not start rendering, Generated view was null");
        }
    }
}
