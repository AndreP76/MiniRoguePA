package com.TPPA.TextUI;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.IView;
import com.TPPA.GameLogic.Main;
import com.TPPA.GameLogic.States.*;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * Created by andre on 4/11/17.
 */
public abstract class StateView implements IView, Observer, Serializable {
    public static HashMap<Class, Class> ModelToViewMap;

    static {
        ModelToViewMap = new HashMap<>();
        ModelToViewMap.put(StartState.class, StartStateView.class);
        ModelToViewMap.put(AwaitCardSelectionState.class, DrawPhaseView.class);
        ModelToViewMap.put(RestingState.class, RestingStateView.class);
        ModelToViewMap.put(TradingState.class, TradingStateView.class);
        ModelToViewMap.put(SpellPhase.class, SpellView.class);
        ModelToViewMap.put(RollPhase.class, RollView.class);
        ModelToViewMap.put(RollPhase.class, RollView.class);
        ModelToViewMap.put(GameOverState.class, GameOverView.class);
        ModelToViewMap.put(FeatPhase.class, FeatView.class);
    }

    public StateView CurrentView;
    public GameStateController GS;

    StateView(GameStateController GS) {
        this.GS = GS;
        GS.addObserver(this);
    }

    /*@Nullable
    public StateView GenerateView() {
        //Honestly I think this is useless...
        if (GS.getCurrentGameState().getClass() == StartState.class) {
            return new StartStateView();
        } else return null;
    }*/

    public void Render() {//Entry point for the views
        String Text = "";
        AnsiConsole.systemInstall();
        TextDrawHelper.ClearScreen();
        GameStateController GSC = GS;
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
        Class ViewClass = ModelToViewMap.get(GS.getCurrentGameState().getClass());
        if (ViewClass == this.getClass()) {
            Main.ErrorStream.println("Keeping current view!");
            this.Render();
        } else {
            Main.ErrorStream.println("Creating new view!");
            GS.deleteObserver(this);
            try {
                Main.ErrorStream.println("\t" + ViewClass.getCanonicalName());
                ((StateView) ViewClass.getDeclaredConstructor(GameStateController.class).newInstance(this.GS)).Render();
            } catch (InstantiationException InEx) {
                Main.ErrorStream.println("Instantiation exception in view update!");
                Main.ErrorStream.println(InEx.toString());
                Main.ErrorStream.println(InEx.fillInStackTrace().toString());
            } catch (IllegalAccessException IlAcEx) {
                Main.ErrorStream.println("Illegal access exception in view update!");
                Main.ErrorStream.println(IlAcEx.toString());
                Main.ErrorStream.println(IlAcEx.fillInStackTrace().toString());
            } catch (NoSuchMethodException e) {
                Main.ErrorStream.println("No such method exception in view update!");
                Main.ErrorStream.println(e.toString());
                Main.ErrorStream.println(e.fillInStackTrace().toString());
            } catch (InvocationTargetException e) {
                Main.ErrorStream.println("Invocation Target exception in view update!");
                Main.ErrorStream.println(e.toString());
                Main.ErrorStream.println(e.fillInStackTrace().toString());
            }
        }
        //this.CurrentView.Render();
    }
}
