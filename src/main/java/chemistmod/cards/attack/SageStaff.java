package chemistmod.cards.attack;

import chemistmod.ChemistMod;
import chemistmod.cards.BaseChemistCard;
import chemistmod.characters.TheChemist;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SageStaff extends BaseChemistCard {
    public static final String CARD_ID = ChemistMod.makeId("SageStaff");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(CARD_ID);

    private static final int BASE_COST = 1;
    private static final int BASE_DAMAGE = 8;
    private static final int BASE_BLOCK = 5;
    private static final int BASE_MAGIC = 6;
    private static final int UPGRADE_DAMAGE = 2;
    private static final int UPGRADE_BLOCK = 1;
    private static final int UPGRADE_MAGIC = 2;

    public SageStaff() {
        super(CARD_ID, CARD_STRINGS.NAME, ChemistMod.getCardImagePath(CARD_ID), BASE_COST, CARD_STRINGS.DESCRIPTION,
                CardType.ATTACK, TheChemist.Enums.CARD_GOLD, CardRarity.RARE, CardTarget.ENEMY);
        this.damage = this.baseDamage = BASE_DAMAGE;
        this.block = this.baseBlock = BASE_BLOCK;
        this.magicNumber = this.baseMagicNumber = BASE_MAGIC;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE);
            this.upgradeBlock(UPGRADE_BLOCK);
            this.upgradeMagicNumber(UPGRADE_MAGIC);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBottom(new DamageAction(monster, new DamageInfo(player, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void triggerOnExhaust() {
        addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));
        addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, this.magicNumber));
    }
}
