package com.TPPA.GameLogic;
//Sorry Lidia, eu programo tudo em ingles xD
//Se for problema diz que eu mudo xD

import com.TPPA.TextUI.StartStateView;
import com.TPPA.TextUI.StateView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Paths;

public class Main {
    public static PrintStream ErrorStream;
    public static PrintStream OutputStream;
    public static InputStream InputStream;

    public static String ErrorLogPath = Paths.get("./ErrorLog.txt").toString();

    public static void main(String[] args) throws Throwable{
        if(!ErrorLogPath.equals(""))
            ErrorStream = new PrintStream(new FileOutputStream(ErrorLogPath));
        else
            ErrorStream = System.err;

        GameStateController GSC = new GameStateController();
        StateView SV = StateView.GenerateView();
    }
}
