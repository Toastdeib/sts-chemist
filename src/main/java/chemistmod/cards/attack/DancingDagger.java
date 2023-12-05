package chemistmod.cards.attack;

import chemistmod.ChemistMod;
import chemistmod.cards.BaseChemistCard;
import chemistmod.characters.TheChemist;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.ArrayList;

public class DancingDagger extends BaseChemistCard {
    public static final String CARD_ID = ChemistMod.makeId("DancingDagger");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(CARD_ID);

    private static final int BASE_COST = 2;
    private static final int BASE_DAMAGE = 10;
    private static final int BASE_MAGIC = 1;
    private static final int UPGRADE_DAMAGE = 4;
    private static final int UPGRADE_MAGIC = 1;

    public DancingDagger() {
        super(CARD_ID, CARD_STRINGS.NAME, ChemistMod.getCardImagePath(CARD_ID), BASE_COST, CARD_STRINGS.DESCRIPTION,
                CardType.ATTACK, TheChemist.Enums.CARD_GOLD, CardRarity.RARE, CardTarget.ENEMY);
        this.damage = this.baseDamage = BASE_DAMAGE;
        this.magicNumber = this.baseMagicNumber = BASE_MAGIC;
        this.requiresStockpile = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE);
            this.upgradeMagicNumber(UPGRADE_MAGIC);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        ArrayList<AbstractGameAction> actions = new ArrayList<>();
        switch (monster.intent) {
            case ATTACK:
            case ATTACK_BUFF:
            case ATTACK_DEBUFF:
            case ATTACK_DEFEND:
            case DEFEND:
            case DEFEND_BUFF:
            case DEFEND_DEBUFF:
                // Attacking or blocking; apply debuffs
                actions.add(new DamageAction(monster, new DamageInfo(player, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                actions.add(new ApplyPowerAction(monster, player, new WeakPower(monster, this.magicNumber, false), this.magicNumber));
                actions.add(new ApplyPowerAction(monster, player, new VulnerablePower(monster, this.magicNumber, false), this.magicNumber));
                break;
            default:
                // Anything else; do double damage
                actions.add(new DamageAction(monster, new DamageInfo(player, this.damage * 2, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                break;
        }

        for (AbstractGameAction action : actions) {
            addToBot(action);
        }
    }
}
