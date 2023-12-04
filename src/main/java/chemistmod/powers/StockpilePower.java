package chemistmod.powers;

import chemistmod.ChemistMod;
import chemistmod.characters.TheChemist;
import chemistmod.util.MixUtils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class StockpilePower extends AbstractPower {
    public static final String POWER_ID = ChemistMod.makeId("StockpilePower");
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    // TODO - Make this work outside of the Chemist class
    private TheChemist player;

    public StockpilePower(AbstractCreature owner) {
        this.name = POWER_STRINGS.NAME;
        this.ID = POWER_ID;

        this.owner = owner;
        this.player = (owner instanceof TheChemist) ? (TheChemist)owner : null;
        this.amount = 0;

        this.type = PowerType.BUFF;
        this.canGoNegative = false;
        this.isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(new Texture(ChemistMod.getPowerImagePath(POWER_ID, "84")), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(new Texture(ChemistMod.getPowerImagePath(POWER_ID, "32")), 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (this.player == null) {
            this.description = POWER_STRINGS.DESCRIPTIONS[15];
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(POWER_STRINGS.DESCRIPTIONS[0]);
        sb.append(this.player.stockpileCapacity);

        if (this.amount == 0) {
            sb.append(POWER_STRINGS.DESCRIPTIONS[14]);
            this.description = sb.toString();
            return;
        }

        for (int i = 0; i < this.amount; i++) {
            sb.append(POWER_STRINGS.DESCRIPTIONS[i + 2]);
            sb.append(getPrettyName(this.player.getReagent(i).toString()));
        }

        if (this.amount >= 2) {
            sb.append(POWER_STRINGS.DESCRIPTIONS[12]);
            sb.append(getPrettyName(MixUtils.mix(this.player.getReagent(0), this.player.getReagent(1)).toString()));
            sb.append(POWER_STRINGS.DESCRIPTIONS[13]);
        }

        this.description = sb.toString();
    }

    private String getPrettyName(String enumName) {
        return CardCrawlGame.languagePack.getPowerStrings(ChemistMod.makeId(enumName)).NAME;
    }
}
