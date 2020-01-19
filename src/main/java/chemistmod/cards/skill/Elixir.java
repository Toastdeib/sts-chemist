package chemistmod.cards.skill;

import chemistmod.ChemistMod;
import chemistmod.cards.BaseChemistCard;
import chemistmod.characters.TheChemist;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Elixir extends BaseChemistCard {
    public static final String CARD_ID = ChemistMod.makeId("Elixir");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(CARD_ID);

    private static final int BASE_COST = 1;
    private static final int BASE_BLOCK = 4;
    private static final int BASE_HEAL = 4;
    private static final int BASE_MAGIC = 1;
    private static final int UPGRADE_BLOCK = 2;
    private static final int UPGRADE_HEAL = 2;
    private static final int UPGRADE_MAGIC = 1;

    public Elixir() {
        super(CARD_ID, CARD_STRINGS.NAME, ChemistMod.getCardImagePath(CARD_ID), BASE_COST, CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, TheChemist.Enums.CARD_GOLD, CardRarity.RARE, CardTarget.SELF);
        this.block = this.baseBlock = BASE_BLOCK;
        this.heal = this.baseHeal = BASE_HEAL;
        this.magicNumber = this.baseMagicNumber = BASE_MAGIC;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_BLOCK);
            this.baseHeal += UPGRADE_HEAL; // This is janky and I don't love it but okay then
            this.upgradeMagicNumber(UPGRADE_MAGIC);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBottom(new GainBlockAction(player, player, this.block));
        addToBottom(new HealAction(player, player, this.heal));
        addToBottom(new GainEnergyAction(this.magicNumber));
    }
}
