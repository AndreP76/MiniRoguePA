package com.TPPA.TextUI;

import com.TPPA.Main;
import org.fusesource.jansi.AnsiConsole;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * Created by andre on 4/11/17.
 */
enum AvailableOS{
    Windows,
    Linux,
    Mac,
    Other
}

public class TextDrawHelper {//To console
    private static final int FillClearNum = 64;
    public static Scanner InputScanner = new Scanner(Main.InputStream);
    //this uses system calls :(
    private static int ConsoleLines;
    private static int ConsoleRows;
    private static String OSName;
    private static AvailableOS OS;

    public static void Init(){
        OSName = System.getProperty("os.name").toLowerCase();
        if(OSName.contains("win")){
            OS = AvailableOS.Windows;
            Main.ErrorStream.println("User is using Windows OS");
        }else if(OSName.contains("mac")){
            OS = AvailableOS.Mac;
            Main.ErrorStream.println("User is using MacOS");
        }else if(OSName.contains("linux")){
            OS = AvailableOS.Linux;
            Main.ErrorStream.println("User is using a Linux distro");
        }else{
            OS = AvailableOS.Other;
            Main.ErrorStream.println("User is using an unknown OS. Maybe BSD or Solaris?");
        }
    }
    private static void fillClearScreen(){
        StringBuilder x = new StringBuilder();
        for (int i = 0; i < FillClearNum; ++i) {
            x.append("\n");
        }

        if(OS == AvailableOS.Windows) {
            x.append("\r");
        }

        Main.OutputStream.print(x.toString());
    }
    //Helper methods
    public static void ClearScreen(){
        AnsiConsole.systemInstall();
        ansi().eraseScreen();
        AnsiConsole.systemUninstall();
        /*
        if(OS == AvailableOS.Other || OS == AvailableOS.Linux) {//whut ? Let's assume Linux
            //if(OS == AvailableOS.Other) Warnings here
            try {
                Process x = Runtime.getRuntime().exec("clear");
                Main.ErrorStream.println("Clearing on " + OSName + " using system call");
            }catch (IOException IOEX){
                Main.ErrorStream.println("exec(clear) on " + OSName + " failed! \\n method used");
                fillClearScreen();
            }
        }else if(OS == AvailableOS.Mac){
            try {
                Runtime.getRuntime().exec("clear");//heh, same as linux
                Main.ErrorStream.println("Clearing on " + OSName + " using system call");
            }catch (IOException IOEX){
                Main.ErrorStream.println("exec(clear) on " + OSName + " failed! \\n method used");
                fillClearScreen();
            }
        }else if(OS == AvailableOS.Windows){
            //I'm sure there is a reasonable way, but....
            Main.ErrorStream.println("Clearing on " + OSName + " using \\n");
            fillClearScreen();
        }else{
            Main.ErrorStream.println("WHAT ? HOW ? WHEN ? WHERE ?\nThis shouldn't even be possible to happen");
        }
        */
    }
}
