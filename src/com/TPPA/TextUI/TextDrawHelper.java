package com.TPPA.TextUI;

import com.TPPA.GameLogic.Main;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by andre on 4/11/17.
 */
enum AvailableOS{
    Windows,
    Linux,
    Mac,
    Other;
}

public class TextDrawHelper {//To console
    //this uses system calls :(
    private static int ConsoleLines;
    private static int ConsoleRows;
    private static String OSName;
    private static AvailableOS OS;
    public static Scanner InputScanner = new Scanner(Main.InputStream);

    private static final int FillClearNum = 64;
    public static void Init(){
        OSName = System.getProperty("os.name").toLowerCase();
        if(OSName.contains("win")){
            OS = AvailableOS.Windows;
        }else if(OSName.contains("mac")){
            OS = AvailableOS.Mac;
        }else if(OSName.contains("linux")){
            OS = AvailableOS.Linux;
        }else{
            OS = AvailableOS.Other;
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
    }
}
