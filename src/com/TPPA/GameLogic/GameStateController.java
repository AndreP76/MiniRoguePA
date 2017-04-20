package com.TPPA.GameLogic;

import com.TPPA.GameLogic.Cards.CardBase;

import java.util.Observable;

/**
 * Created by andre on 4/10/17.
 */
public class GameStateController extends Observable implements java.io.Serializable{
    //Acho que esta a ser misturada aqui um controlador de MVC e um "controlador" da logica...
    //The IView speaks only to this class
    private static GameStateController CurrentController; //Keeps the current controller. THERE CAN BE ONLY ONE!

    private IState CurrentGameState; //The "Model"

    private DifficultyLevelEnum GameDifficulty;
    private int MaxZones;
    private int CurrentZone;
    private Player CurrentPlayer;
    private Deck CurrentDeck;
    private int MaxStagesInRoom = 5;
    private int CurrentStageInRoom = 0;
    private int RoomsPerZoneCoeficient = 1;
    private int RoomsInZone;
    private int CurrentRoom = 0;
    private CardBase[][] RoomStages = null;
    private int MaxCardsInStage = 2;
    private int CardsInEvenStage = 1;
    private Boolean BattledInThisRoom = false;
    GameStateController(){//default
        CurrentGameState = new StartState();
        GameDifficulty = DifficultyLevelEnum.Normal;//User can change it on StartState
        MaxZones = 5;
        CurrentZone = 1;
        CurrentPlayer = new Player(0, 0, 0, 0, 0, 0);
        CurrentController = this;
    }

    public static GameStateController getCurrentController() {
        return CurrentController;
    }

    //getter
    public int getCardsInEvenStage() {
        return CardsInEvenStage;
    }
    public int getMaxCardsInStage() {
        return MaxCardsInStage;
    }
    public Player getCurrentPlayer() {
        return CurrentPlayer;
    }
    public CardBase[][] getRoomStages() {
        return RoomStages;
    }
    public int getCurrentStageInRoom() {
        return CurrentStageInRoom;
    }
    public Deck getCurrentDeck() {
        return CurrentDeck;
    }
    public int getMaxStagesInRoom() {
        return MaxStagesInRoom;
    }
    public int getMaxZones() {
        return MaxZones;
    }
    public int getCurrentZone() {
        return CurrentZone;
    }

    //setters
    public void setCurrentZone(int currentZone) {
        CurrentZone = currentZone;
    }

    public Boolean getBattledInThisRoom() {
        return BattledInThisRoom;
    }

    public IState getCurrentGameState() {
        return CurrentGameState;
    }
    public void setCurrentGameState(IState currentGameState) {
        CurrentGameState = currentGameState;
        this.setChanged();
        this.notifyObservers();
    }
    public int getCurrentRoom() {
        return CurrentRoom;
    }
    public int getRoomsInZone() {
        return RoomsInZone;
    }
    public int getRoomsPerZoneCoeficient() {
        return RoomsPerZoneCoeficient;
    }
    public DifficultyLevelEnum getGameDifficulty() {
        return GameDifficulty;
    }
    public void setGameDifficulty(DifficultyLevelEnum gameDifficulty) {
        GameDifficulty = gameDifficulty;
    }

    //C methods
    public void RelayAction(String ActionString) {
        setCurrentGameState(getCurrentGameState().Action(ActionString));
    }
}
