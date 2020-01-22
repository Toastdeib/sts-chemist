package chemistmod.cards.skill;

import chemistmod.ChemistMod;
import chemistmod.actions.TakeStockAction;
import chemistmod.cards.BaseChemistCard;
import chemistmod.characters.TheChemist;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TakeStock extends BaseChemistCard {
    public static final String CARD_ID = ChemistMod.makeId("TakeStock");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(CARD_ID);

    private static final int BASE_COST = 1;
    private static final int UPGRADE_COST = 0;

    public TakeStock() {
        super(CARD_ID, CARD_STRINGS.NAME, ChemistMod.getCardImagePath(CARD_ID), BASE_COST, CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, TheChemist.Enums.CARD_GOLD, CardRarity.COMMON, CardTarget.SELF);
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
        // TODO - Support this outside of just the Chemist class
        if (!(player instanceof TheChemist)) {
            return;
        }

        TheChemist chemist = (TheChemist)player;
        if (chemist.stockpileCount() < 2) {
            // Nothing to move
            return;
        }

        addToBot(new TakeStockAction());
    }
}
