package chemistmod.actions;

import chemistmod.characters.TheChemist;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TakeStockAction extends AbstractGameAction {
    public TakeStockAction() {
    }

    @Override
    public void update() {
        // This cast should be safe; we check in the base class
        TheChemist player = (TheChemist)AbstractDungeon.player;
        player.stockpileReagent(player.popReagent());
        this.isDone = true;
    }
}
