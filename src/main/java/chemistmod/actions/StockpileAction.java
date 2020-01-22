package chemistmod.actions;

import chemistmod.characters.TheChemist;
import chemistmod.reagents.ReagentEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class StockpileAction extends AbstractGameAction {
    private ReagentEnum reagent;

    public StockpileAction(ReagentEnum reagent) {
        this.reagent = reagent;
    }

    @Override
    public void update() {
        // This cast should be safe; we only use this action after a check
        TheChemist player = (TheChemist)AbstractDungeon.player;
        player.stockpileReagent(this.reagent);
        this.isDone = true;
    }
}
