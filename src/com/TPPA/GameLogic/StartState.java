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
        Act[0] = new Action(InternalCommandsDictionary.SetDifficultyCommand, "Change difficulty (1 ~ 4)");
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
            if (DiffInt > 0 && DiffInt < 4) {//valid difficulty
                DifficultyLevelEnum Diff = DifficultyLevelEnum.values()[DiffInt - 1];
                if (Diff != null)
                    GameStateController.getCurrentController().setGameDifficulty(Diff);
            } else {
                GameStateController.getCurrentController().MessageStack.push("Unknown difficulty");
            }
        }else if(SSplit[0].equals(InternalCommandsDictionary.SetAreaCommand)){
            Integer AreaInt = Integer.parseInt(SSplit[SSplit.length - 1]);
            GameStateController GSC = GameStateController.getCurrentController();
            if(AreaInt > 0 && AreaInt < GSC.getMaxZones()) {//valid area
                GSC.setCurrentZone(AreaInt);
            }
        }else if(SSplit[0].equals(InternalCommandsDictionary.StartCommand)){
            GameStateController GSC = GameStateController.getCurrentController();
            Player P = GSC.getCurrentPlayer();
            int armor = 0;
            int hp = 0;
            int gold = 0;
            int food = 0;
            int xp = 0;
            switch (GSC.getGameDifficulty()) {
                case Casual: {
                    armor = 1;
                    hp = 5;
                    gold = 5;
                    food = 6;
                    break;
                }
                case Normal: {
                    hp = 5;
                    gold = 3;
                    food = 6;
                    break;
                }
                case Hard: {
                    hp = 4;
                    gold = 2;
                    food = 5;
                    break;
                }
                case Nightmare: {
                    hp = 3;
                    gold = 1;
                    food = 3;
                    break;
                }
                case Debug: {
                    armor = 7;
                    hp = 30;
                    gold = 30;
                    food = 10;
                    xp = 36;
                    break;
                }
            }
            P.setArmor(armor);
            P.setFood(food);
            P.setGold(gold);
            P.setHP(hp);
            P.setXP(xp);
            return new AwaitCardSelectionState();
        } else if (SSplit[0].equals(InternalCommandsDictionary.LoadCommand)) {
            String Pth = Paths.get(SSplit[SSplit.length - 1]).toString();
            File x = new File(Pth);
            if (x.exists()) {
                try {
                    Main.ErrorStream.println("Loading savegame in location : " + SSplit[SSplit.length - 1]);
                    GameStateController.setCurrentController((GameStateController) (new ObjectInputStream(new FileInputStream(x)).readObject()));
                } catch (IOException e) {
                    Main.ErrorStream.println("Error deserealizing object : IOException\n\t" + e.fillInStackTrace());
                } catch (ClassNotFoundException e) {
                    Main.ErrorStream.println("Error deserealizing object : ClassNotFound\n\t" + e.fillInStackTrace());
                }
            } else {
                return this;
            }
        }
        return this;
    }
}
