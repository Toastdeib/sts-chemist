package chemistmod.actions;

import chemistmod.characters.TheChemist;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TransformReagentAction extends AbstractGameAction {
    private int transformIndex;

    public TransformReagentAction(int transformIndex) {
        this.transformIndex = transformIndex;
    }

    @Override
    public void update() {
        // This cast should be safe; we only use this action after a check
        TheChemist chemist = (TheChemist)AbstractDungeon.player;
        if (this.transformIndex >= chemist.stockpileCount()) {
            // Extra safeguard; right now, transformIndex should always be 0, but we don't want any
            // ArrayIndexOutOfBoundsExceptions cropping up
            this.transformIndex = chemist.stockpileCount() - 1;
        }

        chemist.transformReagent(this.transformIndex);
        this.isDone = true;
    }
}
