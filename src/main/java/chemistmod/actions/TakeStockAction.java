package chemistmod.actions;

import chemistmod.characters.TheChemist;
import chemistmod.reagents.ReagentEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TakeStockAction extends AbstractGameAction {
    private static final Logger log = LogManager.getLogger(FlingAction.class.getName());

    public TakeStockAction() {
    }

    @Override
    public void update() {
        // TODO: Extend stockpile functionality outside of the Chemist class
        try {
            TheChemist player = (TheChemist)AbstractDungeon.player;
            player.stockpileReagent(player.popReagent());
        } catch (ClassCastException ex) {
            log.warn("Unable to restock; current player isn't a Chemist");
        } finally {
            this.isDone = true;
        }
    }
}
