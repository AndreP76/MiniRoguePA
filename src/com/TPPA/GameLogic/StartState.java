package com.TPPA.GameLogic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Paths;

/**
 * Created by andre on 4/5/17.
 */
public class StartState extends GameState {
    StartState(){

    }

    @Override
    public Action[] GetActions() {
        Action[] Act = new Action[4];
        Act[0] = new Action(InternalCommandsDictionary.SetDifficultyCommand, "Change difficulty (1 ~ 7)");
        Act[1] = new Action(InternalCommandsDictionary.SetAreaCommand, "Define starting area (1 ~ " + GameStateController.getCurrentController().getMaxZones() + ")");
        Act[2] = new Action(InternalCommandsDictionary.StartCommand, "Start the game!");
        Act[3] = new Action(InternalCommandsDictionary.LoadCommand, "Load a previously saved game");
        return Act;
    }

    @Override
    public Boolean CanDrawCard() {
        return true;
    }

    @Override
    public IState ToStart() {
        return this;
    }

    @Override
    public IState ToDrawPhase() {
        return new AwaitCardSelectionState();
    }

    @Override
    public IState Action(String ActionString) {
        String[] SSplit = ActionString.split(" ");
        if(SSplit[0].equals(InternalCommandsDictionary.SetDifficultyCommand)){
            Integer DiffInt = Integer.parseInt(SSplit[SSplit.length - 1]);
            DifficultyLevelEnum Diff = DifficultyLevelEnum.values()[DiffInt];
            if(Diff != null)
                GameStateController.getCurrentController().setGameDifficulty(Diff);
            //else idk, log it or something
        }else if(SSplit[0].equals(InternalCommandsDictionary.SetAreaCommand)){
            Integer AreaInt = Integer.parseInt(SSplit[SSplit.length - 1]);
            GameStateController GSC = GameStateController.getCurrentController();
            if(AreaInt > 0 && AreaInt < GSC.getMaxZones()) {//valid area
                GSC.setCurrentZone(AreaInt);
            }
            //else idk, log it or something
        }else if(SSplit[0].equals(InternalCommandsDictionary.StartCommand)){
            return new AwaitCardSelectionState();
        } else if (SSplit[0].equals(InternalCommandsDictionary.LoadCommand)) {
            String Pth = Paths.get(SSplit[SSplit.length]).toString();
            File x = new File(Pth);
            if (x.exists()) {
                try {
                    GameStateController.setCurrentController((GameStateController) (new ObjectInputStream(new FileInputStream(x)).readObject()));
                } catch (IOException e) {
                    Main.ErrorStream.println("Error deserealizing object\n\t" + e.fillInStackTrace());
                } catch (ClassNotFoundException e) {
                    Main.ErrorStream.println("Error deserealizing object\n\t" + e.fillInStackTrace());
                }
            } else {
                return this;
            }
        }
        return this;
    }
}
