package chemistmod.cards.skill;

import chemistmod.ChemistMod;
import chemistmod.cards.BaseChemistCard;
import chemistmod.characters.TheChemist;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

public class Nightshade extends BaseChemistCard {
    public static final String CARD_ID = ChemistMod.makeId("Nightshade");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(CARD_ID);

    private static final int BASE_COST = 4;
    private static final int UPGRADE_COST = 3;

    private static final float KILL_THRESHOLD = 0.2f;

    public Nightshade() {
        super(CARD_ID, CARD_STRINGS.NAME, ChemistMod.getCardImagePath(CARD_ID), BASE_COST, CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, TheChemist.Enums.CARD_GOLD, CardRarity.RARE, CardTarget.ENEMY);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        if ((float)monster.currentHealth / monster.maxHealth > KILL_THRESHOLD) {
            AbstractDungeon.effectList.add(new ThoughtBubble(player.dialogX, player.dialogY, 3.0f,
                    TheChemist.getTooHealthyText(), true));
            return;
        }

        addToBot(new VFXAction(new WeightyImpactEffect(monster.hb.cX, monster.hb.cY, Color.GREEN.cpy())));
        addToBot(new InstantKillAction(monster));
    }
}
