package com.TPPA.GameLogic;

/**
 * Created by andre on 4/5/17.
 */
public class StartState extends GameState {
    StartState(){

    }

    @Override
    public Action[] GetActions() {
        Action[] Act = new Action[3];
        Act[0] = new Action(InternalCommandsDictionary.SetDifficultyCommand, "Alterar dificuldade (1 ~ 7)");
        Act[1] = new Action(InternalCommandsDictionary.SetAreaCommand, "Definir area de inicio (1 ~ " + GameStateController.getCurrentController().getMaxZones() + ")");
        Act[2] = new Action(InternalCommandsDictionary.StartCommand,"Começa o jogo");
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
        }
        return this;
    }
}
