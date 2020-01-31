package chemistmod.cards.skill;

import chemistmod.ChemistMod;
import chemistmod.cards.BaseChemistCard;
import chemistmod.characters.TheChemist;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Recover extends BaseChemistCard {
    public static final String CARD_ID = ChemistMod.makeId("Recover");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(CARD_ID);

    private static final int BASE_COST = 1;
    private static final int BASE_BLOCK = 4;
    private static final int BASE_MAGIC = 1;
    private static final int UPGRADE_BLOCK = 2;

    public Recover() {
        super(CARD_ID, CARD_STRINGS.NAME, ChemistMod.getCardImagePath(CARD_ID), BASE_COST, CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, TheChemist.Enums.CARD_GOLD, CardRarity.RARE, CardTarget.SELF);
        this.block = this.baseBlock = BASE_BLOCK;
        this.magicNumber = this.baseMagicNumber = BASE_MAGIC;
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
        int count = 0;
        if (player.hasPower("Vulnerable")) {
            count++;
        }
        if (player.hasPower("Frail")) {
            count++;
        }
        if (player.hasPower("Weakened")) {
            count++;
        }

        addToBot(new ReducePowerAction(player, player, "Vulnerable", this.magicNumber));
        addToBot(new ReducePowerAction(player, player, "Frail", this.magicNumber));
        addToBot(new ReducePowerAction(player, player, "Weakened", this.magicNumber));

        if (count > 0) {
            addToBot(new GainBlockAction(player, player, this.block * count));
        }
    }
}
