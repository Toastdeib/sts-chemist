package chemistmod.actions;

import chemistmod.characters.TheChemist;
import chemistmod.powers.DefensivePosturePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TakeStockAction extends AbstractGameAction {
    public TakeStockAction() {
    }

    @Override
    public void update() {
        // This cast should be safe; we check in the base class
        TheChemist chemist = (TheChemist)AbstractDungeon.player;
        AbstractPower defensivePosture = chemist.getPower(DefensivePosturePower.POWER_ID);
        if (defensivePosture != null) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(chemist, chemist, defensivePosture.amount));
        }

        chemist.stockpileReagent(chemist.popReagent());
        this.isDone = true;
    }
}
