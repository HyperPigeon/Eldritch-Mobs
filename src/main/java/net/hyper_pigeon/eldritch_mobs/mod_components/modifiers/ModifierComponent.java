package net.hyper_pigeon.eldritch_mobs.mod_components.modifiers;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.hyper_pigeon.eldritch_mobs.mod_components.interfaces.ModifierInterface;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.CompoundTag;
import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class ModifierComponent implements ModifierInterface {

    private boolean is_elite;
    private boolean is_ultra;
    private boolean is_eldritch;
    private boolean rank_decided;
    private int mod_number = 0;
//    private AlchemyComponent alchemy = new AlchemyComponent();
//    private BeserkComponent beserk = new BeserkComponent();
//    private BlindingComponent blinding = new BlindingComponent();
//    private BurningComponent burning = sanew BurningComponent();
//    private CloakedComponent cloaked = new CloakedComponent();
//    private DrainingComponent draining = new DrainingComponent();
//    private DrowningComponent drowning = new DrowningComponent();
//    private GhastlyComponent ghastly = new GhastlyComponent();
//    private GravityComponent gravity = new GravityComponent();
//    private LethargicComponent lethargic = new LethargicComponent();
//    private One_UpComponent one_up = new One_UpComponent();
//    private RegeneratingComponent regen = new RegeneratingComponent();
//    private ResistantComponent resistant = new ResistantComponent();
//    private SnatcherComponent snatcher = new SnatcherComponent();
//    private SpeedsterComponent speedster = new SpeedsterComponent();
//    private SprintingComponent sprinting = new SprintingComponent();
//    private StarvingComponent starving = new StarvingComponent();
//    private StormyComponent stormy = new StormyComponent();
//    private WeaknessComponent weakness = new WeaknessComponent();
//    private WebslingingComponent webslinging = new WebslingingComponent();


    public static ArrayList<String> all_mods = new ArrayList<>(Arrays.asList("alchemist", "berserk", "yeeter", "blinding", "burning",
            "cloaked","deflector","draining","drowning","ender","ghastly", "gravity","lethargic","lifesteal","one_up","regen",
            "resistant","rust","snatcher","speedster","sprinter","starving","stormy","thorny","toxic","weakness","webslinging",
            "withering", "sniper", "duplicator"));

    private ArrayList<String> mods = new ArrayList<>(Arrays.asList("alchemist", "berserk", "yeeter", "blinding", "burning",
            "cloaked","deflector","draining","drowning","ender","ghastly", "gravity","lethargic","lifesteal","one_up","regen",
            "resistant","rust","snatcher","speedster","sprinter","starving","stormy","thorny","toxic","weakness","webslinging",
            "withering","duplicator"));

    private ArrayList<String> ranged_mobs_mods = new ArrayList<>(Arrays.asList("alchemist", "blinding",
            "cloaked","deflector","draining","drowning","ender","ghastly", "gravity","lethargic","lifesteal","one_up","regen",
            "resistant", "snatcher","speedster","sprinter","starving","stormy","thorny","toxic","weakness","webslinging",
            "withering","sniper", "duplicator"));

    public ArrayList<String> creeper_mods = new ArrayList<>(Arrays.asList("alchemist", "blinding",
            "cloaked","deflector","draining","drowning","ender","ghastly", "gravity","lethargic","lifesteal","one_up","regen",
            "resistant", "snatcher","speedster","sprinter","starving","stormy","thorny","toxic","weakness","webslinging",
            "withering", "duplicator"));

    private ArrayList<String> alt_activated_mods = new ArrayList<>(Arrays.asList("yeeter", "deflector","rust","ender","lifesteal","one_up","thorny","toxic",
            "withering","sniper"));

    private HashMap<String, ModifierInterface> mods_hashmap = new HashMap<>();
    {
        mods_hashmap.put("alchemist", new AlchemyComponent());
        mods_hashmap.put("berserk", new BeserkComponent());
        mods_hashmap.put("blinding", new BlindingComponent());
        mods_hashmap.put("burning", new BurningComponent());
        mods_hashmap.put("cloaked",new CloakedComponent());
        mods_hashmap.put("draining",new DrainingComponent());
        mods_hashmap.put("drowning",new DrowningComponent());
        mods_hashmap.put("ghastly",new GhastlyComponent());
        mods_hashmap.put("gravity",new GravityComponent());
        mods_hashmap.put("lethargic",new LethargicComponent());
        mods_hashmap.put("one_up",new One_UpComponent());
        mods_hashmap.put("regen",new RegeneratingComponent());
        mods_hashmap.put("resistant",new ResistantComponent());
        mods_hashmap.put("snatcher",new SnatcherComponent());
        mods_hashmap.put("speedster",new SpeedsterComponent());
        mods_hashmap.put("sprinter",new SprintingComponent());
        mods_hashmap.put("starving",new StarvingComponent());
        mods_hashmap.put("stormy",new StormyComponent());
        mods_hashmap.put("weakness",new WeaknessComponent());
        mods_hashmap.put("webslinging",new WebslingingComponent());
        mods_hashmap.put("duplicator", new DuplicatorComponent());
    }

    public ArrayList<String> modifier_list = new ArrayList<>();

    public LivingEntity mob;

    EldritchMobsConfig config = AutoConfig.getConfigHolder(EldritchMobsConfig.class).getConfig();

    private double EliteSpawnRate = config.EliteSpawnRates;

    private double UltraSpawnRate = config.UltraSpawnRates;

    private double EldritchSpawnRate = config.EldritchSpawnRates;


    public ModifierComponent(LivingEntity entity) {
        if((!config.ignoreNamed || !entity.hasCustomName()) &&
                (entity.getType().isIn(EldritchMobsMod.ELDRITCH_ALLOWED)) && !(entity.getType().isIn(EldritchMobsMod.ELDRITCH_BLACKLIST))) {

            config.removeMods();
            ranged_mobs_mods.retainAll(all_mods);
            creeper_mods.retainAll(all_mods);
            mods.retainAll(all_mods);
            if((entity instanceof WitherEntity || entity instanceof EnderDragonEntity) && config.toggleEldritchBossMobs) {
                this.setRank();
                this.setMods();
                mob = entity;
            }
            else if(!(entity instanceof WitherEntity || entity instanceof EnderDragonEntity)) {
                this.setRank();
                this.setMods();
                mob = entity;
            }

        }
    }

    public void setRank_decided(boolean bool){
        rank_decided = bool;
    }

    public void setIs_elite(boolean bool){
        is_elite = bool;
        mod_number = config.EliteModTotal;
    }

    public void setIs_ultra(boolean bool){
        is_ultra = bool;
        mod_number = config.UltraModTotal;
    }

    public void setIs_eldritch(boolean bool){
        is_eldritch = bool;
        mod_number = config.EldritchModTotal;
    }

    public void setRank() {
        if(!rank_decided) {
            Random random = new Random();
            double random_int_one = random.nextDouble() * 100;
            if (random_int_one <= EliteSpawnRate) {
                is_elite = true;
                mod_number = config.EliteModTotal;
                double random_int_two = random.nextDouble() * 100;
                if (random_int_two <= UltraSpawnRate) {
                    is_ultra = true;
                    mod_number = config.UltraModTotal;
                    double random_int_three = random.nextDouble() * 100;
                    if (random_int_three <= EldritchSpawnRate) {
                        is_eldritch = true;
                        mod_number = config.EldritchModTotal;
                    }
                }
            }
            rank_decided = true;
        }
    }

    public String getEntityName(){
        //System.out.println(mob);
        String type = mob.getType().toString();
        String[] split_string = type.split("\\.");
        return split_string[2];
    }

    public String get_mod_string(){
        String ans = "";
        if(modifier_list.toString().equals("[]")){
            return ans;
        }
        else {
            for(String mod_name : modifier_list){
                ans += WordUtils.capitalize(mod_name) + " ";
            }
            ans += WordUtils.capitalize(getEntityName().replaceAll("_"," "));
        }
        return ans;
    }

    public void setMods() {
        if(is_elite) {
            modifier_list.clear();
            if (mob instanceof RangedAttackMob) {
                //ranged_mobs_mods.retainAll(all_mods);
                if(mod_number > ranged_mobs_mods.size()){
                    mod_number = ranged_mobs_mods.size();
                }
                for (int i = 0; i < mod_number; i++) {
                    String random_mod = ranged_mobs_mods.get(new Random().nextInt(mods.size()));
                    ranged_mobs_mods.remove(random_mod);
                    modifier_list.add(random_mod);
                }
            } else if (mob instanceof CreeperEntity) {
                //creeper_mods.retainAll(all_mods);
                if(mod_number > creeper_mods.size()){
                    mod_number = creeper_mods.size();
                }
                for (int i = 0; i < mod_number; i++) {
                    String random_mod = creeper_mods.get(new Random().nextInt(mods.size()));
                    creeper_mods.remove(random_mod);
                    modifier_list.add(random_mod);
                }
            } else {
                //mods.retainAll(all_mods);
                if(mod_number > mods.size()){
                    mod_number = mods.size();
                }
                for (int i = 0; i < mod_number; i++) {
                    String random_mod = mods.get(new Random().nextInt(mods.size()));
                    mods.remove(random_mod);
                    modifier_list.add(random_mod);
                }
            }
        }
    }


    public boolean hasMod(String name) {
        return modifier_list.contains(name);
    }

    @Override
    public void damageActivatedMod(LivingEntity entity, DamageSource source, float amount) {
        if(modifier_list.contains("drowning")){
            mods_hashmap.get("drowning").damageActivatedMod(entity,source,amount);
        }
        if(modifier_list.contains("one_up")){
            mods_hashmap.get("one_up").damageActivatedMod(entity,source,amount);
        }

    }


    public void useAbility(MobEntity entity) {
        for(String mod_name : modifier_list){
            if(!(alt_activated_mods.contains(mod_name))) {
                mods_hashmap.get(mod_name).useAbility(entity);
            }
        }
    }

    public boolean isEldritch(){
        return is_eldritch;
    }

    public boolean isElite(){
        return is_elite;
    }

    public boolean isUltra(){
        return is_ultra;
    }


    @Override
    public void fromTag(CompoundTag tag) {
        this.setIs_elite(tag.getBoolean("elite"));
        this.setIs_ultra(tag.getBoolean("ultra"));
        this.setIs_eldritch(tag.getBoolean("eldritch"));
        this.setRank_decided(tag.getBoolean("rank"));

        CompoundTag saved_mods = tag.getCompound("saved_mods");

        modifier_list.clear();
        for (String mod : saved_mods.getKeys()) {

            modifier_list.add(mod);
        }

    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putBoolean("elite", this.is_elite);
        tag.putBoolean("ultra", this.is_ultra);
        tag.putBoolean("eldritch", this.is_eldritch);
        tag.putBoolean("rank", this.rank_decided);

        CompoundTag saved_mods = new CompoundTag();
        for(String mod_name : modifier_list){
            saved_mods.putString(mod_name,mod_name);
        }

        tag.put("saved_mods", saved_mods);


        return tag;
    }

    public void spawnedInLampChunk(){
        is_elite = false;
        is_ultra = false;
        is_eldritch = false;
        modifier_list.clear();
    }
}
