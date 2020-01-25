package chemistmod.cards.skill;

import chemistmod.ChemistMod;
import chemistmod.actions.TransformReagentAction;
import chemistmod.cards.BaseChemistCard;
import chemistmod.characters.TheChemist;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class EqualExchange extends BaseChemistCard {
    public static final String CARD_ID = ChemistMod.makeId("EqualExchange");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(CARD_ID);

    private static final int BASE_COST = 1;
    private static final int UPGRADE_COST = 0;
    private static final int TRANSFORM_INDEX = 0;

    public EqualExchange() {
        super(CARD_ID, CARD_STRINGS.NAME, ChemistMod.getCardImagePath(CARD_ID), BASE_COST, CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, TheChemist.Enums.CARD_GOLD, CardRarity.COMMON, CardTarget.SELF);
        this.requiresStockpile = true;
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
        // This cast should be safe; we check in the base class
        TheChemist chemist = (TheChemist)player;
        if (chemist.stockpileCount() == 0) {
            // Nothing to fling
            // TODO - Is this thought bubble actually useful?
            AbstractDungeon.effectList.add(new ThoughtBubble(player.dialogX, player.dialogY, 3.0f,
                    TheChemist.getNoReagentsText(), true));
            return;
        }

        addToBot(new TransformReagentAction(TRANSFORM_INDEX));
    }
}
