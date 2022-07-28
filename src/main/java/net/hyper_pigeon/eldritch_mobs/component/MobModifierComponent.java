package net.hyper_pigeon.eldritch_mobs.component;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityHelper;
import net.hyper_pigeon.eldritch_mobs.component.interfaces.ModifierComponent;
import net.hyper_pigeon.eldritch_mobs.persistent_state.SoothingLanternPersistentState;
import net.hyper_pigeon.eldritch_mobs.rank.MobRank;
import net.hyper_pigeon.eldritch_mobs.register.EldritchMobTagKeys;
import net.hyper_pigeon.eldritch_mobs.register.EldritchMobsAttributeModifiers;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MobModifierComponent implements ModifierComponent {

    private MobRank rank = MobRank.UNDECIDED;
    private List<Ability> modifiers = new ArrayList<>();
    private MobEntity provider;
    private int numMaxAbilities = 0;
    private final ServerBossBar bossBar;
    private boolean healthIncreased = false;
    private boolean checkedIfSpawnedInSoothingLanternChunk = false;


    public MobModifierComponent(MobEntity provider){
        this.provider = provider;
        this.bossBar = (ServerBossBar)(new ServerBossBar(provider.getDisplayName(), BossBar.Color.GREEN, BossBar.Style.PROGRESS));

        if(canBeBuffed(provider)){
            randomlySetRank();
            //maybe move these two methods to server tick for optimization?
            randomlySetModifiers();
            setTitle();
        }
        else {
            this.rank = MobRank.NONE;
        }

    }

    public boolean canBeBuffed(MobEntity mobEntity){
        boolean isBuffable =  this.rank == MobRank.UNDECIDED && !(mobEntity.hasCustomName() && EldritchMobsMod.ELDRITCH_MOBS_CONFIG.ignoreNamedMobs)
                && (mobEntity.getType().isIn(EldritchMobTagKeys.ALLOWED) &&
                !mobEntity.getType().isIn(EldritchMobTagKeys.BLACKLIST));
        return isBuffable;
    }

    @Override
    public void randomlySetRank() {

        if(rank == MobRank.UNDECIDED) {
            Random random = new Random();
            double value = random.nextDouble();

            rank = MobRank.NONE;

            if(provider.getType().isIn(EldritchMobTagKeys.ALWAYS_ELITE) || (value <= EldritchMobsMod.ELDRITCH_MOBS_CONFIG.EliteSpawnRates && value > EldritchMobsMod.ELDRITCH_MOBS_CONFIG.UltraSpawnRates)){
                setRank(MobRank.ELITE);
            }
            else if(provider.getType().isIn(EldritchMobTagKeys.ALWAYS_ULTRA) ||(value<= EldritchMobsMod.ELDRITCH_MOBS_CONFIG.UltraSpawnRates && value > EldritchMobsMod.ELDRITCH_MOBS_CONFIG.EldritchSpawnRates)){
                setRank(MobRank.ULTRA);
            }
            else if(provider.getType().isIn(EldritchMobTagKeys.ALWAYS_ELDRITCH) || value <= EldritchMobsMod.ELDRITCH_MOBS_CONFIG.EldritchSpawnRates){
                setRank(MobRank.ELDRITCH);
            }
        }


    }

    @Override
    public void randomlySetModifiers() {
        if(rank != MobRank.NONE && rank != MobRank.UNDECIDED) {
            modifiers = AbilityHelper.pickNRandomForEntity(AbilityHelper.all_abilities, numMaxAbilities, provider.getType());
        }
    }

    public void setTitle(){
        if(!provider.hasCustomName() && !EldritchMobsMod.ELDRITCH_MOBS_CONFIG.turnOffTitles && rank != MobRank.NONE) {
            String output = "";
            if(EldritchMobsMod.ELDRITCH_MOBS_CONFIG.genericTitles) {
                String tier = getRank().name().toLowerCase();
                output += tier.substring(0,1).toUpperCase() + tier.substring(1) + " " + provider.getDisplayName().getString();
                provider.setCustomName(Text.of(output));
            }
            else {

                for (Ability ability: modifiers){
                    output += ability.getName() + " ";
                }

                output += provider.getDisplayName().getString();

                provider.setCustomName(Text.of(output));
            }

        }
    }

    public Text getTitle(){
        String output = "";

        if(EldritchMobsMod.ELDRITCH_MOBS_CONFIG.genericTitles) {
            String tier = getRank().name().toLowerCase();
            output += tier.substring(0,1).toUpperCase() + tier.substring(1) + " " + provider.getDisplayName().getString();
        }
        else {
            for (Ability ability: modifiers){
                output += ability.getName() + " ";
            }

            output += provider.getDisplayName().getString();
        }


        return (Text.of(output));
    }


    @Override
    public List<Ability> getModifiers() {
        return modifiers;
    }

    @Override
    public void clearModifiers() {
        modifiers.clear();
        numMaxAbilities = 0;
    }

    public MobRank getRank(){
        return rank;
    }

    public void setRank(MobRank mobRank){
        this.rank = mobRank;
        if(mobRank == MobRank.ELITE){
            numMaxAbilities = AbilityHelper.random
                    .nextInt(EldritchMobsMod.ELDRITCH_MOBS_CONFIG.EliteMaxModifiers + 1 - EldritchMobsMod.ELDRITCH_MOBS_CONFIG.EliteMinModifiers)
                    + EldritchMobsMod.ELDRITCH_MOBS_CONFIG.EliteMinModifiers;
            this.bossBar.setColor(BossBar.Color.YELLOW);
        }
        else if(mobRank == MobRank.ULTRA){
            numMaxAbilities = AbilityHelper.random
                    .nextInt(EldritchMobsMod.ELDRITCH_MOBS_CONFIG.UltraMaxModifiers + 1 - EldritchMobsMod.ELDRITCH_MOBS_CONFIG.UltraMinModifiers)
                    + EldritchMobsMod.ELDRITCH_MOBS_CONFIG.UltraMinModifiers;
            this.bossBar.setColor(BossBar.Color.RED);
        }
        else if(mobRank == MobRank.ELDRITCH){
            numMaxAbilities = AbilityHelper.random
                    .nextInt(EldritchMobsMod.ELDRITCH_MOBS_CONFIG.EldritchMaxModifiers + 1 - EldritchMobsMod.ELDRITCH_MOBS_CONFIG.EldritchMinModifiers)
                    + EldritchMobsMod.ELDRITCH_MOBS_CONFIG.EldritchMinModifiers;
            this.bossBar.setColor(BossBar.Color.PURPLE);
        }
    }

    public void increaseHealth(){
        if(!healthIncreased){
            if(this.rank == MobRank.ELITE){
                EntityAttributeInstance entityAttributeInstance = provider.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
                assert entityAttributeInstance != null;
                if(!entityAttributeInstance.hasModifier(EldritchMobsAttributeModifiers.ELITE_HEALTH_BOOST)) {
                    entityAttributeInstance.addPersistentModifier(EldritchMobsAttributeModifiers.ELITE_HEALTH_BOOST);
                }
                provider.setHealth((float)provider.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH));
            }
            else if(this.rank == MobRank.ULTRA){
                EntityAttributeInstance entityAttributeInstance = provider.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
                assert entityAttributeInstance != null;
                if(!entityAttributeInstance.hasModifier(EldritchMobsAttributeModifiers.ULTRA_HEALTH_BOOST)) {
                    entityAttributeInstance.addPersistentModifier(EldritchMobsAttributeModifiers.ULTRA_HEALTH_BOOST);
                }
                provider.setHealth((float)provider.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH));
            }
            else if(this.rank == MobRank.ELDRITCH){
                EntityAttributeInstance entityAttributeInstance = provider.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
                assert entityAttributeInstance != null;
                if(!entityAttributeInstance.hasModifier(EldritchMobsAttributeModifiers.ELDRITCH_HEALTH_BOOST)){
                    entityAttributeInstance.addPersistentModifier(EldritchMobsAttributeModifiers.ELDRITCH_HEALTH_BOOST);
                }
                provider.setHealth((float)provider.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH));
            }
            healthIncreased = true;
        }
    }

    @Override
    public ServerBossBar getBossBar() {
        return bossBar;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {

        healthIncreased = tag.getBoolean("healthIncreased");
        numMaxAbilities = tag.getInt("numMaxAbilities");
        checkedIfSpawnedInSoothingLanternChunk = tag.getBoolean("checkedIfSpawnedInSoothingLanternChunk");

        if(numMaxAbilities == 0){
            rank = MobRank.NONE;
        }
        else if(numMaxAbilities <= 4 && numMaxAbilities >= 1){
            rank = MobRank.ELITE;
            this.bossBar.setColor(BossBar.Color.YELLOW);
        }
        else if(numMaxAbilities <= 8 && numMaxAbilities >= 5){
            rank = MobRank.ULTRA;
            this.bossBar.setColor(BossBar.Color.RED);
        }
        else if(numMaxAbilities <= 12 && numMaxAbilities >= 9){
            rank = MobRank.ULTRA;
            this.bossBar.setColor(BossBar.Color.PURPLE);
        }

        NbtCompound abilities = tag.getCompound("abilities");

        if(modifiers != null) {
            modifiers.clear();
        }

        for (String ability : abilities.getKeys()){
            modifiers.add(AbilityHelper.abilityNames.get(ability));
        }

    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("healthIncreased", healthIncreased);
        tag.putInt("numMaxAbilities",numMaxAbilities);
        tag.putBoolean("checkedIfSpawnedInSoothingLanternChunk",checkedIfSpawnedInSoothingLanternChunk);

        NbtCompound mobAbilities = new NbtCompound();

        if(modifiers != null){
            Iterator<Ability> iterator = modifiers.iterator();
            while(iterator.hasNext()){
                Ability ability = iterator.next();
                mobAbilities.putString(ability.getName(),ability.getName());
            }
        }

        tag.put("abilities",mobAbilities);

    }

    public void makeMobNormal(){
        this.rank = MobRank.NONE;
        clearModifiers();
        provider.setCustomName(null);
        this.bossBar.clearPlayers();
    }


    @Override
    public void serverTick() {
        if(!checkedIfSpawnedInSoothingLanternChunk)
        {
            if(this.rank != MobRank.NONE && !provider.getEntityWorld().isClient() &&
                    SoothingLanternPersistentState.get((ServerWorld) provider.getEntityWorld()).containsChunk(provider.getChunkPos())){
                makeMobNormal();
            }
            checkedIfSpawnedInSoothingLanternChunk = true;
        }

        if(getRank() != MobRank.NONE) {
            if(!EldritchMobsMod.ELDRITCH_MOBS_CONFIG.turnOffBossBars) {
                if(!provider.hasCustomName()) {
                    this.bossBar.setName(getTitle());
                }
                else {
                    this.bossBar.setName(provider.getCustomName());
                }
                this.bossBar.setPercent(provider.getHealth() / provider.getMaxHealth());
            }
            increaseHealth();
        }
    }
}
