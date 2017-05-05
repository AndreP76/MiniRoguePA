package com.TPPA.GameLogic;

import com.TPPA.GameLogic.Spells.FireSpell;
import com.TPPA.GameLogic.Spells.HealSpell;
import com.TPPA.GameLogic.Spells.IceSpell;
import com.TPPA.GameLogic.Spells.PoisonSpell;

import static com.TPPA.GameLogic.Main.ErrorStream;

/**
 * Created by andre on 4/5/17.
 */
public class TradingState extends GameState {

    @Override
    public Action[] GetActions() {
        Action[] Act = new Action[8];
        Act[0] = new Action(InternalCommandsDictionary.BuyRation, "Comprar comida: +1 Food / -1 Gold");
        Act[1] = new Action(InternalCommandsDictionary.BuyHealthPotion, "Comprar poção de vida: +1 HP / -1 Gold");
        Act[2] = new Action(InternalCommandsDictionary.BuyBigHealthPotion, "Comprar poção de vida grande: +4 HP / -3 Gold");
        Act[3] = new Action(InternalCommandsDictionary.BuyArmorPiece, "Comprar peça de armadura: +1 Armor / -6 Gold");
        Act[4] = new Action(InternalCommandsDictionary.BuySpell, "Comprar feitiço: +1 Spell aleatório / -8 Gold");
        Act[5] = new Action(InternalCommandsDictionary.SellArmorPiece, "Vender peça de armadura: -1 Armor / +3 Gold");
        Act[6] = new Action(InternalCommandsDictionary.SellSpell, "Vender feitiço: -1 Spell / +4 Gold");
        Act[7] = new Action(InternalCommandsDictionary.EndTradingState, "Passar à fase seguinte");
        return Act;
    }

    @Override
    public IState Action(String ActionString) {
        String[] SSplit = ActionString.split(" ");

        if (SSplit[0].equals(InternalCommandsDictionary.BuyRation)) {
            if (GameStateController.getCurrentController().getCurrentPlayer().incFood(1)) {
                if (!(GameStateController.getCurrentController().getCurrentPlayer().incGold(-1))) {
                    GameStateController.getCurrentController().getCurrentPlayer().incFood(-1);
                    Main.ErrorStream.println("BuyRation failed: not enough money");
                }
            } else
                Main.ErrorStream.println("BuyRation failed: cannot carry more food");
            return this;
        }
        if (SSplit[0].equals(InternalCommandsDictionary.BuyHealthPotion)) {
            if (GameStateController.getCurrentController().getCurrentPlayer().incHP(1)) {
                if (!GameStateController.getCurrentController().getCurrentPlayer().incGold(-1)) {
                    GameStateController.getCurrentController().getCurrentPlayer().incHP(-1);
                    Main.ErrorStream.println("BuyHealthPotion failed: not enough money");
                }
            } else
                Main.ErrorStream.println("BuyHealthPotion failed: maximum health reached");
            return this;
        }
        if (SSplit[0].equals(InternalCommandsDictionary.BuyBigHealthPotion)) {
            if (GameStateController.getCurrentController().getCurrentPlayer().incHP(4)) {
                if (!GameStateController.getCurrentController().getCurrentPlayer().incGold(-3)) {
                    GameStateController.getCurrentController().getCurrentPlayer().incHP(-4);
                    Main.ErrorStream.println("BuyBigHealthPotion failed: not enough money");
                }
            } else
                Main.ErrorStream.println("BuyBigHealthPotion failed: maximum health reached");
            return this;
        }
        if (SSplit[0].equals(InternalCommandsDictionary.BuyArmorPiece)) {
            if (GameStateController.getCurrentController().getCurrentPlayer().incArmor(1)) {
                if (!GameStateController.getCurrentController().getCurrentPlayer().incGold(-6)) {
                    GameStateController.getCurrentController().getCurrentPlayer().incArmor(-1);
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
            if (GameStateController.getCurrentController().getCurrentPlayer().incArmor(-1)) {
                if (!GameStateController.getCurrentController().getCurrentPlayer().incGold(3)) {
                    GameStateController.getCurrentController().getCurrentPlayer().incArmor(1);
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
            return new AwaitCardSelectionState();
        }
        return this;
    }

    public boolean buySpell(String SpellId) {

        if (GameStateController.getCurrentController().getCurrentPlayer().getSpellsInventory().size() >= 2)
            return false;

        if (!GameStateController.getCurrentController().getCurrentPlayer().incGold(-8))
            return false;

        if (SpellId.equals(InternalCommandsDictionary.FireSpellID))
            if (GameStateController.getCurrentController().getCurrentPlayer().getSpellsInventory().add(new FireSpell(InternalCommandsDictionary.FireSpellID)))
                return true;

            else if (SpellId.equals(InternalCommandsDictionary.HealSpellID))
                if (GameStateController.getCurrentController().getCurrentPlayer().getSpellsInventory().add(new HealSpell(InternalCommandsDictionary.HealSpellID)))
                    return true;

                else if (SpellId.equals(InternalCommandsDictionary.IceSpellID))
                    if (GameStateController.getCurrentController().getCurrentPlayer().getSpellsInventory().add(new IceSpell(InternalCommandsDictionary.IceSpellID)))
                        return true;

                    else if (SpellId.equals(InternalCommandsDictionary.PoisonSpellID))
                        if (GameStateController.getCurrentController().getCurrentPlayer().getSpellsInventory().add(new PoisonSpell(InternalCommandsDictionary.PoisonSpellID)))
                            return true;

        GameStateController.getCurrentController().getCurrentPlayer().incGold(8);

        return false;

    }

    public boolean sellSpell(int index) {
        if (!GameStateController.getCurrentController().getCurrentPlayer().incGold(4))
            return false;
        try {
            GameStateController.getCurrentController().getCurrentPlayer().getSpellsInventory().remove(index);
        } catch (IndexOutOfBoundsException e) {
            ErrorStream.println("Second argument of " + InternalCommandsDictionary.SellSpell + " is not a valid index");
            GameStateController.getCurrentController().getCurrentPlayer().incGold(-4);
            return false;
        }

        return true;

    }
}