package com.TPPA.GraphicalUI;

import com.TPPA.GameLogic.Internals.InternalCommandsDictionary;
import com.TPPA.GameLogic.Internals.Player;
import com.TPPA.GameLogic.Spells.FireSpell;
import com.TPPA.GameLogic.Spells.HealSpell;
import com.TPPA.GameLogic.Spells.IceSpell;
import com.TPPA.GameLogic.Spells.PoisonSpell;

import javax.swing.*;

/**
 * Created by andre on 6/4/17.
 */
public class SpellSelector extends JFrame {
    private JList<String> SpellsList;
    private int SpellIndex;
    private JPanel JP;
    private String[] SpellNames;
    private Player P;

    public SpellSelector(JPanel Pan, Player P) {
        this.P = P;
        this.JP = Pan;
        SpellNames = new String[P.getSpellsInventory().size()];
        for (int i = 0; i < P.getSpellsInventory().size(); ++i) {
            SpellNames[i] = P.getSpellsInventory().get(i).getSpellID();
        }
    }

    public static Integer GetSpellIndex(JPanel JP, Player P) {
        SpellSelector S = new SpellSelector(JP, P);
        return S.getSpellIndex();
    }

    public static String GetBuySpell(JPanel J) {
        Player FauxPlayer = new Player(0, 0, 0, 0, 0, 0);
        FauxPlayer.getSpellsInventory().add(new FireSpell(null, InternalCommandsDictionary.FireSpellID));
        FauxPlayer.getSpellsInventory().add(new IceSpell(null, InternalCommandsDictionary.IceSpellID));
        FauxPlayer.getSpellsInventory().add(new PoisonSpell(null, InternalCommandsDictionary.PoisonSpellID));
        FauxPlayer.getSpellsInventory().add(new HealSpell(null, InternalCommandsDictionary.HealSpellID));
        int S = GetSpellIndex(J, FauxPlayer);
        return FauxPlayer.getSpellsInventory().get(S).getSpellID();
    }

    public Integer getSpellIndex() {
        String Spell = ListDialog.showDialog(JP, null, "Select a spell", "Spell select", SpellNames, null, null);
        for (int i = 0; i < P.getSpellsInventory().size(); ++i)
            if (P.getSpellsInventory().get(i).getSpellID().equals(Spell))
                return i;
        return -1;
    }
}
