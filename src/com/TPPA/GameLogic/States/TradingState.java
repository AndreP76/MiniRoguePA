package com.TPPA.GameLogic.States;

import com.TPPA.GameLogic.GameStateController;
import com.TPPA.GameLogic.IState;
import com.TPPA.GameLogic.Internals.Action;
import com.TPPA.GameLogic.Internals.InternalCommandsDictionary;
import com.TPPA.GameLogic.Spells.FireSpell;
import com.TPPA.GameLogic.Spells.HealSpell;
import com.TPPA.GameLogic.Spells.IceSpell;
import com.TPPA.GameLogic.Spells.PoisonSpell;
import com.TPPA.Main;

import static com.TPPA.Main.ErrorStream;

/**
 * Created by andre on 4/5/17.
 */
public class TradingState extends GameState {

    public TradingState(GameStateController GSC) {
        super(GSC);
    }

    @Override
    public Action[] GetActions() {
        Action[] Act = new Action[8];
        Act[0] = new Action(InternalCommandsDictionary.BuyRation, "Buy Ration: +1 Food / -1 Gold");
        Act[1] = new Action(InternalCommandsDictionary.BuyHealthPotion, "Buy Health Potion: +1 HP / -1 Gold");
        Act[2] = new Action(InternalCommandsDictionary.BuyBigHealthPotion, "Buy Big Health Potion: +4 HP / -3 Gold");
        Act[3] = new Action(InternalCommandsDictionary.BuyArmorPiece, "Buy Armor Piece: +1 Armor / -6 Gold");
        Act[4] = new Action(InternalCommandsDictionary.BuySpell, "Buy Spell: +1 Spell of your choice / -8 Gold");
        Act[5] = new Action(InternalCommandsDictionary.SellArmorPiece, "Sell Armor Piece: -1 Armor / +3 Gold");
        Act[6] = new Action(InternalCommandsDictionary.SellSpell, "Sell Spell: -1 Spell of your choice / +4 Gold");
        Act[7] = new Action(InternalCommandsDictionary.EndTradingState, "Skip to next phase");
        return Act;
    }

    @Override
    public IState Action(String ActionString) {
        String[] SSplit = ActionString.split(" ");

        if (SSplit[0].equals(InternalCommandsDictionary.BuyRation)) {
            if (getCurrentController().getCurrentPlayer().incFood(1)) {
                if (!(getCurrentController().getCurrentPlayer().incGold(-1))) {
                    getCurrentController().getCurrentPlayer().incFood(-1);
                    Main.ErrorStream.println("BuyRation failed: not enough money");
                }
            } else
                Main.ErrorStream.println("BuyRation failed: cannot carry more food");
            return this;
        }
        if (SSplit[0].equals(InternalCommandsDictionary.BuyHealthPotion)) {
            if (getCurrentController().getCurrentPlayer().incHP(1)) {
                if (!getCurrentController().getCurrentPlayer().incGold(-1)) {
                    getCurrentController().getCurrentPlayer().incHP(-1);
                    Main.ErrorStream.println("BuyHealthPotion failed: not enough money");
                }
            } else
                Main.ErrorStream.println("BuyHealthPotion failed: maximum health reached");
            return this;
        }
        if (SSplit[0].equals(InternalCommandsDictionary.BuyBigHealthPotion)) {
            if (getCurrentController().getCurrentPlayer().incHP(4)) {
                if (!getCurrentController().getCurrentPlayer().incGold(-3)) {
                    getCurrentController().getCurrentPlayer().incHP(-4);
                    Main.ErrorStream.println("BuyBigHealthPotion failed: not enough money");
                }
            } else
                Main.ErrorStream.println("BuyBigHealthPotion failed: maximum health reached");
            return this;
        }
        if (SSplit[0].equals(InternalCommandsDictionary.BuyArmorPiece)) {
            if (getCurrentController().getCurrentPlayer().incArmor(1)) {
                if (!getCurrentController().getCurrentPlayer().incGold(-6)) {
                    getCurrentController().getCurrentPlayer().incArmor(-1);
                    Main.ErrorStream.println("BuyArmorPiece failed: not enough money");
                }
            } else
                Main.ErrorStream.println("BuyArmorPiece failed: cannot carry more armor");
            return this;
        }
        if (SSplit[0].equals(InternalCommandsDictionary.BuySpell)) {
            if (SSplit.length >= 2) {
                if (!buySpell(SSplit[1])) {
                    Main.ErrorStream.println("BuySpell failed: cannot carry more spells / not enough money / unknown spell id");
                }
            } else
                Main.ErrorStream.println("BuySpell failed: spell id was not indicated");
            return this;
        }
        if (SSplit[0].equals(InternalCommandsDictionary.SellArmorPiece)) {
            if (getCurrentController().getCurrentPlayer().incArmor(-1)) {
                if (!getCurrentController().getCurrentPlayer().incGold(3)) {
                    getCurrentController().getCurrentPlayer().incArmor(1);
                    Main.ErrorStream.println("SellArmorPiece failed: cannot carry more gold");
                }
            } else
                Main.ErrorStream.println("SellArmorPiece failed: not enough armor");
            return this;
        }
        if (SSplit[0].equals(InternalCommandsDictionary.SellSpell)) {
            if (SSplit.length >= 2) {
                int index;
                try {
                    index = Integer.parseInt(SSplit[1]);
                    if (!sellSpell(index))
                        Main.ErrorStream.println("SellSpell failed: cannot carry more money / spell index out of bounds");
                } catch (NumberFormatException e) {
                    ErrorStream.println("Second argument of " + InternalCommandsDictionary.SellSpell + " is not a valid number");
                }

            }
            return this;
        }
        if (SSplit[0].equals(InternalCommandsDictionary.EndTradingState)) {
            return new AwaitCardSelectionState(getCurrentController());
        }
        return this;
    }

    public boolean buySpell(String SpellId) {

        if (getCurrentController().getCurrentPlayer().getSpellsInventory().size() >= 2)
            return false;

        if (!getCurrentController().getCurrentPlayer().incGold(-8))
            return false;

        if (SpellId.equals(InternalCommandsDictionary.FireSpellID))
            if (getCurrentController().getCurrentPlayer().getSpellsInventory().add(new FireSpell(getCurrentController(), InternalCommandsDictionary.FireSpellID)))
                return true;

            else if (SpellId.equals(InternalCommandsDictionary.HealSpellID))
                if (getCurrentController().getCurrentPlayer().getSpellsInventory().add(new HealSpell(getCurrentController(), InternalCommandsDictionary.HealSpellID)))
                    return true;

                else if (SpellId.equals(InternalCommandsDictionary.IceSpellID))
                    if (getCurrentController().getCurrentPlayer().getSpellsInventory().add(new IceSpell(getCurrentController(), InternalCommandsDictionary.IceSpellID)))
                        return true;

                    else if (SpellId.equals(InternalCommandsDictionary.PoisonSpellID))
                        if (getCurrentController().getCurrentPlayer().getSpellsInventory().add(new PoisonSpell(getCurrentController(), InternalCommandsDictionary.PoisonSpellID)))
                            return true;

        getCurrentController().getCurrentPlayer().incGold(8);

        return false;

    }

    public boolean sellSpell(int index) {
        if (!getCurrentController().getCurrentPlayer().incGold(4))
            return false;
        try {
            getCurrentController().getCurrentPlayer().getSpellsInventory().remove(index);
        } catch (IndexOutOfBoundsException e) {
            ErrorStream.println("Second argument of " + InternalCommandsDictionary.SellSpell + " is not a valid index");
            getCurrentController().getCurrentPlayer().incGold(-4);
            return false;
        }

        return true;

    }
}