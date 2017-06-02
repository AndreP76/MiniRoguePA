package com.TPPA.GraphicalUI;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.States.*;
import com.TPPA.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Dimension2D;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by andre on 5/18/17.
 */
public abstract class GraphicalStateView extends JFrame implements Observer {
    private static HashMap<Class, Class> GraphicalMap;

    static {
        GraphicalMap = new HashMap<>();
        GraphicalMap.put(StartState.class, GraphicalStartStateView.class);
        GraphicalMap.put(AwaitCardSelectionState.class, GraphicalDrawPhaseView.class);
        GraphicalMap.put(RestingState.class, GraphicalRestingStateView.class);
        GraphicalMap.put(TradingState.class, GraphicalTradeView.class);
        GraphicalMap.put(SpellPhase.class, GraphicalSpellView.class);
        GraphicalMap.put(RollPhase.class, GraphicalRollView.class);
        GraphicalMap.put(GameOverState.class, GraphicalGameOverView.class);
        GraphicalMap.put(FeatPhase.class, GraphicalFeatView.class);
    }

    protected GameStateController GS;
    protected Dimension2D ScreenSize;
    private Menu menu;

    GraphicalStateView(GameStateController GS) {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.GS = GS;
        GS.addObserver(this);
    }

    public abstract void Render();

    public abstract void DestroyView();

    public void update(Observable observable, Object o) {
        Class ViewClass = GraphicalMap.get(GS.getCurrentGameState().getClass());
        if (ViewClass == this.getClass()) {
            Main.ErrorStream.println("Keeping current frame as view!");
            this.DestroyView();
            this.Render();
        } else {
            Main.ErrorStream.println("Creating new JFrame view!");
            GS.deleteObserver(this);
            try {
                Main.ErrorStream.println("\t" + ViewClass.getCanonicalName());
                this.DestroyView();
                ((GraphicalStateView) ViewClass.getDeclaredConstructor(GameStateController.class).newInstance(this.GS)).Render();
            } catch (InstantiationException InEx) {
                Main.ErrorStream.println("Instantiation exception in graphical view update!");
                Main.ErrorStream.println(InEx.toString());
                Main.ErrorStream.println(InEx.fillInStackTrace().toString());
            } catch (IllegalAccessException IlAcEx) {
                Main.ErrorStream.println("Illegal access exception in graphical view update!");
                Main.ErrorStream.println(IlAcEx.toString());
                Main.ErrorStream.println(IlAcEx.fillInStackTrace().toString());
            } catch (NoSuchMethodException e) {
                Main.ErrorStream.println("No such method exception in graphical view update!");
                Main.ErrorStream.println(e.toString());
                Main.ErrorStream.println(e.fillInStackTrace().toString());
            } catch (InvocationTargetException e) {
                Main.ErrorStream.println("Invocation Target exception in graphical view update!");
                Main.ErrorStream.println(e.toString());
                Main.ErrorStream.println(e.fillInStackTrace().toString());
            }
        }
    }

}
