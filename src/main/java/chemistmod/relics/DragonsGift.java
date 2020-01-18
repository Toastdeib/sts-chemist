package chemistmod.relics;

import basemod.abstracts.CustomRelic;
import chemistmod.ChemistMod;
import com.badlogic.gdx.graphics.Texture;

public class DragonsGift extends CustomRelic {

    public static final String RELIC_ID = ChemistMod.makeId("DragonsGift");

    public DragonsGift() {
        super(RELIC_ID, new Texture(ChemistMod.getRelicImagePath(RELIC_ID)), RelicTier.STARTER, LandingSound.FLAT);
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
    }

    @Override
    public void onEquip() {

    }
}
