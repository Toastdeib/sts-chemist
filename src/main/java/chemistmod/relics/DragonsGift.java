package chemistmod.relics;

import basemod.abstracts.CustomRelic;
import chemistmod.ChemistMod;
import chemistmod.actions.StockpileAction;
import chemistmod.reagents.ReagentEnum;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DragonsGift extends CustomRelic {

    public static final String RELIC_ID = ChemistMod.makeId("DragonsGift");

    public DragonsGift() {
        super(RELIC_ID, new Texture(ChemistMod.getRelicImagePath(RELIC_ID)), RelicTier.STARTER, LandingSound.FLAT);
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new StockpileAction(ReagentEnum.DRAGON_FANG));
    }

    @Override
    public void onEquip() {

    }
}
