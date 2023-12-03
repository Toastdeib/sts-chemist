package chemistmod.cards.attack;

import chemistmod.ChemistMod;
import chemistmod.actions.MixAction;
import chemistmod.cards.BaseMixCard;
import chemistmod.characters.TheChemist;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FlaskBash extends BaseMixCard {
    public static final String CARD_ID = ChemistMod.makeId("FlaskBash");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(CARD_ID);

    private static final int BASE_COST = 1;
    private static final int BASE_DAMAGE = 10;

    public FlaskBash() {
        super(CARD_ID, CARD_STRINGS.NAME, ChemistMod.getCardImagePath(CARD_ID), BASE_COST, CARD_STRINGS.DESCRIPTION,
                CardType.ATTACK, TheChemist.Enums.CARD_GOLD, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = BASE_DAMAGE;
        this.exhaust = true;
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
        if (canMix(chemist)) {
            addToBot(new MixAction(chemist.popReagent(), chemist.popReagent()));
        }

        addToBot(new DamageAction(monster, new DamageInfo(player, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new MakeTempCardInHandAction(new BrokenFlask()));
    }
}
