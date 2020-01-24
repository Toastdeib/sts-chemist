package chemistmod.cards.skill;

import chemistmod.ChemistMod;
import chemistmod.actions.FlingAction;
import chemistmod.cards.BaseChemistCard;
import chemistmod.characters.TheChemist;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class Screen extends BaseChemistCard {
    public static final String CARD_ID = ChemistMod.makeId("Screen");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(CARD_ID);

    private static final int BASE_COST = 1;
    private static final int BASE_BLOCK = 8;
    private static final int UPGRADE_BLOCK = 3;

    public Screen() {
        super(CARD_ID, CARD_STRINGS.NAME, ChemistMod.getCardImagePath(CARD_ID), BASE_COST, CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, TheChemist.Enums.CARD_GOLD, CardRarity.COMMON, CardTarget.SELF);
        this.block = this.baseBlock = BASE_BLOCK;
        this.requiresStockpile = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_BLOCK);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new GainBlockAction(player, player, this.block));

        // This cast should be safe; we check in the base class
        TheChemist chemist = (TheChemist)player;
        if (chemist.stockpileCount() == 0) {
            // Nothing to fling
            // TODO - Is this thought bubble actually useful?
            AbstractDungeon.effectList.add(new ThoughtBubble(player.dialogX, player.dialogY, 3.0f,
                    TheChemist.getNoReagentsText(), true));
            return;
        }

        addToBot(new FlingAction());
        chemist.popReagent();
    }
}
