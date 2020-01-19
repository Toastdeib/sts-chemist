package chemistmod.cards.skill;

import chemistmod.ChemistMod;
import chemistmod.actions.MixAction;
import chemistmod.actions.StockpileAction;
import chemistmod.cards.BaseChemistCard;
import chemistmod.characters.TheChemist;
import chemistmod.reagents.ReagentEnum;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WasteNot extends BaseChemistCard {
    public static final String CARD_ID = ChemistMod.makeId("WasteNot");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(CARD_ID);

    private static final int BASE_COST = 4;
    private static final int UPGRADE_COST = 3;

    public WasteNot() {
        super(CARD_ID, CARD_STRINGS.NAME, ChemistMod.getCardImagePath(CARD_ID), BASE_COST, CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, TheChemist.Enums.CARD_GOLD, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        // TODO - Support this outside of just the Chemist class
        if (!(player instanceof TheChemist)) {
            return;
        }

        TheChemist chemist = (TheChemist)player;
        if (chemist.stockpileCount() < 2) {
            // TODO - Thought bubble
            return;
        }

        ReagentEnum first = chemist.popReagent();
        ReagentEnum second = chemist.popReagent();
        addToBottom(new MixAction(first, second));
        addToBottom(new StockpileAction(first));
        addToBottom(new StockpileAction(second));
    }
}