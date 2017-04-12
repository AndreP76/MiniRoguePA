package com.TPPA.GameLogic;

import java.util.Observable;

/**
 * Created by andre on 4/10/17.
 */
public class GameStateController extends Observable implements java.io.Serializable{
    //Acho que esta a ser misturada aqui um controlador de MVC e um "controlador" da logica...
    //A classe Observable nao tenho a certeza se e precisa, mas nao faz mal estar ali
    //The IView speaks only to this class
    private static GameStateController CurrentController; //Keeps the current controller. THERE CAN BE ONLY ONE!

    private IState CurrentGameState; //The "Model"

    private DifficultyLevelEnum GameDificulty;
    private int MaxZones;
    private int CurrentZone;
    private Player CurrentPlayer;
    GameStateController(){//default
        CurrentGameState = new StartState();
        GameDificulty = DifficultyLevelEnum.Normal;//User can change it on StartState
        MaxZones = 5;
        CurrentZone = 1;
        CurrentPlayer = new Player();
        CurrentController = this;
    }

    //getter
    public static GameStateController getCurrentController() {
        return CurrentController;
    }
    public int getMaxZones() {
        return MaxZones;
    }
    public int getCurrentZone() {
        return CurrentZone;
    }

    public void setCurrentZone(int currentZone) {
        CurrentZone = currentZone;
    }

    public IState getCurrentGameState() {
        return CurrentGameState;
    }

    public void setCurrentGameState(IState currentGameState) {
        CurrentGameState = currentGameState;
        this.setChanged();
        this.notifyObservers();
    }

    public DifficultyLevelEnum getGameDificulty() {
        return GameDificulty;
    }

    //setters
    public void setGameDificulty(DifficultyLevelEnum gameDificulty) {
        GameDificulty = gameDificulty;
    }

    public Player getCurrentPlayer() {
        return CurrentPlayer;
    }

    //C methods
    public void RelayAction(String ActionString) {
        setCurrentGameState(getCurrentGameState().Action(ActionString));
    }
}
