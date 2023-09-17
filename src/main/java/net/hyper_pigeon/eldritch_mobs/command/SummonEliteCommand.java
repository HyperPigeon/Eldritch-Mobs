package net.hyper_pigeon.eldritch_mobs.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.rank.MobRank;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.NbtCompoundArgumentType;
import net.minecraft.command.argument.RegistryEntryArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SummonEliteCommand {
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.summon_elite.failed"));
    private static final SimpleCommandExceptionType FAILED_UUID_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.summon_elite.failed.uuid"));
    private static final SimpleCommandExceptionType INVALID_POSITION_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.summon_elite.invalidPosition"));

    public SummonEliteCommand() {

    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(
                (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("summon_elite").requires(source -> source.hasPermissionLevel(2)))
                        .then(
                                ((RequiredArgumentBuilder)CommandManager.argument(
                                                "entity", RegistryEntryArgumentType.registryEntry(registryAccess, RegistryKeys.ENTITY_TYPE)
                                        )
                                        .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                        .executes(
                                                context -> execute(
                                                        (ServerCommandSource)context.getSource(),
                                                        RegistryEntryArgumentType.getSummonableEntityType(context, "entity"),
                                                        ((ServerCommandSource)context.getSource()).getPosition(),
                                                        new NbtCompound(),
                                                        true
                                                )
                                        ))
                                        .then(
                                                ((RequiredArgumentBuilder)CommandManager.argument("pos", Vec3ArgumentType.vec3())
                                                        .executes(
                                                                context -> execute(
                                                                        (ServerCommandSource)context.getSource(),
                                                                        RegistryEntryArgumentType.getSummonableEntityType(context, "entity"),
                                                                        Vec3ArgumentType.getVec3(context, "pos"),
                                                                        new NbtCompound(),
                                                                        true
                                                                )
                                                        ))
                                                        .then(
                                                                CommandManager.argument("nbt", NbtCompoundArgumentType.nbtCompound())
                                                                        .executes(
                                                                                context -> execute(
                                                                                        (ServerCommandSource)context.getSource(),
                                                                                        RegistryEntryArgumentType.getSummonableEntityType(context, "entity"),
                                                                                        Vec3ArgumentType.getVec3(context, "pos"),
                                                                                        NbtCompoundArgumentType.getNbtCompound(context, "nbt"),
                                                                                        false
                                                                                )
                                                                        )
                                                        )
                                        )
                        )
        );
    }

    public static Entity summon(ServerCommandSource source, RegistryEntry.Reference<EntityType<?>> entityType, Vec3d pos, NbtCompound nbt, boolean initialize) throws CommandSyntaxException {
        BlockPos blockPos = BlockPos.ofFloored(pos);
        if (!World.isValid(blockPos)) {
            throw INVALID_POSITION_EXCEPTION.create();
        } else {
            NbtCompound nbtCompound = nbt.copy();
            nbtCompound.putString("id", entityType.registryKey().getValue().toString());
            ServerWorld serverWorld = source.getWorld();
            Entity entity = EntityType.loadEntityWithPassengers(nbtCompound, serverWorld, entityx -> {
                entityx.refreshPositionAndAngles(pos.x, pos.y, pos.z, entityx.getYaw(), entityx.getPitch());
                return entityx;
            });
            if (entity == null) {
                throw FAILED_EXCEPTION.create();
            } else {
                if (initialize && entity instanceof MobEntity) {
                    ((MobEntity)entity)
                            .initialize(source.getWorld(), source.getWorld().getLocalDifficulty(entity.getBlockPos()), SpawnReason.COMMAND, null, null);
                    if (EldritchMobsMod.ELDRITCH_MODIFIERS.get(entity).getModifiers() != null) {
                        EldritchMobsMod.ELDRITCH_MODIFIERS.get(entity).setRank(MobRank.NONE);
                        EldritchMobsMod.ELDRITCH_MODIFIERS.get(entity).clearModifiers();
                        entity.setCustomName(null);
                    }
                    EldritchMobsMod.ELDRITCH_MODIFIERS.get(entity).setRank(MobRank.ELITE);
                    EldritchMobsMod.ELDRITCH_MODIFIERS.get(entity).randomlySetModifiers();
                    EldritchMobsMod.ELDRITCH_MODIFIERS.get(entity).setTitle();
                }

                if (!serverWorld.spawnNewEntityAndPassengers(entity)) {
                    throw FAILED_UUID_EXCEPTION.create();
                } else {
                    return entity;
                }
            }
        }
    }


    private static int execute(ServerCommandSource source, RegistryEntry.Reference<EntityType<?>> entityType, Vec3d pos, NbtCompound nbt, boolean initialize) throws CommandSyntaxException {
        Entity entity = summon(source, entityType, pos, nbt, initialize);
        source.sendFeedback(Text.translatable("commands.summon_elite.success", new Object[]{entity.getDisplayName()}), true);
        return 1;
    }


}
