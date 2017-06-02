package com.TPPA.GameLogic;

import com.TPPA.GameLogic.Cards.CardBase;
import com.TPPA.GameLogic.Internals.Deck;
import com.TPPA.GameLogic.Internals.DifficultyLevelEnum;
import com.TPPA.GameLogic.Internals.Monster;
import com.TPPA.GameLogic.Internals.Player;
import com.TPPA.Main;

import java.util.Observable;
import java.util.Stack;

/**
 * Created by andre on 4/10/17.
 */
public class GameStateController extends Observable implements java.io.Serializable{
    //Acho que esta a ser misturada aqui um controlador de MVC e um "controlador" da logica...
    //The IView speaks only to this class
    //private static GameStateController CurrentController; //Keeps the current controller. THERE CAN BE ONLY ONE!
    //Idiots...
    public Stack<String> MessageStack = null;//the view reads messages from this
    private IState CurrentGameState; //The "Model"
    private DifficultyLevelEnum GameDifficulty;
    private int MaxZones;
    private int CurrentZone;
    private Player CurrentPlayer;
    private Deck CurrentDeck;
    private int MaxStagesInRoom = 5;
    private int CurrentStageInRoom = 0;
    private int RoomsInZone;
    private int CurrentRoom = 0;
    private CardBase[][] RoomStages = null;
    private int MaxCardsInStage = 2;
    private int CardsInEvenStage = 1;
    private Boolean BattledInThisRoom = false;
    private Monster CurrentMonster = null;

    public GameStateController() {//default
        //CurrentGameState = new StartState();
        GameDifficulty = DifficultyLevelEnum.Normal;//User can change it on StartState
        MaxZones = 5;
        CurrentZone = 1;
        CurrentPlayer = new Player(0, 0, 0, 0, 0, 0);
        //CurrentController = this;
        CurrentDeck = new Deck(this, 6);
        MessageStack = new Stack<>();
    }

    /*public static GameStateController getCurrentController() {
        return CurrentController;
    }*/

    //getter

    /*public static void setCurrentController(GameStateController currentController) {
        CurrentController = currentController;
        CurrentController.setCurrentGameState(CurrentController.getCurrentGameState());
    }*/

    public Monster getCurrentMonster() {
        return CurrentMonster;
    }

    //setters
    public void setCurrentMonster(Monster currentMonster) {
        CurrentMonster = currentMonster;
        setBattledInThisRoom(true);
    }

    public Boolean getBattledInThisRoom() {
        return BattledInThisRoom;
    }

    public void setBattledInThisRoom(Boolean battledInThisRoom) {
        BattledInThisRoom = battledInThisRoom;
    }

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

    public void setRoomStages(CardBase[][] roomStages) {
        RoomStages = roomStages;
    }

    public int getCurrentStageInRoom() {
        return CurrentStageInRoom;
    }

    public void setCurrentStageInRoom(int currentStageInRoom) {
        CurrentStageInRoom = currentStageInRoom;
        /*if (CurrentStageInRoom == this.getMaxStagesInRoom()) {
            CurrentStageInRoom = 0;
            this.setCurrentRoom(this.getCurrentRoom() + 1);
        }*/
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

    public void setCurrentZone(int currentZone) {
        CurrentZone = currentZone;
        if (CurrentZone == 1 || CurrentZone == 2) {
            RoomsInZone = 2;
        } else if (CurrentZone == 3 || CurrentZone == 4) {
            RoomsInZone = 3;
        } else if (CurrentZone == 5) {
            RoomsInZone = 4;
        } else {
            RoomsInZone = 0;
        }
    }

    public int getTrueRoom() {
        int Level = getCurrentZone();

        if (Level == 1) {
            return getCurrentRoom();
        } else if (Level == 2) {
            return 2 + getCurrentRoom();
        } else if (Level == 3) {
            return 4 + getCurrentRoom();
        } else if (Level == 4) {
            return 7 + getCurrentRoom();
        } else if (Level == 5) {
            return 10 + getCurrentRoom();
        } else return -1;
    }

    public IState getCurrentGameState() {
        return CurrentGameState;
    }

    public void setCurrentGameState(IState currentGameState) {
        CurrentGameState = currentGameState;
    }

    public void notificarObservadores() {
        this.setChanged();
        this.notifyObservers();
    }

    public int getCurrentRoom() {
        return CurrentRoom;
    }

    public void setCurrentRoom(int currentRoom) {
        Main.ErrorStream.println("Room was set to " + currentRoom);
        CurrentRoom = currentRoom;
    }

    public int getRoomsInZone() {
        return RoomsInZone;
    }

    public DifficultyLevelEnum getGameDifficulty() {
        return GameDifficulty;
    }

    //C methods

    public void setGameDifficulty(DifficultyLevelEnum gameDifficulty) {
        GameDifficulty = gameDifficulty;
    }

    public void RelayAction(String ActionString) {
        setCurrentGameState(getCurrentGameState().Action(ActionString));
    }

    public int CalculateScore() {
        int CurrentScore = 0;
        switch (this.getGameDifficulty()) {
            case Casual: {
                CurrentScore += 0;
                break;
            }
            case Normal: {
                CurrentScore += 2;
                break;
            }
            case Hard: {
                CurrentScore += 4;
                break;
            }
            case Nightmare: {
                CurrentScore += 6;
                break;
            }
        }

        Player P = getCurrentPlayer();
        CurrentScore += (getCurrentZone() * 3);                  //Area Reached *3
        CurrentScore += ((getTrueRoom() - 1) * 2);               //Bosses defeated *2
        CurrentScore += (P.getRank() * 2);                      //Rank *2
        CurrentScore += (P.getHP() * 2);                        //HP *2
        CurrentScore += (P.getGold() * 2);                       //Gold *2
        CurrentScore += P.getSpellsInventory().size();          //Spells *1
        CurrentScore += P.getArmor();                           //Armor *1
        CurrentScore += P.getFood();                            //Food *1

        return CurrentScore;
    }

    public String getZoneString(int currentZone) {
        switch (currentZone) {
            case 1:
                return "Black Sewers";
            case 2:
                return "Poisonous Dungeon";
            case 3:
                return "Undead Catacombs";
            case 4:
                return "Flaming Underworld";
            case 5:
                return "Sunken keep of Og";
            default:
                return "Not defined";
        }
    }
}
