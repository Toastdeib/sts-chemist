package chemistmod.powers;

import chemistmod.ChemistMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EfficiencyPower extends AbstractPower {
    public static final String POWER_ID = ChemistMod.makeId("EfficiencyPower");
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public EfficiencyPower(AbstractCreature owner, int amount) {
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
        this.description = POWER_STRINGS.DESCRIPTIONS[0] + this.amount +
                (this.amount > 1 ? POWER_STRINGS.DESCRIPTIONS[2] : POWER_STRINGS.DESCRIPTIONS[1]);
    }
}
