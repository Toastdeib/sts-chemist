package chemistmod.relics;

import basemod.abstracts.CustomRelic;
import chemistmod.ChemistMod;
import com.badlogic.gdx.graphics.Texture;

public class TaintedFlask extends CustomRelic {
    public static final String RELIC_ID = ChemistMod.makeId("TaintedFlask");

    public TaintedFlask() {
        super(RELIC_ID, new Texture(ChemistMod.getRelicImagePath(RELIC_ID)), RelicTier.UNCOMMON, LandingSound.CLINK);
    }
}
