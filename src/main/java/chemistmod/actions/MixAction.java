package chemistmod.actions;

import chemistmod.characters.TheChemist;
import chemistmod.powers.FluidityPower;
import chemistmod.powers.ImbalancePower;
import chemistmod.powers.ProwessPower;
import chemistmod.powers.VolatilityPower;
import chemistmod.reagents.MixResultEnum;
import chemistmod.reagents.ReagentEnum;
import chemistmod.util.MixUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import java.util.ArrayList;

public class MixAction extends AbstractGameAction {
    private static final int DUD_DAMAGE = 3;
    private static final int LIGHT_DAMAGE = 5;
    private static final int HEAVY_DAMAGE = 10;

    private static final int LIGHT_BLOCK = 4;
    private static final int HEAVY_BLOCK = 8;
    private static final int POTION_BLOCK = 3;
    private static final int ELIXIR_BLOCK = 2;

    private static final int POTION_HEAL = 3;
    private static final int ELIXIR_HEAL = 2;

    private static final int ETHER_ENERGY = 3;
    private static final int ELIXIR_ENERGY = 1;

    private static final int LIGHT_DEBUFF_STACK = 1;
    private static final int HEAVY_DEBUFF_STACK = 3;
    private static final int STAT_BUFF_STACK = 1;
    private static final int THORNS_STACK = 2;
    private static final int PLATED_ARMOR_STACK = 4;
    private static final int DRAW_AMOUNT = 1;

    private ReagentEnum first;
    private ReagentEnum second;

    public MixAction(ReagentEnum first, ReagentEnum second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public void update() {
        MixResultEnum result = MixUtils.mix(this.first, this.second);
        AbstractPlayer player = AbstractDungeon.player;
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
                // TODO
                break;
            case PHOENIX_FIRE:
                // TODO
                break;
            case PANACEA:
                doClearDebuffs(player);
                break;
            case DRAGONS_FEAST:
                this.target = AbstractDungeon.getMonsters().getRandomMonster(true);
                doSingleTargetVampireDamage(player, LIGHT_DAMAGE);
                break;
            case DRAGONS_BITE:
                this.target = AbstractDungeon.getMonsters().getRandomMonster(true);
                doSingleTargetDamage(player, LIGHT_DAMAGE);
                doSingleTargetPower(player, new WeakPower(this.target, LIGHT_DEBUFF_STACK, false), LIGHT_DEBUFF_STACK);
                break;
            case DRAGONS_BREATH:
                doMultiTargetDamage(player, LIGHT_DAMAGE);
                break;
            case DRAGONS_CLAW:
                this.target = AbstractDungeon.getMonsters().getRandomMonster(true);
                doSingleTargetDamage(player, LIGHT_DAMAGE);
                doSingleTargetPower(player, new VulnerablePower(this.target, LIGHT_DEBUFF_STACK, false), LIGHT_DEBUFF_STACK);
                break;
            case DRAGONS_GLARE:
                this.target = AbstractDungeon.getMonsters().getRandomMonster(true);
                doSingleTargetDamage(player, LIGHT_DAMAGE);
                doDraw(player, DRAW_AMOUNT);
                break;
            case DRAGONS_SCALES:
                doGainBlock(player, LIGHT_BLOCK);
                this.target = AbstractDungeon.getMonsters().getRandomMonster(true);
                doSingleTargetDamage(player, LIGHT_DAMAGE);
                break;
            case DRAGONS_CURSE:
                this.target = AbstractDungeon.getMonsters().getRandomMonster(true);
                doSingleTargetDamage(player, LIGHT_DAMAGE);
                doSingleTargetPower(player, new ImbalancePower(this.target, LIGHT_DEBUFF_STACK), LIGHT_DEBUFF_STACK);
                break;
            case DRAGONS_RAGE:
                this.target = AbstractDungeon.getMonsters().getRandomMonster(true);
                doSingleTargetDamage(player, HEAVY_DAMAGE);
                break;
            case CARAPACE_COCKTAIL:
                doSingleTargetPower(player, player, new PlatedArmorPower(player, PLATED_ARMOR_STACK), PLATED_ARMOR_STACK);
                break;
            case OCEANS_FURY:
                doGainBlock(player, LIGHT_BLOCK);
                doSingleTargetPower(player, player, new StrengthPower(player, STAT_BUFF_STACK), STAT_BUFF_STACK);
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
            case CALM_OF_THE_DEPTHS:
                doGainBlock(player, LIGHT_BLOCK);
                doSingleTargetPower(player, player, new DexterityPower(player, STAT_BUFF_STACK), STAT_BUFF_STACK);
                break;
            case TBD_1:
                // TODO
                break;
            case HARDENING_SOLUTION:
                doGainBlock(player, HEAVY_BLOCK);
                break;
            case CETACEAN_WRATH:
                doGainBlock(player, LIGHT_BLOCK);
                doSingleTargetPower(player, player, new ThornsPower(player, THORNS_STACK), THORNS_STACK);
                break;
            case DEMONS_EMBRACE:
                this.target = AbstractDungeon.getMonsters().getRandomMonster(true);
                doSingleTargetPower(player, new WeakPower(this.target, HEAVY_DEBUFF_STACK, false), HEAVY_DEBUFF_STACK);
                break;
            case DEVILS_TOUCH:
                doMultiTargetPower(player, new ImbalancePower(player, LIGHT_DEBUFF_STACK), LIGHT_DEBUFF_STACK);
                break;
            case FIENDS_CARESS:
                this.target = AbstractDungeon.getMonsters().getRandomMonster(true);
                doSingleTargetPower(player, new VulnerablePower(this.target, HEAVY_DEBUFF_STACK, false), HEAVY_DEBUFF_STACK);
                break;
            case TBD_2:
                // TODO
                break;
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
        AbstractDungeon.actionManager.addToBottom(new DamageAction(target,
                new DamageInfo(source, modifyDamage(source, target, amount), TheChemist.Enums.MIX), AttackEffect.FIRE));
    }

    private void doSingleTargetVampireDamage(AbstractPlayer source, int amount) {
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
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, source, power, modifyPower(source, stackAmount)));
    }

    private void doMultiTargetPower(AbstractPlayer source, AbstractPower power, int stackAmount) {
        int modifiedAmount = modifyPower(source, stackAmount);
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            doSingleTargetPower(source, monster, power, modifiedAmount);
        }
    }

    private void doClearDebuffs(AbstractPlayer source) {
        AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(source));
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
