package com.TPPA.GameLogic;
// Olá MUndo
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
            GameStateController.getCurrentController().getCurrentPlayer().incFood(1);
            GameStateController.getCurrentController().getCurrentPlayer().incGold(-1);
            return this;
        }
        if (SSplit[0].equals(InternalCommandsDictionary.BuyHealthPotion)) {
            GameStateController.getCurrentController().getCurrentPlayer().incHP(1);
            GameStateController.getCurrentController().getCurrentPlayer().incGold(-1);
            return this;
        }
        if (SSplit[0].equals(InternalCommandsDictionary.BuyBigHealthPotion)) {
            GameStateController.getCurrentController().getCurrentPlayer().incHP(4);
            GameStateController.getCurrentController().getCurrentPlayer().incGold(-3);
            return this;
        }
        if (SSplit[0].equals(InternalCommandsDictionary.BuyArmorPiece)) {
            GameStateController.getCurrentController().getCurrentPlayer().incArmor(1);
            GameStateController.getCurrentController().getCurrentPlayer().incGold(-6);
            return this;
        }
        if (SSplit[0].equals(InternalCommandsDictionary.BuySpell)) {
            buyRandomSpell();
            GameStateController.getCurrentController().getCurrentPlayer().incGold(-8);
            return this;
        }
        if (SSplit[0].equals(InternalCommandsDictionary.SellArmorPiece)) {
            GameStateController.getCurrentController().getCurrentPlayer().incArmor(-1);
            GameStateController.getCurrentController().getCurrentPlayer().incGold(3);
            return this;
        }
        if (SSplit[0].equals(InternalCommandsDictionary.SellSpell)) {
            //TODO: escolher feitiço para vender
            if (SSplit.length >= 2) {
                int index;
                try {
                    index = Integer.parseInt(SSplit[1]);
                    sellSpell(index);
                } catch (NumberFormatException e) {
                    ErrorStream.println("Second argument of " + InternalCommandsDictionary.SellSpell + " is not a valid number");
                }
                GameStateController.getCurrentController().getCurrentPlayer().incGold(4);
            }
            return this;
        }
        if (SSplit[0].equals(InternalCommandsDictionary.EndTradingState)) {
            return new AwaitCardSelectionState();
        }
        return this;
    }

    public void buyRandomSpell() {
        int id = (int) (Math.random() % 4);

        switch (id) {
            case 0:
                GameStateController.getCurrentController().getCurrentPlayer().getSpellsInventory().add(new FireSpell(InternalCommandsDictionary.FireSpellID));
                break;
            case 1:
                GameStateController.getCurrentController().getCurrentPlayer().getSpellsInventory().add(new HealSpell(InternalCommandsDictionary.HealSpellID));
                break;
            case 2:
                GameStateController.getCurrentController().getCurrentPlayer().getSpellsInventory().add(new IceSpell(InternalCommandsDictionary.IceSpellID));
                break;
            case 3:
                GameStateController.getCurrentController().getCurrentPlayer().getSpellsInventory().add(new PoisonSpell(InternalCommandsDictionary.PoisonSpellID));
                break;
        }
    }

    public void sellSpell(int index) {
        try {
            GameStateController.getCurrentController().getCurrentPlayer().getSpellsInventory().remove(index);
        } catch (IndexOutOfBoundsException e) {
            ErrorStream.println("Second argument of " + InternalCommandsDictionary.SellSpell + " is not a valid index");
        }
    }
}