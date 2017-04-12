package com.TPPA.TextUI;

import com.TPPA.GameLogic.*;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by andre on 4/11/17.
 */
public abstract class StateView implements IView, Observer {
    public static StateView CurrentView;
    StateView(){
        GameStateController.getCurrentController().addObserver(this);
    }

    public static StateView GenerateView() {
        if (GameStateController.getCurrentController().getCurrentGameState().getClass() == StartState.class) {
            CurrentView = new StartStateView();
            return CurrentView;
        } else return null;
    }

    public abstract void Render();//Entry point for the views

    @Override
    public void update(Observable observable, Object o) {
        if (GameStateController.getCurrentController().getCurrentGameState().getClass() == StartState.class) {
            Main.ErrorStream.println("Start state!");
            if (CurrentView.getClass() != StartStateView.class) {
                GameStateController.getCurrentController().deleteObserver(CurrentView);
                Main.ErrorStream.println("Creating new view!");
                CurrentView = new StartStateView();
            } else {
                Main.ErrorStream.println("Keeping current view!");
            }
        } else if (GameStateController.getCurrentController().getCurrentGameState().getClass() == RestingState.class) {
            Main.ErrorStream.println("Resting state!");
            Main.ErrorStream.println("Unimplemented view!");
        } else if (GameStateController.getCurrentController().getCurrentGameState().getClass() == AwaitCardSelectionState.class) {
            Main.ErrorStream.println("DrawState!");
            if (CurrentView.getClass() != DrawPhaseView.class) {
                GameStateController.getCurrentController().deleteObserver(CurrentView);
                Main.ErrorStream.println("Creating new view!");
                CurrentView = new DrawPhaseView();
            } else {
                Main.ErrorStream.println("Keeping current view!");
            }
        }else{
            Main.ErrorStream.println("Unknown state!");
        }
        CurrentView.Render();
    }
}
