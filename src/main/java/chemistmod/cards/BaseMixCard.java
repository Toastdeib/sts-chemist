package chemistmod.cards;

import chemistmod.characters.TheChemist;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BaseMixCard extends BaseChemistCard {
    private static final Logger log = LogManager.getLogger(BaseMixCard.class.getName());

    public BaseMixCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        this.requiresStockpile = true;
    }

    @Override
    public void triggerOnGlowCheck() {
        if (AbstractDungeon.player instanceof TheChemist && ((TheChemist)AbstractDungeon.player).stockpileCount() > 1) {
            this.glowColor = GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    protected boolean canMix(TheChemist chemist) {
        if (chemist.stockpileCount() < 2) {
            AbstractDungeon.effectList.add(new ThoughtBubble(chemist.dialogX, chemist.dialogY, 3.0f,
                    TheChemist.getNotEnoughReagentsText(), true));
            return false;
        }

        return true;
    }
}
