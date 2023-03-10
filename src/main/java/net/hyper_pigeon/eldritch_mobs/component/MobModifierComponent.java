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
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MobModifierComponent implements ModifierComponent {

    private MobRank rank = MobRank.UNDECIDED;
    private List<Ability> modifiers = new ArrayList<>();
    private final MobEntity provider;
    private int numMaxAbilities = 0;
    private final ServerBossBar bossBar;
    private boolean healthIncreased = false;
    private boolean checkedIfSpawnedInSoothingLanternChunk = false;
    private boolean titleSet = false;
    private boolean setName = false;

    public MobModifierComponent(MobEntity provider) {
        this.provider = provider;
        this.bossBar = new ServerBossBar(Text.of(""), BossBar.Color.GREEN, BossBar.Style.PROGRESS);

        if (canBeBuffed(provider)) {
            randomlySetRank();
            randomlySetModifiers();
        } else this.rank = MobRank.NONE;
    }

    public boolean canBeBuffed(MobEntity mobEntity) {
        return this.rank == MobRank.UNDECIDED && !(mobEntity.hasCustomName() && EldritchMobsMod.ELDRITCH_MOBS_CONFIG.ignoreNamedMobs)
                && (mobEntity.getType().isIn(EldritchMobTagKeys.ALLOWED)
                && !mobEntity.getType().isIn(EldritchMobTagKeys.BLACKLIST)
        );
    }

    @Override
    public void randomlySetRank() {

        if (rank == MobRank.UNDECIDED) {
            Random random = new Random();
            double value = random.nextDouble();

            rank = MobRank.NONE;

            if (provider.getType().isIn(EldritchMobTagKeys.ALWAYS_ELITE) || (value <= EldritchMobsMod.ELDRITCH_MOBS_CONFIG.EliteSpawnRates && value > EldritchMobsMod.ELDRITCH_MOBS_CONFIG.UltraSpawnRates)) {
                setRank(MobRank.ELITE);
            } else if (provider.getType().isIn(EldritchMobTagKeys.ALWAYS_ULTRA) || (value <= EldritchMobsMod.ELDRITCH_MOBS_CONFIG.UltraSpawnRates && value > EldritchMobsMod.ELDRITCH_MOBS_CONFIG.EldritchSpawnRates)) {
                setRank(MobRank.ULTRA);
            } else if (provider.getType().isIn(EldritchMobTagKeys.ALWAYS_ELDRITCH) || value <= EldritchMobsMod.ELDRITCH_MOBS_CONFIG.EldritchSpawnRates) {
                setRank(MobRank.ELDRITCH);
            }
        }
    }

    @Override
    public void randomlySetModifiers() {
        if (rank != MobRank.NONE && rank != MobRank.UNDECIDED) modifiers = AbilityHelper.pickNRandomForEntity(AbilityHelper.getAbilities(), numMaxAbilities, provider.getType());
    }

    public void setTitle() {
        if (!provider.hasCustomName() && !EldritchMobsMod.ELDRITCH_MOBS_CONFIG.turnOffTitles && rank != MobRank.NONE) {
            StringBuilder output = new StringBuilder();
            if (EldritchMobsMod.ELDRITCH_MOBS_CONFIG.genericTitles) {
                String tier = getRank().name().toLowerCase();
                output.append(tier.substring(0, 1).toUpperCase()).append(tier.substring(1)).append(" ").append(provider.getDisplayName().getString());
                provider.setCustomName(Text.of(output.toString()));
            } else {
                for (Ability ability : modifiers) output.append(ability.getName()).append(" ");
                output.append(provider.getDisplayName().getString());
                provider.setCustomName(Text.of(output.toString()));
            }
        }
    }

    public Text getTitle() {
        StringBuilder output = new StringBuilder();

        if (EldritchMobsMod.ELDRITCH_MOBS_CONFIG.genericTitles) {
            String tier = getRank().name().toLowerCase();
            output.append(tier.substring(0, 1).toUpperCase()).append(tier.substring(1)).append(" ").append(provider.getDisplayName().getString());
        } else {
            for (Ability ability : modifiers) output.append(ability.getName()).append(" ");
            output.append(provider.getDisplayName().getString());
        }

        return (Text.of(output.toString()));
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

    public MobRank getRank() {
        return rank;
    }

    public void setRank(MobRank mobRank) {
        this.rank = mobRank;

        switch (mobRank) {
            case ELITE -> {
                numMaxAbilities = AbilityHelper.RANDOM.nextInt(EldritchMobsMod.ELDRITCH_MOBS_CONFIG.EliteMinModifiers, EldritchMobsMod.ELDRITCH_MOBS_CONFIG.EliteMaxModifiers + 1);
                this.bossBar.setColor(BossBar.Color.YELLOW);
            }
            case ULTRA -> {
                numMaxAbilities = AbilityHelper.RANDOM.nextInt(EldritchMobsMod.ELDRITCH_MOBS_CONFIG.UltraMinModifiers, EldritchMobsMod.ELDRITCH_MOBS_CONFIG.UltraMaxModifiers + 1);
                this.bossBar.setColor(BossBar.Color.RED);
            }
            case ELDRITCH -> {
                numMaxAbilities = AbilityHelper.RANDOM.nextInt(EldritchMobsMod.ELDRITCH_MOBS_CONFIG.EldritchMinModifiers, EldritchMobsMod.ELDRITCH_MOBS_CONFIG.EldritchMaxModifiers + 1);
                this.bossBar.setColor(BossBar.Color.PURPLE);
            }
            default -> {}
        }
    }

    private void increaseMaxHealthForModifier(EntityAttributeModifier modifier) {
        EntityAttributeInstance entityAttributeInstance = provider.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        assert entityAttributeInstance != null;
        if (!entityAttributeInstance.hasModifier(modifier)) entityAttributeInstance.addPersistentModifier(modifier);
        provider.setHealth((float) provider.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH));
    }

    public void increaseHealth() {
        if (!healthIncreased) {
            switch (rank) {
                case ELITE    -> increaseMaxHealthForModifier(EldritchMobsAttributeModifiers.ELITE_HEALTH_BOOST   );
                case ULTRA    -> increaseMaxHealthForModifier(EldritchMobsAttributeModifiers.ULTRA_HEALTH_BOOST   );
                case ELDRITCH -> increaseMaxHealthForModifier(EldritchMobsAttributeModifiers.ELDRITCH_HEALTH_BOOST);
                default -> {}
            }
            healthIncreased = true;
        }
    }

    @Override
    public ServerBossBar getBossBar() { return bossBar; }

    @Override
    public void readFromNbt(NbtCompound tag) {

        healthIncreased = tag.getBoolean("healthIncreased");
        numMaxAbilities = tag.getInt("numMaxAbilities");
        checkedIfSpawnedInSoothingLanternChunk = tag.getBoolean("checkedIfSpawnedInSoothingLanternChunk");
        titleSet = tag.getBoolean("titleSet");

        switch (numMaxAbilities) {
            case 1, 2, 3, 4 -> {
                rank = MobRank.ELITE;
                this.bossBar.setColor(BossBar.Color.YELLOW);
            }
            case 5, 6, 7, 8 -> {
                rank = MobRank.ULTRA;
                this.bossBar.setColor(BossBar.Color.RED);
            }
            case 9, 10, 11, 12 -> {
                rank = MobRank.ELDRITCH;
                this.bossBar.setColor(BossBar.Color.PURPLE);
            }
            default -> rank = MobRank.NONE;
        }

        if (modifiers != null) modifiers.clear();

        for (String name : tag.getCompound("abilities").getKeys()) AbilityHelper.getAbilityRecordByName(name).ifPresent(record -> modifiers.add(record.ability));
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("healthIncreased", healthIncreased);
        tag.putInt("numMaxAbilities", numMaxAbilities);
        tag.putBoolean("checkedIfSpawnedInSoothingLanternChunk", checkedIfSpawnedInSoothingLanternChunk);
        tag.putBoolean("titleSet", titleSet);

        NbtCompound mobAbilities = new NbtCompound();

        if (modifiers != null) {
            for (Ability ability : modifiers) mobAbilities.putString(ability.getName(), ability.getName());
        }

        tag.put("abilities", mobAbilities);
    }

    public void makeMobNormal() {
        this.rank = MobRank.NONE;
        clearModifiers();
        provider.setCustomName(null);
        this.bossBar.clearPlayers();
    }

    boolean isPlayerStaring(ServerPlayerEntity player) {
        Vec3d vec3d  = player.getEyePos();
        Vec3d vec3d2 = player.getRotationVec(1.0F);
        Vec3d vec3d3 = vec3d.add(vec3d2.x * 100.0D, vec3d2.y * 100.0D, vec3d2.z * 100.0D);
        EntityHitResult entityHitResult = ProjectileUtil.getEntityCollision(
                player.world,
                player,
                vec3d,
                vec3d3,
                new Box(vec3d, vec3d3).expand(1.0D),
                (entityX) -> !entityX.isSpectator() && entityX instanceof MobEntity,
                0.0F
        );
        if (entityHitResult != null && entityHitResult.getType() == HitResult.Type.ENTITY) {
            MobEntity mobEntity = (MobEntity) entityHitResult.getEntity();
            return mobEntity.equals(provider);
        }
        return false;
    }


    @Override
    public void serverTick() {

        if (!checkedIfSpawnedInSoothingLanternChunk) {
            if (this.rank != MobRank.NONE
                && !provider.getEntityWorld().isClient
                && SoothingLanternPersistentState.get((ServerWorld) provider.getEntityWorld()).containsChunk(provider.getChunkPos())
            ) makeMobNormal();
            checkedIfSpawnedInSoothingLanternChunk = true;
        }

        if(!setName){
            bossBar.setName(provider.getDisplayName());
            setName = true;
        }

        if (getRank() != MobRank.NONE) {

            if (!provider.hasCustomName() && !titleSet && !EldritchMobsMod.ELDRITCH_MOBS_CONFIG.turnOffTitles) {
                setTitle();
                titleSet = true;
            }

            if (!EldritchMobsMod.ELDRITCH_MOBS_CONFIG.turnOffBossBars) {
                if (!provider.hasCustomName()) this.bossBar.setName(getTitle());
                else this.bossBar.setName(provider.getCustomName());

                this.bossBar.setPercent(provider.getHealth() / provider.getMaxHealth());

                if (EldritchMobsMod.ELDRITCH_MOBS_CONFIG.crosshairBossBars) {
                    List<ServerPlayerEntity> bossBarPlayers = bossBar.getPlayers().stream().toList();
                    bossBarPlayers.forEach(player -> {
                        if (!isPlayerStaring(player)) bossBar.removePlayer(player);
                    });
                }
            }
            increaseHealth();
        }
    }
}
