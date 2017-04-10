package com.TPPA.GameLogic;

/**
 * Created by andre on 4/10/17.
 */
public class Action {
    private String ActionString;
    private String DescriptionString;
    Action(String AS, String DS){
        ActionString  = AS;
        DescriptionString = DS;
    }

    public String getActionString() {
        return ActionString;
    }

    public String getDescriptionString() {
        return DescriptionString;
    }
}
