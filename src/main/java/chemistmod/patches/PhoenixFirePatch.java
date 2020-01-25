package chemistmod.patches;

import chemistmod.powers.PhoenixFirePower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

import java.util.ArrayList;

public class PhoenixFirePatch {

    @SpirePatch(clz = AbstractPlayer.class, method = "damage")
    public static class PhoenixFirePatch_Damage {

        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn Insert(AbstractPlayer __instance, DamageInfo info) {
            if (__instance.hasPower(PhoenixFirePower.POWER_ID)) {
                __instance.currentHealth = 0;
                __instance.heal(__instance.maxHealth * PhoenixFirePower.REVIVE_PERCENT / 100, true);
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(__instance, __instance, PhoenixFirePower.POWER_ID));
                return SpireReturn.Return(null);
            }

            return SpireReturn.Continue();
        }

        private static class Locator extends SpireInsertLocator {

            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasPotion");
                return LineFinder.findInOrder(ctBehavior, new ArrayList<>(), matcher);
            }
        }
    }
}
