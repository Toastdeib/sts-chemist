package chemistmod.relics;

import basemod.abstracts.CustomRelic;
import chemistmod.ChemistMod;
import com.badlogic.gdx.graphics.Texture;

public class ReinforcedFlask extends CustomRelic {
    public static final String RELIC_ID = ChemistMod.makeId("ReinforcedFlask");

    public ReinforcedFlask() {
        super(RELIC_ID, new Texture(ChemistMod.getRelicImagePath(RELIC_ID)), RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
