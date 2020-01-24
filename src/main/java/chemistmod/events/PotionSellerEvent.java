package chemistmod.events;

import chemistmod.ChemistMod;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;

public class PotionSellerEvent extends AbstractImageEvent {
    public static final String EVENT_ID = ChemistMod.makeId("PotionSeller");
    private static final EventStrings EVENT_STRINGS = CardCrawlGame.languagePack.getEventString(EVENT_ID);

    private boolean hideousMutation;
    private boolean choiceScreen;
    private int damage;

    private static final int GOLD_REWARD = 50;
    private static final int DAMAGE_PENALTY = 20;
    private static final int DAMAGE_PENALTY_A15 = 30;

    public PotionSellerEvent() {
        super(EVENT_STRINGS.NAME, EVENT_STRINGS.DESCRIPTIONS[0], ChemistMod.getEventImagePath(PotionSellerEvent.EVENT_ID));
        this.hideousMutation = AbstractDungeon.miscRng.randomBoolean();
        this.choiceScreen = true;
        this.damage = AbstractDungeon.ascensionLevel >= 15 ? DAMAGE_PENALTY_A15 : DAMAGE_PENALTY;
        this.imageEventText.setDialogOption(EVENT_STRINGS.OPTIONS[0] + GOLD_REWARD + EVENT_STRINGS.OPTIONS[1]);
        this.imageEventText.setDialogOption(EVENT_STRINGS.OPTIONS[2] + this.damage + EVENT_STRINGS.OPTIONS[3]);
    }

    @Override
    protected void buttonEffect(int i) {
        if (this.choiceScreen) {
            this.choiceScreen = false;
            this.imageEventText.updateDialogOption(0, EVENT_STRINGS.OPTIONS[4]);
            this.imageEventText.clearRemainingOptions();
            switch (i) {
                case 0: // The safe bet; 50 gold
                    this.imageEventText.updateBodyText(EVENT_STRINGS.DESCRIPTIONS[1]);
                    AbstractDungeon.player.gainGold(GOLD_REWARD);
                    CardCrawlGame.sound.play("GOLD_GAIN");
                    break;
                case 1: // The gamble; relic and possibly pain?
                    if (this.hideousMutation) {
                        this.imageEventText.updateBodyText(EVENT_STRINGS.DESCRIPTIONS[3]);
                        AbstractDungeon.player.damage(new DamageInfo(null, this.damage, DamageInfo.DamageType.HP_LOSS));
                        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
                        CardCrawlGame.sound.play("ATTACK_FAST");
                    } else {
                        this.imageEventText.updateBodyText(EVENT_STRINGS.DESCRIPTIONS[2]);
                    }
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f,
                            AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier()));
                    break;
            }

            return;
        }

        openMap();
    }
}
