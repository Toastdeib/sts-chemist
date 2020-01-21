package chemistmod.cards.attack;

import chemistmod.ChemistMod;
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
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class ChickenKnife extends BaseChemistCard {
    public static final String CARD_ID = ChemistMod.makeId("ChickenKnife");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(CARD_ID);

    private static final int BASE_COST = 2;
    private static final int BASE_DAMAGE = 15;
    private static final int UPGRADE_DAMAGE = 5;

    public ChickenKnife() {
        super(CARD_ID, CARD_STRINGS.NAME, ChemistMod.getCardImagePath(CARD_ID), BASE_COST, CARD_STRINGS.DESCRIPTION,
                CardType.ATTACK, TheChemist.Enums.CARD_GOLD, CardRarity.RARE, CardTarget.ENEMY);
        this.damage = this.baseDamage = BASE_DAMAGE;
        this.exhaust = true;
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
        if (player.currentHealth < (player.maxHealth / 10) && canEscape()) {
            // Below 10% HP in an escapable battle, flee instead of attacking
            escape(player);
            return;
        }

        addToBot(new DamageAction(monster, new DamageInfo(player, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    // The following two methods are shamelessly stolen and slightly modified from the Smoke Bomb potion code
    private boolean canEscape() {
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (monster.hasPower("BackAttack") || monster.type == AbstractMonster.EnemyType.BOSS) {
                return false;
            }
        }

        return true;
    }

    private void escape(AbstractPlayer player) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.getCurrRoom().smoked = true;
            player.hideHealthBar();
            player.isEscaping = true;
            player.flipHorizontal = !AbstractDungeon.player.flipHorizontal;
            AbstractDungeon.overlayMenu.endTurnButton.disable();
            player.escapeTimer = 2.5F;
        }
    }
}
