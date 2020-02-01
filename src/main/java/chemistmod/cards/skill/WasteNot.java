package chemistmod.cards.skill;

import chemistmod.ChemistMod;
import chemistmod.actions.MixAction;
import chemistmod.actions.StockpileAction;
import chemistmod.cards.BaseChemistCard;
import chemistmod.characters.TheChemist;
import chemistmod.reagents.ReagentEnum;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WasteNot extends BaseChemistCard {
    public static final String CARD_ID = ChemistMod.makeId("WasteNot");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(CARD_ID);

    private static final int BASE_COST = 1;

    public WasteNot() {
        super(CARD_ID, CARD_STRINGS.NAME, ChemistMod.getCardImagePath(CARD_ID), BASE_COST, CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, TheChemist.Enums.CARD_GOLD, CardRarity.RARE, CardTarget.SELF);
        this.requiresStockpile = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.selfRetain = true;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        // This cast should be safe; we check in the base class
        TheChemist chemist = (TheChemist)player;
        if (!canMix(chemist)) {
            return;
        }

        ReagentEnum first = chemist.popReagent();
        ReagentEnum second = chemist.popReagent();
        addToBot(new MixAction(first, second));
        addToBot(new StockpileAction(first));
        addToBot(new StockpileAction(second));
    }

    @Override
    public void triggerOnGlowCheck() {
        if (AbstractDungeon.player instanceof TheChemist && ((TheChemist)AbstractDungeon.player).stockpileCount() > 1) {
            this.glowColor = GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }
}
