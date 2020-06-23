package net.hyper_pigeon.eldritch_mobs.mod_components.modifiers;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.mod_components.interfaces.ModifierInterface;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.CompoundTag;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ModifierComponent implements ModifierInterface {

    private boolean is_elite;
    private boolean is_ultra;
    private boolean is_eldritch;
    private boolean rank_decided;
    private AlchemyComponent alchemy = new AlchemyComponent();
    private BeserkComponent beserk = new BeserkComponent();
    private BlindingComponent blinding = new BlindingComponent();
    private BurningComponent burning = new BurningComponent();
    private CloakedComponent cloaked = new CloakedComponent();
    private DrainingComponent draining = new DrainingComponent();
    private DrowningComponent drowning = new DrowningComponent();
    private GhastlyComponent ghastly = new GhastlyComponent();
    private GravityComponent gravity = new GravityComponent();
    private LethargicComponent lethargic = new LethargicComponent();
    private One_UpComponent one_up = new One_UpComponent();
    private RegeneratingComponent regen = new RegeneratingComponent();
    private ResistantComponent resistant = new ResistantComponent();
    private SnatcherComponent snatcher = new SnatcherComponent();
    private SpeedsterComponent speedster = new SpeedsterComponent();
    private SprintingComponent sprinting = new SprintingComponent();
    private StarvingComponent starving = new StarvingComponent();
    private StormyComponent stormy = new StormyComponent();
    private WeaknessComponent weakness = new WeaknessComponent();
    private WebslingingComponent webslinging = new WebslingingComponent();


    public ArrayList<String> mods = new ArrayList<>(Arrays.asList("alchemist", "beserk", "yeeter", "blinding", "burning",
            "cloaked","deflector","draining","drowning","ender","ghastly", "gravity","lethargic","lifesteal","one_up","regen",
            "resistant","rust","snatcher","speedster","sprinter","starving","stormy","thorny","toxic","weakness","webslinging",
            "withering"));


    public ArrayList<String> modifier_list = new ArrayList<>();

    public ModifierComponent() {
        this.setRank();
        this.setMods();
    }

    public void setRank_decided(boolean bool){
        rank_decided = bool;
    }

    public void setIs_elite(boolean bool){
        is_elite = bool;
    }

    public void setIs_ultra(boolean bool){
        is_ultra = bool;
    }

    public void setIs_eldritch(boolean bool){
        is_eldritch = bool;
    }

    public void setRank() {
        if(!rank_decided) {
            Random random = new Random();
            int random_int_one = random.nextInt(10) + 1;
            if (random_int_one == 1) {
                is_elite = true;
                int random_int_two = random.nextInt(10) + 1;
                if (random_int_two <= 2) {
                    is_ultra = true;
                    int random_int_three = random.nextInt(10) + 1;
                    if (random_int_three <= 2) {
                        is_eldritch = true;
                    }
                }
            }
            rank_decided = true;
        }
    }



    public String get_mod_string(){
        return modifier_list.toString();
    }

    public void setMods() {
                if (is_elite) {
                    for (int i = 0; i < 4; i++) {
                        String random_mod = mods.get(new Random().nextInt(mods.size()));
                        mods.remove(random_mod);
                        modifier_list.add(random_mod);
                    }
                    if (is_ultra) {
                        for (int i = 0; i < 3; i++) {
                            String random_mod = mods.get(new Random().nextInt(mods.size()));
                            mods.remove(random_mod);
                            modifier_list.add(random_mod);
                        }

                        if (is_eldritch) {
                            for (int i = 0; i < 4; i++) {
                                String random_mod = mods.get(new Random().nextInt(mods.size()));
                                mods.remove(random_mod);
                                modifier_list.add(random_mod);
                            }
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
            drowning.damageActivatedMod(entity,source,amount);
        }
        if(modifier_list.contains("one_up")){
            one_up.damageActivatedMod(entity,source,amount);
        }

    }


    public void useAbility(MobEntity entity) {
        if(modifier_list.contains("alchemist")){
            alchemy.useAbility(entity);
        }
        if(modifier_list.contains("beserk")){
            beserk.useAbility(entity);
        }
        if(modifier_list.contains("blinding")){
            blinding.useAbility(entity);
        }
        if(modifier_list.contains("burning")){
            burning.useAbility(entity);
        }
        if(modifier_list.contains("cloaked")){
            cloaked.useAbility(entity);
        }
        if(modifier_list.contains("draining")){
            draining.useAbility(entity);
        }
        if(modifier_list.contains("drowning")){
            drowning.useAbility(entity);
        }
        if(modifier_list.contains("ghastly")){
            ghastly.useAbility(entity);
        }
        if(modifier_list.contains("gravity")){
            gravity.useAbility(entity);
        }
        if(modifier_list.contains("lethargic")){
            lethargic.useAbility(entity);
        }
        if(modifier_list.contains("regen")){
            regen.useAbility(entity);
        }
        if(modifier_list.contains("resistant")){
            resistant.useAbility(entity);
        }
        if(modifier_list.contains("snatcher")){
            snatcher.useAbility(entity);
        }
        if(modifier_list.contains("speedster")){
            speedster.useAbility(entity);
        }
        if(modifier_list.contains("sprinter")){
            sprinting.useAbility(entity);
        }
        if(modifier_list.contains("stormy")){
            stormy.useAbility(entity);
        }
        if(modifier_list.contains("weakness")){
            weakness.useAbility(entity);
        }
        if(modifier_list.contains("starving")){
            starving.useAbility(entity);
        }
        if(modifier_list.contains("webslinging")){
            webslinging.useAbility(entity);
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
}
