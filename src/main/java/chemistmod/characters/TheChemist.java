package chemistmod.characters;

import basemod.abstracts.CustomPlayer;
import chemistmod.ChemistMod;
import chemistmod.actions.FlingAction;
import chemistmod.cards.attack.ChemStrike;
import chemistmod.cards.attack.DragonFang;
import chemistmod.cards.skill.ChemDefend;
import chemistmod.cards.skill.DarkMatter;
import chemistmod.cards.skill.Mix;
import chemistmod.cards.skill.TurtleShell;
import chemistmod.reagents.ReagentEnum;
import chemistmod.relics.DragonsGift;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

public class TheChemist extends CustomPlayer {
    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass CHEMIST;
        @SpireEnum(name = "CHEMIST_GOLD")
        public static AbstractCard.CardColor CARD_GOLD;
        @SpireEnum(name = "CHEMIST_GOLD")
        public static CardLibrary.LibraryType LIBRARY_GOLD;
    }

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 70;
    public static final int MAX_HP = 70;
    public static final int MAX_ORBS = 0;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;

    public static final String CHARACTER_ID = ChemistMod.makeId("ChemistCharacter");
    private static final CharacterStrings CHARACTER_STRINGS = CardCrawlGame.languagePack.getCharacterString(CHARACTER_ID);

    private ArrayList<ReagentEnum> stockpile;
    public int baseStockpileCapacity;
    public int stockpileCapacity;

    public TheChemist(String name) {
        super(name, Enums.CHEMIST, null, null, (String) null, null);

        initializeClass(null, ChemistMod.CHARACTER_SHOULDER_2_PATH, ChemistMod.CHARACTER_SHOULDER_PATH,
                ChemistMod.CHARACTER_CORPSE_PATH, getLoadout(), 20.0f, -10.0f, 220.0f, 290.0f, new EnergyManager(ENERGY_PER_TURN));

        loadAnimation(ChemistMod.CHARACTER_ATLAS_PATH, ChemistMod.CHARACTER_JSON_PATH, 1.0f);
        AnimationState.TrackEntry entry = this.state.setAnimation(0, "Idle", true);
        entry.setTime(entry.getEndTime() * MathUtils.random());

        this.stockpile = new ArrayList<>();
        this.stockpileCapacity = this.baseStockpileCapacity = 3;
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> deck = new ArrayList<>();

        // Strikes
        deck.add(ChemStrike.CARD_ID);
        deck.add(ChemStrike.CARD_ID);

        // Defends
        deck.add(ChemDefend.CARD_ID);
        deck.add(ChemDefend.CARD_ID);

        // Chemist-specific cards
        deck.add(DragonFang.CARD_ID);
        deck.add(DragonFang.CARD_ID);
        deck.add(TurtleShell.CARD_ID);
        deck.add(TurtleShell.CARD_ID);
        deck.add(DarkMatter.CARD_ID);
        deck.add(Mix.CARD_ID);

        return deck;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> relics = new ArrayList<>();

        relics.add(DragonsGift.RELIC_ID);
        UnlockTracker.markRelicAsSeen(DragonsGift.RELIC_ID);

        return relics;
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(CHARACTER_STRINGS.NAMES[0], CHARACTER_STRINGS.TEXT[0], STARTING_HP, MAX_HP, MAX_ORBS,
                STARTING_GOLD, CARD_DRAW, this, getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return CHARACTER_STRINGS.NAMES[1];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.CARD_GOLD;
    }

    @Override
    public Color getCardRenderColor() {
        return ChemistMod.GOLD;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new ChemStrike();
    }

    @Override
    public Color getCardTrailColor() {
        return ChemistMod.GOLD;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        // TODO - Customize this?
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("POTION_2", 1.00f);
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "POTION_1";
    }

    @Override
    public String getLocalizedCharacterName() {
        return CHARACTER_STRINGS.NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheChemist(ChemistMod.MOD_NAME);
    }

    @Override
    public String getSpireHeartText() {
        return CHARACTER_STRINGS.TEXT[1];
    }

    @Override
    public Color getSlashAttackColor() {
        return ChemistMod.GOLD;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.SMASH,
                AbstractGameAction.AttackEffect.POISON,
                AbstractGameAction.AttackEffect.FIRE
        };
    }

    @Override
    public String getVampireText() {
        return CHARACTER_STRINGS.TEXT[2];
    }

    public void stockpileReagent(ReagentEnum reagent) {
        if (this.stockpile.size() == this.stockpileCapacity) {
            // Fling
            AbstractDungeon.actionManager.addToBottom(new FlingAction());
            this.stockpile.remove(0);
        }

        this.stockpile.add(reagent);
    }

    public int stockpileCount() {
        return this.stockpile.size();
    }

    public ReagentEnum popReagent() {
        // NOTE: This expects the calling code to have verified that stockpileCount() > 0 and will crash if it isn't.
        return this.stockpile.remove(0);
    }

    public void emptyStockpile() {
        this.stockpile.clear();
    }
}
