package com.TPPA.TextUI;

import com.TPPA.GameLogic.*;

import java.awt.geom.Area;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by andre on 4/11/17.
 */
public class StartStateView extends StateView {
    private String DifficultyKey = "D";
    private String AreaKey = "A";
    private String StartKey = "S";
    StartStateView(){
        super();//regista este observador no GameStateController
    }

    @Override
    public void Render() {
        while (true) {
            TextDrawHelper.ClearScreen();
            Main.OutputStream.println("Inicio :\n\t" + DifficultyKey + " - Definir difficuldade\n\t" + AreaKey + " - Definir area inicial\n\n\nEscolha : ");
            String userCommand = TextDrawHelper.InputScanner.next();
            if (userCommand.equals(DifficultyKey)) {
                while (true) {
                    TextDrawHelper.ClearScreen();
                    DifficultyLevelEnum[] Diffs = DifficultyLevelEnum.values();
                    for (int i = 0; i < Diffs.length; ++i) {
                        System.out.println("" + (i + 1) + ") " + Diffs[i].name());
                    }
                    int Sel = TextDrawHelper.InputScanner.nextInt();
                    if (Sel >= 1 && Sel <= Diffs.length) {//valid
                        GameStateController.getCurrentController().setGameDificulty(Diffs[Sel-1]);
                        break;
                    }
                }
            }else if(userCommand.equals(AreaKey)){
                while (true) {
                    TextDrawHelper.ClearScreen();
                    GameStateController GSC = GameStateController.getCurrentController();
                    System.out.println("Area inicial (0 ~ " + GSC.getMaxZones() + ") : ");
                    int Sel = TextDrawHelper.InputScanner.nextInt();
                    if (Sel >= 1 && Sel <= GSC.getMaxZones()) {
                        GSC.setCurrentZone(Sel);
                        break;
                    }
                }
            }else if(userCommand.equals(StartKey)){
                GameStateController.getCurrentController().getCurrentGameState().ToDrawPhase();
            }
        }
    }

    @Override
    public void update(Observable observable, Object o) {//chamado quando muda o estado
        GameStateController.getCurrentController().deleteObserver(this);
        super.update(observable,o);
    }
}
