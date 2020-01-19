package chemistmod.actions;

import chemistmod.reagents.MixResultEnum;
import chemistmod.reagents.ReagentEnum;
import chemistmod.util.MixUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

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
                for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(monster,
                            new DamageInfo(player, DUD_DAMAGE, DamageInfo.DamageType.NORMAL),
                            AbstractGameAction.AttackEffect.FIRE));
                }

                AbstractDungeon.actionManager.addToBottom(new DamageAction(player,
                        new DamageInfo(player, DUD_DAMAGE, DamageInfo.DamageType.NORMAL),
                        AbstractGameAction.AttackEffect.FIRE));
                break;
            case MEGA_POTION:
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, POTION_BLOCK));
                AbstractDungeon.actionManager.addToBottom(new HealAction(player, player, POTION_HEAL));
                break;
            case MEGA_ETHER:
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(ETHER_ENERGY));
                break;
            case HALF_ELIXIR:
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, ELIXIR_BLOCK));
                AbstractDungeon.actionManager.addToBottom(new HealAction(player, player, ELIXIR_HEAL));
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(ELIXIR_ENERGY));
                break;
            case RESURRECTION:
                // TODO
                break;
            case PHOENIX_FIRE:
                // TODO
                break;
            case PANACEA:
                AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(player));
                break;
            case DRAGONS_FEAST:
                break;
            case DRAGONS_BITE:
                this.target = AbstractDungeon.getMonsters().getRandomMonster(true);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(this.target,
                        new DamageInfo(player, LIGHT_DAMAGE, DamageInfo.DamageType.NORMAL),
                        AbstractGameAction.AttackEffect.FIRE));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, player,
                        new WeakPower(this.target, LIGHT_DEBUFF_STACK, false), LIGHT_DEBUFF_STACK));
                break;
            case DRAGONS_BREATH:
                for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(monster,
                            new DamageInfo(player, LIGHT_DAMAGE, DamageInfo.DamageType.NORMAL),
                            AbstractGameAction.AttackEffect.FIRE));
                }
                break;
            case DRAGONS_CLAW:
                this.target = AbstractDungeon.getMonsters().getRandomMonster(true);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(this.target,
                        new DamageInfo(player, LIGHT_DAMAGE, DamageInfo.DamageType.NORMAL),
                        AbstractGameAction.AttackEffect.FIRE));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, player,
                        new VulnerablePower(this.target, LIGHT_DEBUFF_STACK, false), LIGHT_DEBUFF_STACK));
                break;
            case DRAGONS_GLARE:
                this.target = AbstractDungeon.getMonsters().getRandomMonster(true);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(this.target,
                        new DamageInfo(player, LIGHT_DAMAGE, DamageInfo.DamageType.NORMAL),
                        AbstractGameAction.AttackEffect.FIRE));
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(player, DRAW_AMOUNT, false));
                break;
            case DRAGONS_SCALES:
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, LIGHT_BLOCK));
                this.target = AbstractDungeon.getMonsters().getRandomMonster(true);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(this.target,
                        new DamageInfo(player, LIGHT_DAMAGE, DamageInfo.DamageType.NORMAL),
                        AbstractGameAction.AttackEffect.FIRE));
                break;
            case DRAGONS_CURSE:
                // TODO
                break;
            case DRAGONS_RAGE:
                this.target = AbstractDungeon.getMonsters().getRandomMonster(true);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(this.target,
                        new DamageInfo(player, HEAVY_DAMAGE, DamageInfo.DamageType.NORMAL),
                        AbstractGameAction.AttackEffect.FIRE));
                break;
            case CARAPACE_COCKTAIL:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player,
                        new PlatedArmorPower(player, PLATED_ARMOR_STACK), PLATED_ARMOR_STACK));
                break;
            case OCEANS_FURY:
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, LIGHT_BLOCK));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player,
                        new StrengthPower(player, STAT_BUFF_STACK), STAT_BUFF_STACK));
                break;
            case STORMS_EYE:
                int enemies = 0;
                for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                    if (!monster.halfDead && !monster.isDead && !monster.isEscaping) {
                        enemies++;
                    }
                }

                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, LIGHT_BLOCK * enemies));
                break;
            case CALM_OF_THE_DEPTHS:
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, LIGHT_BLOCK));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player,
                        new DexterityPower(player, STAT_BUFF_STACK), STAT_BUFF_STACK));
                break;
            case TBD_1:
                break;
            case HARDENING_SOLUTION:
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, HEAVY_BLOCK));
                break;
            case CETACEAN_WRATH:
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, LIGHT_BLOCK));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player,
                        new ThornsPower(player, THORNS_STACK), THORNS_STACK));
                break;
            case DEMONS_EMBRACE:
                this.target = AbstractDungeon.getMonsters().getRandomMonster(true);
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, player,
                        new WeakPower(this.target, HEAVY_DEBUFF_STACK, false), HEAVY_DEBUFF_STACK));
                break;
            case DEVILS_TOUCH:
                for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player,
                            new WeakPower(this.target, LIGHT_DEBUFF_STACK, false), LIGHT_DEBUFF_STACK));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player,
                            new VulnerablePower(this.target, LIGHT_DEBUFF_STACK, false), LIGHT_DEBUFF_STACK));
                }
                break;
            case FIENDS_CARESS:
                this.target = AbstractDungeon.getMonsters().getRandomMonster(true);
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, player,
                        new VulnerablePower(this.target, HEAVY_DEBUFF_STACK, false), HEAVY_DEBUFF_STACK));
                break;
            case TBD_2:
                break;
        }

        this.isDone = true;
    }
}
