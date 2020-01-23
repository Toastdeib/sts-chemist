package chemistmod.util;

import chemistmod.reagents.MixResultEnum;
import chemistmod.reagents.ReagentEnum;

import java.util.HashMap;

public class MixUtils {

    private static HashMap<Integer, MixResultEnum> resultMap = new HashMap<>();

    static {
        resultMap.put(hash(ReagentEnum.HI_POTION, ReagentEnum.HI_POTION), MixResultEnum.MEGA_POTION);
        resultMap.put(hash(ReagentEnum.HI_POTION, ReagentEnum.HI_ETHER), MixResultEnum.HALF_ELIXIR);
        resultMap.put(hash(ReagentEnum.HI_POTION, ReagentEnum.PHOENIX_DOWN), MixResultEnum.RESURRECTION);
        resultMap.put(hash(ReagentEnum.HI_POTION, ReagentEnum.TURTLE_SHELL), MixResultEnum.CARAPACE_COCKTAIL);
        resultMap.put(hash(ReagentEnum.HI_POTION, ReagentEnum.DRAGON_FANG), MixResultEnum.DRAGONS_FEAST);
        resultMap.put(hash(ReagentEnum.HI_ETHER, ReagentEnum.HI_ETHER), MixResultEnum.MEGA_ETHER);
        resultMap.put(hash(ReagentEnum.HI_ETHER, ReagentEnum.PHOENIX_DOWN), MixResultEnum.RESURRECTION);
        resultMap.put(hash(ReagentEnum.PHOENIX_DOWN, ReagentEnum.PHOENIX_DOWN), MixResultEnum.PHOENIX_FIRE);
        resultMap.put(hash(ReagentEnum.MAIDENS_KISS, ReagentEnum.HOLY_WATER), MixResultEnum.PANACEA);
        resultMap.put(hash(ReagentEnum.MAIDENS_KISS, ReagentEnum.ANTIDOTE), MixResultEnum.PANACEA);
        resultMap.put(hash(ReagentEnum.MAIDENS_KISS, ReagentEnum.EYEDROP), MixResultEnum.PANACEA);
        resultMap.put(hash(ReagentEnum.MAIDENS_KISS, ReagentEnum.TURTLE_SHELL), MixResultEnum.OCEANS_FURY);
        resultMap.put(hash(ReagentEnum.MAIDENS_KISS, ReagentEnum.DARK_MATTER), MixResultEnum.DEMONS_EMBRACE);
        resultMap.put(hash(ReagentEnum.MAIDENS_KISS, ReagentEnum.DRAGON_FANG), MixResultEnum.DRAGONS_BITE);
        resultMap.put(hash(ReagentEnum.HOLY_WATER, ReagentEnum.ANTIDOTE), MixResultEnum.PANACEA);
        resultMap.put(hash(ReagentEnum.HOLY_WATER, ReagentEnum.EYEDROP), MixResultEnum.PANACEA);
        resultMap.put(hash(ReagentEnum.HOLY_WATER, ReagentEnum.TURTLE_SHELL), MixResultEnum.STORMS_EYE);
        resultMap.put(hash(ReagentEnum.HOLY_WATER, ReagentEnum.DARK_MATTER), MixResultEnum.DEVILS_TOUCH);
        resultMap.put(hash(ReagentEnum.HOLY_WATER, ReagentEnum.DRAGON_FANG), MixResultEnum.DRAGONS_BREATH);
        resultMap.put(hash(ReagentEnum.ANTIDOTE, ReagentEnum.EYEDROP), MixResultEnum.PANACEA);
        resultMap.put(hash(ReagentEnum.ANTIDOTE, ReagentEnum.TURTLE_SHELL), MixResultEnum.CALM_OF_THE_DEPTHS);
        resultMap.put(hash(ReagentEnum.ANTIDOTE, ReagentEnum.DARK_MATTER), MixResultEnum.FIENDS_CARESS);
        resultMap.put(hash(ReagentEnum.ANTIDOTE, ReagentEnum.DRAGON_FANG), MixResultEnum.DRAGONS_CLAW);
        resultMap.put(hash(ReagentEnum.EYEDROP, ReagentEnum.TURTLE_SHELL), MixResultEnum.ABYSSAL_CLARITY);
        resultMap.put(hash(ReagentEnum.EYEDROP, ReagentEnum.DARK_MATTER), MixResultEnum.CURSED_GAZE);
        resultMap.put(hash(ReagentEnum.EYEDROP, ReagentEnum.DRAGON_FANG), MixResultEnum.DRAGONS_GLARE);
        resultMap.put(hash(ReagentEnum.TURTLE_SHELL, ReagentEnum.TURTLE_SHELL), MixResultEnum.HARDENING_SOLUTION);
        resultMap.put(hash(ReagentEnum.TURTLE_SHELL, ReagentEnum.DARK_MATTER), MixResultEnum.CETACEAN_WRATH);
        resultMap.put(hash(ReagentEnum.TURTLE_SHELL, ReagentEnum.DRAGON_FANG), MixResultEnum.DRAGONS_SCALES);
        resultMap.put(hash(ReagentEnum.DARK_MATTER, ReagentEnum.DRAGON_FANG), MixResultEnum.DRAGONS_CURSE);
        resultMap.put(hash(ReagentEnum.DRAGON_FANG, ReagentEnum.DRAGON_FANG), MixResultEnum.DRAGONS_RAGE);
    }

    private MixUtils() {

    }

    public static MixResultEnum mix(ReagentEnum first, ReagentEnum second) {
        int hash = hash(first, second);
        if (resultMap.containsKey(hash)) {
            return resultMap.get(hash);
        }

        return MixResultEnum.DUD;
    }

    private static int hash(ReagentEnum first, ReagentEnum second) {
        return first.hashCode() + second.hashCode();
    }
}
