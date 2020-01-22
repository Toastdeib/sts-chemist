package chemistmod.actions;

import chemistmod.powers.MarkedPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FlingAction extends AbstractGameAction {
    private static final int BASE_FLING_DAMAGE = 4;

    public FlingAction() {
    }

    @Override
    public void update() {
        // TODO - Account for modifiers to Fling damage (e.g. STR and the boss relic once I implement that)
        pickTarget();
        AbstractDungeon.actionManager.addToTop(new DamageAction(this.target,
                new DamageInfo(AbstractDungeon.player, BASE_FLING_DAMAGE, DamageInfo.DamageType.THORNS), AttackEffect.BLUNT_LIGHT));
        this.isDone = true;
    }

    private void pickTarget() {
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (monster.hasPower(MarkedPower.POWER_ID)) {
                this.target = monster;
                return;
            }
        }

        this.target = AbstractDungeon.getMonsters().getRandomMonster(true);
    }
}
