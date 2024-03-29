package chemistmod;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.interfaces.*;
import chemistmod.cards.attack.*;
import chemistmod.cards.power.*;
import chemistmod.cards.skill.*;
import chemistmod.characters.TheChemist;
import chemistmod.events.PotionSellerEvent;
import chemistmod.powers.StockpilePower;
import chemistmod.relics.DragonsGift;
import chemistmod.relics.ReinforcedFlask;
import chemistmod.util.Keyword;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.nio.charset.StandardCharsets;

@SpireInitializer
public class ChemistMod implements PostInitializeSubscriber, EditCardsSubscriber, EditRelicsSubscriber, EditCharactersSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, OnStartBattleSubscriber {

    public static final Color GOLD = CardHelper.getColor(255.0f, 199.0f, 0.0f);

    private static final String MOD_ID = "theChemist";
    public static final String MOD_NAME = "The Chemist";
    private static final String MOD_AUTHOR = "Toastdeib";
    private static final String MOD_DESCRIPTION = "Adds a new playable character based loosely on the Chemist job from Final Fantasy V.";

    private static final String BASE_IMAGE_PATH = MOD_ID + "Resources/images/";
    private static final String BASE_STRINGS_PATH = MOD_ID + "Resources/localization/eng/";

    private static final String ATTACK_CHEMIST_GOLD = BASE_IMAGE_PATH + "cardui/512/bg_attack_gold.png";
    private static final String SKILL_CHEMIST_GOLD = BASE_IMAGE_PATH + "cardui/512/bg_skill_gold.png";
    private static final String POWER_CHEMIST_GOLD = BASE_IMAGE_PATH + "cardui/512/bg_power_gold.png";
    private static final String ENERGY_ORB_CHEMIST_GOLD = BASE_IMAGE_PATH + "cardui/512/card_gold_orb.png";
    private static final String CARD_ENERGY_ORB = BASE_IMAGE_PATH + "cardui/512/card_gold_orb_small.png";

    private static final String ATTACK_CHEMIST_GOLD_PORTRAIT = BASE_IMAGE_PATH + "cardui/1024/bg_attack_gold.png";
    private static final String SKILL_CHEMIST_GOLD_PORTRAIT = BASE_IMAGE_PATH + "cardui/1024/bg_skill_gold.png";
    private static final String POWER_CHEMIST_GOLD_PORTRAIT = BASE_IMAGE_PATH + "cardui/1024/bg_power_gold.png";
    private static final String ENERGY_ORB_CHEMIST_GOLD_PORTRAIT = BASE_IMAGE_PATH + "cardui/1024/card_gold_orb.png";

    private static final String MOD_BADGE_PATH = BASE_IMAGE_PATH + "badge.png";
    private static final String SELECT_BUTTON_PATH = BASE_IMAGE_PATH + "ui/charSelect/chemistButton.png";
    private static final String PORTRAIT_PATH = BASE_IMAGE_PATH + "ui/charSelect/chemistPortrait.jpg";

    private static final String CARD_STRINGS_PATH = BASE_STRINGS_PATH + "CardStrings.json";
    private static final String POWER_STRINGS_PATH = BASE_STRINGS_PATH + "PowerStrings.json";
    private static final String ENUM_STRINGS_PATH = BASE_STRINGS_PATH + "EnumStrings.json";
    private static final String RELIC_STRINGS_PATH = BASE_STRINGS_PATH + "RelicStrings.json";
    private static final String EVENT_STRINGS_PATH = BASE_STRINGS_PATH + "EventStrings.json";
    private static final String CHARACTER_STRINGS_PATH = BASE_STRINGS_PATH + "CharacterStrings.json";
    private static final String UI_STRINGS_PATH = BASE_STRINGS_PATH + "UiStrings.json";
    private static final String KEYWORD_STRINGS_PATH = BASE_STRINGS_PATH + "KeywordStrings.json";

    public static final String CHARACTER_CORPSE_PATH = BASE_IMAGE_PATH + "character/corpse.png";
    public static final String CHARACTER_SHOULDER_PATH = BASE_IMAGE_PATH + "character/shoulder.png";
    public static final String CHARACTER_SHOULDER_2_PATH = BASE_IMAGE_PATH + "character/shoulder2.png";
    public static final String CHARACTER_ATLAS_PATH = BASE_IMAGE_PATH + "character/idle/skeleton.atlas";
    public static final String CHARACTER_JSON_PATH = BASE_IMAGE_PATH + "character/idle/skeleton.json";

    public ChemistMod() {
        BaseMod.subscribe(this);

        BaseMod.addColor(TheChemist.Enums.CARD_GOLD, GOLD, ATTACK_CHEMIST_GOLD, SKILL_CHEMIST_GOLD, POWER_CHEMIST_GOLD,
                ENERGY_ORB_CHEMIST_GOLD, ATTACK_CHEMIST_GOLD_PORTRAIT, SKILL_CHEMIST_GOLD_PORTRAIT,
                POWER_CHEMIST_GOLD_PORTRAIT, ENERGY_ORB_CHEMIST_GOLD_PORTRAIT, CARD_ENERGY_ORB);
    }

    public static void initialize() {
        new ChemistMod();
    }

    public static String makeId(String name) {
        return MOD_ID + ":" + name;
    }

    public static String getCardImagePath(String cardId) {
        return BASE_IMAGE_PATH + "cards/" + cardId.replace(MOD_ID + ":", "") + ".png";
    }

    public static String getPowerImagePath(String powerId, String size) {
        return BASE_IMAGE_PATH + "powers/" + powerId.replace(MOD_ID + ":", "") + "_" + size + ".png";
    }

    public static String getRelicImagePath(String relicId) {
        return BASE_IMAGE_PATH + "relics/" + relicId.replace(MOD_ID + ":", "") + ".png";
    }

    public static String getEventImagePath(String eventId) {
        return BASE_IMAGE_PATH + "events/" + eventId.replace(MOD_ID + ":", "") + ".jpg";
    }

    @Override
    public void receivePostInitialize() {
        ModPanel settings = new ModPanel();
        BaseMod.registerModBadge(new Texture(MOD_BADGE_PATH), MOD_NAME, MOD_AUTHOR, MOD_DESCRIPTION, settings);
        BaseMod.addEvent(PotionSellerEvent.EVENT_ID, PotionSellerEvent.class, TheCity.ID);
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TheChemist(MOD_NAME), SELECT_BUTTON_PATH, PORTRAIT_PATH, TheChemist.Enums.CHEMIST);
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addCard(new AirKnife());
        BaseMod.addCard(new Antidote());
        BaseMod.addCard(new BeltPouch());
        BaseMod.addCard(new BottomlessSack());
        BaseMod.addCard(new BrokenFlask());
        BaseMod.addCard(new CalledShot());
        BaseMod.addCard(new ChemDefend());
        BaseMod.addCard(new ChemStrike());
        BaseMod.addCard(new ChickenKnife());
        BaseMod.addCard(new ChitinousBash());
        BaseMod.addCard(new Dagger());
        BaseMod.addCard(new DancingDagger());
        BaseMod.addCard(new DarkMatter());
        BaseMod.addCard(new DefensivePosture());
        BaseMod.addCard(new Disposal());
        BaseMod.addCard(new DizzyingFumes());
        BaseMod.addCard(new DoubleDose());
        BaseMod.addCard(new DragonFang());
        BaseMod.addCard(new Efficiency());
        BaseMod.addCard(new Elixir());
        BaseMod.addCard(new EqualExchange());
        BaseMod.addCard(new ExpandedBags());
        BaseMod.addCard(new Eyedrops());
        BaseMod.addCard(new FlaskBash());
        BaseMod.addCard(new FromNothing());
        BaseMod.addCard(new GoliathTonic());
        BaseMod.addCard(new HastenedResearch());
        BaseMod.addCard(new HeroCocktail());
        BaseMod.addCard(new HiEther());
        BaseMod.addCard(new HiPotion());
        BaseMod.addCard(new Hoard());
        BaseMod.addCard(new HolyWater());
        BaseMod.addCard(new IronDraught());
        BaseMod.addCard(new MageMasher());
        BaseMod.addCard(new MaidensKiss());
        BaseMod.addCard(new MainGauche());
        BaseMod.addCard(new Mix());
        BaseMod.addCard(new MorningStar());
        BaseMod.addCard(new Nightshade());
        BaseMod.addCard(new Overstock());
        BaseMod.addCard(new Parry());
        //BaseMod.addCard(new Pharmacology()); // This needs to be properly implemented or maybe just fully revamped
        BaseMod.addCard(new PhoenixDown());
        BaseMod.addCard(new PowerDrink());
        BaseMod.addCard(new Prowess());
        BaseMod.addCard(new Prudence());
        BaseMod.addCard(new Recover());
        //BaseMod.addCard(new Reorganize()); // This also needs to be properly implemented, for now it's just scaffolded
        BaseMod.addCard(new Roar());
        BaseMod.addCard(new SageStaff());
        BaseMod.addCard(new Screen());
        BaseMod.addCard(new SealedBox());
        BaseMod.addCard(new SleightOfHand());
        BaseMod.addCard(new StudiousResearch());
        BaseMod.addCard(new TaintedBlade());
        BaseMod.addCard(new TakeStock());
        BaseMod.addCard(new Temerity());
        BaseMod.addCard(new TurtleShell());
        BaseMod.addCard(new VoidStrike());
        BaseMod.addCard(new VolatileConcoction());
        BaseMod.addCard(new WasteNot());
        BaseMod.addCard(new Withdraw());
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new DragonsGift(), TheChemist.Enums.CARD_GOLD);
        BaseMod.addRelicToCustomPool(new ReinforcedFlask(), TheChemist.Enums.CARD_GOLD);
    }

    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, CARD_STRINGS_PATH);
        BaseMod.loadCustomStringsFile(PowerStrings.class, POWER_STRINGS_PATH);
        BaseMod.loadCustomStringsFile(PowerStrings.class, ENUM_STRINGS_PATH); // Not technically powers but close enough to work
        BaseMod.loadCustomStringsFile(RelicStrings.class, RELIC_STRINGS_PATH);
        BaseMod.loadCustomStringsFile(EventStrings.class, EVENT_STRINGS_PATH);
        BaseMod.loadCustomStringsFile(CharacterStrings.class, CHARACTER_STRINGS_PATH);
        BaseMod.loadCustomStringsFile(UIStrings.class, UI_STRINGS_PATH);
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        Keyword[] keywords = gson.fromJson(Gdx.files.internal(KEYWORD_STRINGS_PATH).readString(String.valueOf(StandardCharsets.UTF_8)), Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        if (AbstractDungeon.player instanceof TheChemist) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new StockpilePower(AbstractDungeon.player)));
            TheChemist player = (TheChemist)AbstractDungeon.player;
            player.emptyStockpile();
            player.stockpileCapacity = player.baseStockpileCapacity;
        }
    }
}
