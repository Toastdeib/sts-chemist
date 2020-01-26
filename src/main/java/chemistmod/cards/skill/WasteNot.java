package chemistmod.cards.skill;

import chemistmod.ChemistMod;
import chemistmod.actions.MixAction;
import chemistmod.actions.StockpileAction;
import chemistmod.cards.BaseChemistCard;
import chemistmod.characters.TheChemist;
import chemistmod.reagents.ReagentEnum;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WasteNot extends BaseChemistCard {
    public static final String CARD_ID = ChemistMod.makeId("WasteNot");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(CARD_ID);

    private static final int BASE_COST = 2;

    public WasteNot() {
        super(CARD_ID, CARD_STRINGS.NAME, ChemistMod.getCardImagePath(CARD_ID), BASE_COST, CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, TheChemist.Enums.CARD_GOLD, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
        this.requiresStockpile = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.exhaust = false;
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
}
