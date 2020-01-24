package chemistmod.actions;

import chemistmod.ChemistMod;
import chemistmod.cards.skill.PhoenixDown;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;

public class PhoenixDownAction extends AbstractGameAction {
    private static final String ACTION_ID = ChemistMod.makeId("PhoenixDownAction");
    private static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ACTION_ID);

    private ArrayList<AbstractCard> phoenixDowns;

    public PhoenixDownAction(int amount) {
        this.phoenixDowns = new ArrayList<>();
        this.amount = amount;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        // This is all pretty much shameless stolen from the code for Exhume, with a couple of small tweaks
        AbstractPlayer player = AbstractDungeon.player;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (player.hand.size() == 10) {
                // Full hand; short circuit
                player.createHandIsFullDialog();
                this.isDone = true;
                return;
            }

            if (player.exhaustPile.isEmpty()) {
                // Empty exhaust pile; short circuit
                this.isDone = true;
                return;
            }

            for (AbstractCard card : player.exhaustPile.group) {
                card.stopGlowing();
                card.unhover();
                card.unfadeOut();
            }

            for (Iterator<AbstractCard> c = player.exhaustPile.group.iterator(); c.hasNext(); ) {
                AbstractCard card = c.next();
                if (card.cardID.equals(PhoenixDown.CARD_ID)) {
                    c.remove();
                    this.phoenixDowns.add(card);
                }
            }

            if (player.exhaustPile.isEmpty()) {
                // All the cards in the exhaust pile are Phoenix Downs; short circuit
                player.exhaustPile.group.addAll(this.phoenixDowns);
                this.phoenixDowns.clear();
                this.isDone = true;
                return;
            }

            String msg = UI_STRINGS.TEXT[0] + this.amount + (this.amount == 1 ? UI_STRINGS.TEXT[1] : UI_STRINGS.TEXT[2]);
            AbstractDungeon.gridSelectScreen.open(player.exhaustPile, this.amount, true, msg);
            tickDuration();
            return;
        }

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                player.hand.addToHand(c);
                if (AbstractDungeon.player.hasPower("Corruption") && c.type == AbstractCard.CardType.SKILL) {
                    c.setCostForTurn(-9);
                }

                player.exhaustPile.removeCard(c);
                c.unhover();
            }
        }

        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        player.hand.refreshHandLayout();
        player.exhaustPile.group.addAll(this.phoenixDowns);
        this.phoenixDowns.clear();
        for (AbstractCard c : player.exhaustPile.group) {
            c.unhover();
            c.target_x = CardGroup.DISCARD_PILE_X;
            c.target_y = 0.0F;
        }

        tickDuration();
    }
}
