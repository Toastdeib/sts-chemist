package chemistmod.actions;

import chemistmod.characters.TheChemist;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FlingAction extends AbstractGameAction {

    private static final Logger log = LogManager.getLogger(FlingAction.class.getName());

    private static final int BASE_FLING_DAMAGE = 4;

    public FlingAction() {
    }

    @Override
    public void update() {
        // TODO:
        //  Extend stockpile/fling functionality outside of The Chemist
        //  Check for Marked debuff so it isn't a random target
        //  Account for modifiers to Fling damage (e.g. STR and the boss relic once I implement that)
        try {
            TheChemist player = (TheChemist) AbstractDungeon.player;
            this.target = AbstractDungeon.getMonsters().getRandomMonster(true);
            AbstractDungeon.actionManager.addToTop(new DamageAction(this.target,
                    new DamageInfo(player, BASE_FLING_DAMAGE, DamageInfo.DamageType.NORMAL), AttackEffect.BLUNT_LIGHT));
        } catch (ClassCastException ex) {
            log.warn("Unable to fling; current player isn't a Chemist");
        } finally {
            this.isDone = true;
        }
    }
}
