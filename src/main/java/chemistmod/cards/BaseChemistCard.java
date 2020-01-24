package chemistmod.cards;

import basemod.abstracts.CustomCard;
import chemistmod.characters.TheChemist;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public abstract class BaseChemistCard extends CustomCard {
    protected boolean requiresStockpile;

    public BaseChemistCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        this.requiresStockpile = false;
    }

    @Override
    public boolean canUse(AbstractPlayer player, AbstractMonster monster) {
        if (!super.canUse(player, monster)) {
            return false;
        }

        // TODO - Support this outside of just the Chemist class
        if (this.requiresStockpile && !(player instanceof TheChemist)) {
            this.cantUseMessage = TheChemist.getCantDoText();
            return false;
        }

        return true;
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
