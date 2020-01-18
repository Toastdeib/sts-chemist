package chemistmod.actions;

import chemistmod.characters.TheChemist;
import chemistmod.reagents.AbstractReagent;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StockpileAction extends AbstractGameAction {

    private static final Logger log = LogManager.getLogger(FlingAction.class.getName());

    private AbstractReagent reagent;

    public StockpileAction(AbstractReagent reagent) {
        this.reagent = reagent;
    }

    @Override
    public void update() {
        // TODO:
        //  Extend stockpile/fling functionality outside of The Chemist
        try {
            TheChemist player = (TheChemist) AbstractDungeon.player;
            player.stockpileReagent(this.reagent);
        } catch (ClassCastException ex) {
            log.warn("Unable to stockpile; current player isn't a Chemist");
        } finally {
            this.isDone = true;
        }
    }
}
