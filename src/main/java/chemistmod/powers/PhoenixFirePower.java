package chemistmod.powers;

import chemistmod.ChemistMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PhoenixFirePower extends AbstractPower {
    public static final String POWER_ID = ChemistMod.makeId("PhoenixFirePower");
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final int REVIVE_PERCENT = 10;

    public PhoenixFirePower(AbstractCreature owner) {
        this.name = POWER_STRINGS.NAME;
        this.ID = POWER_ID;

        this.owner = owner;

        this.type = PowerType.BUFF;
        this.canGoNegative = false;
        this.isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(new Texture(ChemistMod.getPowerImagePath(POWER_ID, "84")), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(new Texture(ChemistMod.getPowerImagePath(POWER_ID, "32")), 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = POWER_STRINGS.DESCRIPTIONS[0] + REVIVE_PERCENT + POWER_STRINGS.DESCRIPTIONS[1];
    }

    @Override
    public void onDeath() {
        AbstractPlayer player = AbstractDungeon.player;
        player.currentHealth = 0;
        player.heal(player.maxHealth / REVIVE_PERCENT, true);
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, PhoenixFirePower.POWER_ID));
    }
}
