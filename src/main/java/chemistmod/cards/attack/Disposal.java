package chemistmod.cards.attack;

import chemistmod.ChemistMod;
import chemistmod.actions.FlingAction;
import chemistmod.cards.BaseChemistCard;
import chemistmod.characters.TheChemist;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class Disposal extends BaseChemistCard {
    public static final String CARD_ID = ChemistMod.makeId("Disposal");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(CARD_ID);

    private static final int BASE_COST = 1;
    private static final int BASE_DAMAGE = 8;
    private static final int UPGRADE_DAMAGE = 3;

    public Disposal() {
        super(CARD_ID, CARD_STRINGS.NAME, ChemistMod.getCardImagePath(CARD_ID), BASE_COST, CARD_STRINGS.DESCRIPTION,
                CardType.ATTACK, TheChemist.Enums.CARD_GOLD, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = BASE_DAMAGE;
        this.requiresStockpile = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new DamageAction(monster, new DamageInfo(player, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        // This cast should be safe; we check in the base class
        TheChemist chemist = (TheChemist)player;
        if (chemist.stockpileCount() == 0) {
            // Nothing to fling
            // TODO - Is this thought bubble actually useful?
            AbstractDungeon.effectList.add(new ThoughtBubble(player.dialogX, player.dialogY, 3.0f,
                    TheChemist.getNoReagentsText(), true));
            return;
        }

        addToBot(new FlingAction());
        chemist.popReagent();
    }
}
