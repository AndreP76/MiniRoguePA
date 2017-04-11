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

    public abstract void Render();//Entry point for the views

    @Override
    public void update(Observable observable, Object o){
        if(GameStateController.getCurrentController().getCurrentGameState().getClass() == StartState.class){
            System.out.print("Start state!");
            CurrentView = new StartStateView();
            CurrentView.Render();
        }else if(GameStateController.getCurrentController().getCurrentGameState().getClass() == RestingState.class){
            System.out.print("Resting state!");
        }else{
            System.out.print("Unknown state!");
        }
    }

    public static StateView GenerateView(){
        if(GameStateController.getCurrentController().getCurrentGameState().getClass() == StartState.class) {
            return new StartStateView();
        }else return null;
    }
}
