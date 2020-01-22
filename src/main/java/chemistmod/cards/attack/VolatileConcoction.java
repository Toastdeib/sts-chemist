package chemistmod.cards.attack;

import chemistmod.ChemistMod;
import chemistmod.cards.BaseChemistCard;
import chemistmod.characters.TheChemist;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class VolatileConcoction extends BaseChemistCard {
    public static final String CARD_ID = ChemistMod.makeId("VolatileConcoction");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(CARD_ID);

    private static final int BASE_COST = 2;
    private static final int BASE_DAMAGE = 16;
    private static final int UPGRADE_DAMAGE = 4;

    public VolatileConcoction() {
        super(CARD_ID, CARD_STRINGS.NAME, ChemistMod.getCardImagePath(CARD_ID), BASE_COST, CARD_STRINGS.DESCRIPTION,
                CardType.ATTACK, TheChemist.Enums.CARD_GOLD, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.damage = this.baseDamage = BASE_DAMAGE;
        this.isMultiDamage = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        // Should be a safe cast here; there's a check for the class in canUse()
        ((TheChemist)player).emptyStockpile();
        addToBot(new DamageAllEnemiesAction(player, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public boolean canUse(AbstractPlayer player, AbstractMonster monster) {
        if (!super.canUse(player, monster)) {
            return false;
        }

        if (!(player instanceof TheChemist)) {
            // TODO - Make this work outside of the Chemist class
            this.cantUseMessage = CARD_STRINGS.EXTENDED_DESCRIPTION[1];
            return false;
        }

        if (((TheChemist)player).stockpileCount() < 2) {
            this.cantUseMessage = CARD_STRINGS.EXTENDED_DESCRIPTION[0];
            return false;
        }

        return true;
    }
}
