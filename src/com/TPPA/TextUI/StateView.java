package com.TPPA.TextUI;

import com.TPPA.GameLogic.*;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * Created by andre on 4/11/17.
 */
public abstract class StateView implements IView, Observer, Serializable {
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
        MTVM.put(SpellPhase.class, SpellView.class);
        MTVM.put(RollPhase.class, RollView.class);
        MTVM.put(RollPhase.class, RollView.class);
        MTVM.put(GameOverState.class, GameOverView.class);
        MTVM.put(FeatPhase.class, FeatView.class);
        return MTVM;
    }

    @Nullable
    public static StateView GenerateView() {
        if (GameStateController.getCurrentController().getCurrentGameState().getClass() == StartState.class) {
            CurrentView = new StartStateView();
            return CurrentView;
        } else return null;
    }

    public void Render() {//Entry point for the views
        String Text = "";
        AnsiConsole.systemInstall();
        TextDrawHelper.ClearScreen();
        GameStateController GSC = GameStateController.getCurrentController();
        Text += ansi().bgBrightDefault().fg(Ansi.Color.BLUE) + "HP : " + ansi().fg(Ansi.Color.YELLOW) + GSC.getCurrentPlayer().getHP() + ansi().fg(Ansi.Color.DEFAULT);
        Text += ansi().bgBrightDefault().fg(Ansi.Color.BLUE) + "\tXP : " + ansi().fg(Ansi.Color.YELLOW) + GSC.getCurrentPlayer().getXP() + ansi().fg(Ansi.Color.DEFAULT);
        Text += ansi().bgBrightDefault().fg(Ansi.Color.BLUE) + "\nGold : " + ansi().fg(Ansi.Color.YELLOW) + GSC.getCurrentPlayer().getGold() + ansi().fg(Ansi.Color.DEFAULT);
        ;
        Text += ansi().bgBrightDefault().fg(Ansi.Color.BLUE) + "\tArmor : " + ansi().fg(Ansi.Color.YELLOW) + GSC.getCurrentPlayer().getArmor() + ansi().fg(Ansi.Color.DEFAULT);
        ;
        Text += ansi().bgBrightDefault().fg(Ansi.Color.BLUE) + "\tFood : " + ansi().fg(Ansi.Color.YELLOW) + GSC.getCurrentPlayer().getFood() + ansi().fg(Ansi.Color.DEFAULT) + "\n";

        while (!GSC.MessageStack.empty())
            Text += ansi().fg(Ansi.Color.GREEN) + GSC.MessageStack.pop() + ansi().fg(Ansi.Color.DEFAULT) + "\n";

        Main.OutputStream.println(Text);
        AnsiConsole.systemUninstall();
    }

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
}
