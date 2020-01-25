package chemistmod.powers;

import chemistmod.ChemistMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class IronDraughtPower extends AbstractPower {
    public static final String POWER_ID = ChemistMod.makeId("IronDraughtPower");
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public IronDraughtPower(AbstractCreature owner, int amount) {
        this.name = POWER_STRINGS.NAME;
        this.ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        this.canGoNegative = false;
        this.isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(new Texture(ChemistMod.getPowerImagePath(POWER_ID, "84")), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(new Texture(ChemistMod.getPowerImagePath(POWER_ID, "32")), 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (this.amount <= 1) {
            this.description = POWER_STRINGS.DESCRIPTIONS[0];
        } else {
            this.description = POWER_STRINGS.DESCRIPTIONS[1] + this.amount + POWER_STRINGS.DESCRIPTIONS[2];
        }
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (damageAmount > 0) {
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, IronDraughtPower.POWER_ID, 1));
        }

        // Round this up; rounding down feels silly (turns 1s into 0s)
        return (int)Math.ceil(damageAmount / 2.0);
    }
}
