package chemistmod.actions;

import chemistmod.cards.skill.PhoenixDown;
import chemistmod.characters.TheChemist;
import chemistmod.powers.*;
import chemistmod.reagents.MixResultEnum;
import chemistmod.reagents.ReagentEnum;
import chemistmod.util.MixUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import java.util.ArrayList;

public class MixAction extends AbstractGameAction {
    private static final int DUD_DAMAGE = 3;
    private static final int LIGHT_DAMAGE = 6;
    private static final int HEAVY_DAMAGE = 12;

    private static final int LIGHT_BLOCK = 5;
    private static final int HEAVY_BLOCK = 10;
    private static final int POTION_BLOCK = 3;
    private static final int ELIXIR_BLOCK = 2;

    private static final int POTION_HEAL = 3;
    private static final int ELIXIR_HEAL = 2;

    private static final int ETHER_ENERGY = 3;
    private static final int ELIXIR_ENERGY = 1;

    private static final int IMBALANCE_STACK = 2;
    private static final int STRENGTH_LOSS_STACK = 6;
    private static final int ENERGY_STACK = 1;
    private static final int ARTIFACT_STACK = 1;
    private static final int THORNS_STACK = 2;
    private static final int PLATED_ARMOR_STACK = 3;

    private ReagentEnum first;
    private ReagentEnum second;

    public MixAction(ReagentEnum first, ReagentEnum second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public void update() {
        AbstractPlayer player = AbstractDungeon.player;
        MixResultEnum result = MixUtils.mix(this.first, this.second);
        switch (result) {
            case DUD:
                // lol
                doMultiTargetDamage(player, DUD_DAMAGE);
                doSingleTargetDamage(player, player, DUD_DAMAGE);
                break;
            case MEGA_POTION:
                doGainBlock(player, POTION_BLOCK);
                doHeal(player, POTION_HEAL);
                break;
            case MEGA_ETHER:
                doGainEnergy(player, ETHER_ENERGY);
                break;
            case HALF_ELIXIR:
                doGainBlock(player, ELIXIR_BLOCK);
                doHeal(player, ELIXIR_HEAL);
                doGainEnergy(player, ELIXIR_ENERGY);
                break;
            case RESURRECTION:
                ArrayList<AbstractCard> cardsToRes = new ArrayList<>();
                for (AbstractCard card : player.exhaustPile.group) {
                    card.stopGlowing();
                    card.unhover();
                    card.unfadeOut();
                    if (card.type != AbstractCard.CardType.CURSE && card.type != AbstractCard.CardType.STATUS && !card.cardID.equals(PhoenixDown.CARD_ID)) {
                        // Only resurrect playable, non-Phoenix Down cards
                        cardsToRes.add(card);
                    }
                }

                for (AbstractCard card : cardsToRes) {
                    player.hand.addToHand(card);
                    if (AbstractDungeon.player.hasPower("Corruption") && card.type == AbstractCard.CardType.SKILL) {
                        card.setCostForTurn(-9);
                    }

                    player.exhaustPile.removeCard(card);
                    card.unhover();
                }

                player.hand.refreshHandLayout();
                for (AbstractCard c : player.exhaustPile.group) {
                    c.unhover();
                    c.target_x = CardGroup.DISCARD_PILE_X;
                    c.target_y = 0.0F;
                }
                break;
            case GALVANIZING_AGENT:
                doSingleTargetPower(player, player, new GalvanizedPower(player, ENERGY_STACK), ENERGY_STACK);
                break;
            case PHOENIX_FIRE:
                if (!player.hasPower(PhoenixFirePower.POWER_ID)) {
                    doSingleTargetPower(player, player, new PhoenixFirePower(player), 0);
                }
                break;
            case PANACEA:
                doSingleTargetPower(player, player, new ArtifactPower(player, ARTIFACT_STACK), ARTIFACT_STACK);
                break;
            case DRAGONS_FEAST:
                this.target = AbstractDungeon.getMonsters().getRandomMonster(true);
                doSingleTargetVampireDamage(player, LIGHT_DAMAGE);
                break;
            case DRAGONS_BREATH:
                doMultiTargetDamage(player, LIGHT_DAMAGE);
                break;
            case DRAGONS_SCALES:
                doGainBlock(player, LIGHT_BLOCK);
                this.target = AbstractDungeon.getMonsters().getRandomMonster(true);
                doSingleTargetDamage(player, LIGHT_DAMAGE);
                break;
            case DRAGONS_CURSE:
                this.target = AbstractDungeon.getMonsters().getRandomMonster(true);
                doSingleTargetDamage(player, LIGHT_DAMAGE);
                doSingleTargetPower(player, new ImbalancePower(this.target, IMBALANCE_STACK), IMBALANCE_STACK);
                break;
            case DRAGONS_RAGE:
                this.target = AbstractDungeon.getMonsters().getRandomMonster(true);
                doSingleTargetDamage(player, HEAVY_DAMAGE);
                break;
            case CARAPACE_COCKTAIL:
                doSingleTargetPower(player, player, new PlatedArmorPower(player, PLATED_ARMOR_STACK), PLATED_ARMOR_STACK);
                break;
            case STORMS_EYE:
                int enemies = 0;
                for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                    if (!monster.halfDead && !monster.isDead && !monster.isEscaping) {
                        enemies++;
                    }
                }

                doGainBlock(player, LIGHT_BLOCK * enemies);
                break;
            case HARDENING_SOLUTION:
                doGainBlock(player, HEAVY_BLOCK);
                break;
            case CETACEAN_WRATH:
                doGainBlock(player, LIGHT_BLOCK);
                doSingleTargetPower(player, player, new ThornsPower(player, THORNS_STACK), THORNS_STACK);
                break;
            case CURSED_GAZE:
                this.target = AbstractDungeon.getMonsters().getRandomMonster(true);
                doSingleTargetPower(player, new StrengthPower(this.target, -STRENGTH_LOSS_STACK), -STRENGTH_LOSS_STACK);
                if (!this.target.hasPower(ArtifactPower.POWER_ID)) {
                    doSingleTargetPower(player, new GainStrengthPower(this.target, STRENGTH_LOSS_STACK), STRENGTH_LOSS_STACK);
                }
                break;
        }

        AbstractPower efficiency = player.getPower(EfficiencyPower.POWER_ID);
        if (efficiency != null) {
            doDraw(player, efficiency.amount);
        }

        this.isDone = true;
    }

    private int modifyDamage(AbstractPlayer source, AbstractCreature target, int amount) {
        AbstractPower volatility = source.getPower(VolatilityPower.POWER_ID);
        if (volatility != null) {
            amount += volatility.amount;
        }

        return modifyAmount(source, target, amount);
    }

    private int modifyBlock(AbstractPlayer source, int amount) {
        AbstractPower fluidity = source.getPower(FluidityPower.POWER_ID);
        if (fluidity != null) {
            amount += fluidity.amount;
        }

        return modifyAmount(source, null, amount);
    }

    private int modifyPower(AbstractPlayer source, int amount) {
        return modifyAmount(source, null, amount);
    }

    private int modifyAmount(AbstractPlayer source, AbstractCreature target, int amount) {
        float finalAmount = amount;

        if (source.hasPower(ProwessPower.POWER_ID)) {
            finalAmount *= ProwessPower.MODIFIER;
        }

        if (target != null && target.hasPower(ImbalancePower.POWER_ID)) {
            finalAmount *= ImbalancePower.getModifier();
        }

        return (int)finalAmount;
    }

    private void doSingleTargetDamage(AbstractPlayer source, int amount) {
        doSingleTargetDamage(source, this.target, amount);
    }

    private void doSingleTargetDamage(AbstractPlayer source, AbstractCreature target, int amount) {
        if (target == null) {
            return;
        }

        AbstractDungeon.actionManager.addToBottom(new DamageAction(target,
                new DamageInfo(source, modifyDamage(source, target, amount), TheChemist.Enums.MIX), AttackEffect.FIRE));
    }

    private void doSingleTargetVampireDamage(AbstractPlayer source, int amount) {
        if (this.target == null) {
            return;
        }

        AbstractDungeon.actionManager.addToBottom(new VampireDamageAction(this.target,
                new DamageInfo(source, modifyDamage(source, this.target, amount), TheChemist.Enums.MIX), AttackEffect.FIRE));
    }

    private void doMultiTargetDamage(AbstractPlayer source, int amount) {
        ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
        int[] amounts = new int[monsters.size()];
        for (int i = 0; i < amounts.length; i++) {
            amounts[i] = modifyDamage(source, monsters.get(i), amount);
        }

        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(source, amounts, TheChemist.Enums.MIX,
                AttackEffect.FIRE));
    }

    private void doGainBlock(AbstractPlayer source, int amount) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(source, source, modifyBlock(source, amount)));
    }

    private void doSingleTargetPower(AbstractPlayer source, AbstractPower power, int stackAmount) {
        doSingleTargetPower(source, this.target, power, modifyPower(source, stackAmount));
    }

    private void doSingleTargetPower(AbstractPlayer source, AbstractCreature target, AbstractPower power, int stackAmount) {
        if (target == null) {
            return;
        }

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, source, power, modifyPower(source, stackAmount)));
    }

    private void doHeal(AbstractPlayer source, int amount) {
        AbstractDungeon.actionManager.addToBottom(new HealAction(source, source, modifyPower(source, amount)));
    }

    private void doGainEnergy(AbstractPlayer source, int amount) {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(modifyPower(source, amount)));
    }

    private void doDraw(AbstractPlayer source, int amount) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(source, modifyPower(source, amount), false));
    }
}
