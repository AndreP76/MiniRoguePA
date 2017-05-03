package com.TPPA.TextUI;

import com.TPPA.GameLogic.*;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by andre on 4/11/17.
 */
public abstract class StateView implements IView, Observer {
    public static HashMap<Class, Class> ModelToViewMap = GenMTVMap();
    public static StateView CurrentView;
    StateView(){
        GameStateController.getCurrentController().addObserver(this);
    }

    private static HashMap<Class, Class> GenMTVMap() {
        HashMap<Class, Class> MTVM = new HashMap<>();
        MTVM.put(StartState.class, StartStateView.class);
        MTVM.put(AwaitCardSelectionState.class, DrawPhaseView.class);
        MTVM.put(RestingState.class, RestingStateView.class);
        MTVM.put(TradingState.class, TradingStateView.class);
        return MTVM;
    }

    @Nullable
    public static StateView GenerateView() {
        if (GameStateController.getCurrentController().getCurrentGameState().getClass() == StartState.class) {
            CurrentView = new StartStateView();
            return CurrentView;
        } else return null;
    }

    public abstract void Render();//Entry point for the views

    @Override
    public void update(Observable observable, Object o) {
        Class ViewClass = ModelToViewMap.get(GameStateController.getCurrentController().getCurrentGameState().getClass());
        if (ViewClass == CurrentView.getClass()) {
            Main.ErrorStream.println("Keeping current view!");
        } else {
            Main.ErrorStream.println("Creating new view!");
            GameStateController.getCurrentController().deleteObserver(CurrentView);
            try {
                Main.ErrorStream.println("\t" + ViewClass.getCanonicalName());
                CurrentView = (StateView) ViewClass.newInstance();
            } catch (InstantiationException InEx) {
                Main.ErrorStream.println("Instantiation exception in view update!");
                Main.ErrorStream.println(InEx.toString());
                Main.ErrorStream.println(InEx.fillInStackTrace().toString());
            } catch (IllegalAccessException IlAcEx) {
                Main.ErrorStream.println("Illegal access exception in view update!");
                Main.ErrorStream.println(IlAcEx.toString());
                Main.ErrorStream.println(IlAcEx.fillInStackTrace().toString());
            }
        }
        CurrentView.Render();
    }

    /*public void update(Observable observable, Object o) {
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
    }*/
}
