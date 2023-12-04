package chemistmod.actions;

import chemistmod.characters.TheChemist;
import chemistmod.powers.DefensivePosturePower;
import chemistmod.powers.FromNothingPower;
import chemistmod.reagents.ReagentEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class StockpileAction extends AbstractGameAction {
    private ReagentEnum reagent;

    public StockpileAction(ReagentEnum reagent) {
        this.reagent = reagent;
    }

    @Override
    public void update() {
        // This cast should be safe; we only use this action after a check
        TheChemist chemist = (TheChemist)AbstractDungeon.player;
        AbstractPower defensivePosture = chemist.getPower(DefensivePosturePower.POWER_ID);
        if (defensivePosture != null) {
            addToBot(new GainBlockAction(chemist, chemist, defensivePosture.amount));
        }

        AbstractPower fromNothing = chemist.getPower(FromNothingPower.POWER_ID);
        if (fromNothing != null) {
            chemist.stockpileReagent(this.reagent);
            addToBot(new ReducePowerAction(chemist, chemist, FromNothingPower.POWER_ID, 1));
        }

        chemist.stockpileReagent(this.reagent);
        this.isDone = true;
    }
}
