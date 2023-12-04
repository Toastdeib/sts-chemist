package chemistmod.powers;

import chemistmod.ChemistMod;
import chemistmod.relics.ReinforcedFlask;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ImbalancePower extends AbstractPower {
    public static final String POWER_ID = ChemistMod.makeId("ImbalancePower");
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private static final float BASE_EFFECTIVENESS = 1.5f;
    private static final float UPGRADE_EFFECTIVENESS = 1.75f;
    private static final String BASE_EFFECTIVENESS_STRING = "50";
    private static final String UPGRADE_EFFECTIVENESS_STRING = "75";

    public ImbalancePower(AbstractCreature owner, int amount) {
        this.name = POWER_STRINGS.NAME;
        this.ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        this.type = PowerType.DEBUFF;
        this.canGoNegative = false;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(new Texture(ChemistMod.getPowerImagePath(POWER_ID, "84")), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(new Texture(ChemistMod.getPowerImagePath(POWER_ID, "32")), 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        String percentage = AbstractDungeon.player.hasRelic(ReinforcedFlask.RELIC_ID) ?
                UPGRADE_EFFECTIVENESS_STRING : BASE_EFFECTIVENESS_STRING;
        if (this.amount == 1) {
            this.description = POWER_STRINGS.DESCRIPTIONS[0] + percentage + POWER_STRINGS.DESCRIPTIONS[1] +
                    this.amount + POWER_STRINGS.DESCRIPTIONS[2];
        } else {
            this.description = POWER_STRINGS.DESCRIPTIONS[0] + percentage + POWER_STRINGS.DESCRIPTIONS[1] +
                    this.amount + POWER_STRINGS.DESCRIPTIONS[3];
        }
    }

    @Override
    public void atEndOfRound() {
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        }
    }

    public static float getModifier() {
        return AbstractDungeon.player.hasRelic(ReinforcedFlask.RELIC_ID) ? UPGRADE_EFFECTIVENESS : BASE_EFFECTIVENESS;
    }
}
