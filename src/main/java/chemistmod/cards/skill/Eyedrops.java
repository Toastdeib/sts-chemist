package chemistmod.cards.skill;

import chemistmod.ChemistMod;
import chemistmod.actions.StockpileAction;
import chemistmod.cards.BaseChemistCard;
import chemistmod.characters.TheChemist;
import chemistmod.reagents.ReagentEnum;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Eyedrops extends BaseChemistCard {
    public static final String CARD_ID = ChemistMod.makeId("Eyedrops");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(CARD_ID);

    private static final int BASE_COST = 1;
    private static final int BASE_MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;

    public Eyedrops() {
        super(CARD_ID, CARD_STRINGS.NAME, ChemistMod.getCardImagePath(CARD_ID), BASE_COST, CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, TheChemist.Enums.CARD_GOLD, CardRarity.COMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = BASE_MAGIC;
        this.requiresStockpile = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_MAGIC);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new DrawCardAction(player, this.magicNumber, false));
        addToBot(new StockpileAction(ReagentEnum.REMEDY));
    }
}
