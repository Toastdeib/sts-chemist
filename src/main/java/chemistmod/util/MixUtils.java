package chemistmod.util;

import chemistmod.reagents.MixResultEnum;
import chemistmod.reagents.ReagentEnum;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.HashMap;

public class MixUtils {

    private static HashMap<Integer, MixResultEnum> resultMap = new HashMap<>();
    private static final ReagentEnum[] REAGENTS = {
            ReagentEnum.HI_POTION,
            ReagentEnum.HI_ETHER,
            ReagentEnum.PHOENIX_DOWN,
            ReagentEnum.REMEDY,
            ReagentEnum.TURTLE_SHELL,
            ReagentEnum.DARK_MATTER,
            ReagentEnum.DRAGON_FANG
    };

    static {
        resultMap.put(hash(ReagentEnum.HI_POTION, ReagentEnum.HI_POTION), MixResultEnum.MEGA_POTION);
        resultMap.put(hash(ReagentEnum.HI_POTION, ReagentEnum.HI_ETHER), MixResultEnum.HALF_ELIXIR);
        resultMap.put(hash(ReagentEnum.HI_POTION, ReagentEnum.PHOENIX_DOWN), MixResultEnum.RESURRECTION);
        resultMap.put(hash(ReagentEnum.HI_POTION, ReagentEnum.TURTLE_SHELL), MixResultEnum.CARAPACE_COCKTAIL);
        resultMap.put(hash(ReagentEnum.HI_POTION, ReagentEnum.DRAGON_FANG), MixResultEnum.DRAGONS_FEAST);
        resultMap.put(hash(ReagentEnum.HI_ETHER, ReagentEnum.HI_ETHER), MixResultEnum.MEGA_ETHER);
        resultMap.put(hash(ReagentEnum.HI_ETHER, ReagentEnum.PHOENIX_DOWN), MixResultEnum.GALVANIZING_AGENT);
        resultMap.put(hash(ReagentEnum.PHOENIX_DOWN, ReagentEnum.PHOENIX_DOWN), MixResultEnum.PHOENIX_FIRE);
        resultMap.put(hash(ReagentEnum.REMEDY, ReagentEnum.REMEDY), MixResultEnum.PANACEA);
        resultMap.put(hash(ReagentEnum.REMEDY, ReagentEnum.TURTLE_SHELL), MixResultEnum.STORMS_EYE);
        resultMap.put(hash(ReagentEnum.REMEDY, ReagentEnum.DARK_MATTER), MixResultEnum.CURSED_GAZE);
        resultMap.put(hash(ReagentEnum.REMEDY, ReagentEnum.DRAGON_FANG), MixResultEnum.DRAGONS_BREATH);
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

    public static ReagentEnum getRandomReagent() {
        return REAGENTS[AbstractDungeon.miscRng.random(REAGENTS.length - 1)];
    }

    private static int hash(ReagentEnum first, ReagentEnum second) {
        return first.hashCode() + second.hashCode();
    }
}
