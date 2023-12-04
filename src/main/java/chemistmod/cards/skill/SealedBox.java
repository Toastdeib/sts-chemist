package chemistmod.cards.skill;

import chemistmod.ChemistMod;
import chemistmod.cards.BaseChemistCard;
import chemistmod.characters.TheChemist;
import chemistmod.util.MixUtils;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SealedBox extends BaseChemistCard {

    public static final String CARD_ID = ChemistMod.makeId("SealedBox");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(CARD_ID);

    private static final int BASE_COST = -2;
    private static final int BASE_MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1;

    public SealedBox() {
        super(CARD_ID, CARD_STRINGS.NAME, ChemistMod.getCardImagePath(CARD_ID), BASE_COST, CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, TheChemist.Enums.CARD_GOLD, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = BASE_MAGIC;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_MAGIC);
        }
    }

    @Override
    public boolean canUse(AbstractPlayer player, AbstractMonster monster) {
        return false;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {

    }

    @Override
    public void triggerWhenDrawn() {
        if ((AbstractDungeon.player instanceof TheChemist)) {
            TheChemist chemist = (TheChemist)AbstractDungeon.player;
            for (int i = 0; i < this.magicNumber; i++) {
                chemist.stockpileReagent(MixUtils.getRandomReagent());
            }
        }

        addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
    }
}
