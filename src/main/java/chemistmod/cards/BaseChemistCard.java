package chemistmod.cards;

import basemod.abstracts.CustomCard;
import chemistmod.reagents.ReagentEnum;

public abstract class BaseChemistCard extends CustomCard {

    public BaseChemistCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    protected void stockpile(ReagentEnum reagent) {
        // TODO
    }
}
