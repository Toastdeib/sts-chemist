package chemistmod.cards.skill;

import chemistmod.ChemistMod;
import chemistmod.actions.MixAction;
import chemistmod.cards.BaseMixCard;
import chemistmod.characters.TheChemist;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Mix extends BaseMixCard {
    public static final String CARD_ID = ChemistMod.makeId("Mix");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(CARD_ID);

    private static final int BASE_COST = 0;

    public Mix() {
        super(CARD_ID, CARD_STRINGS.NAME, ChemistMod.getCardImagePath(CARD_ID), BASE_COST, CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, TheChemist.Enums.CARD_GOLD, CardRarity.BASIC, CardTarget.SELF);
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

        addToBot(new MixAction(chemist.popReagent(), chemist.popReagent()));
    }
}
