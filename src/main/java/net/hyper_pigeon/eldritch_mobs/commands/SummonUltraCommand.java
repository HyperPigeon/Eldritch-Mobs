package net.hyper_pigeon.eldritch_mobs.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.minecraft.command.arguments.EntitySummonArgumentType;
import net.minecraft.command.arguments.NbtCompoundTagArgumentType;
import net.minecraft.command.arguments.Vec3ArgumentType;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SummonUltraCommand {
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.summon_ultra.failed"));
    private static final SimpleCommandExceptionType INVALID_POSITION_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.summon_ultra.invalidPosition"));

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder) CommandManager.literal("summon_ultra").requires((serverCommandSource) -> {
            return serverCommandSource.hasPermissionLevel(2);
        })).then(((RequiredArgumentBuilder)CommandManager.argument("entity", EntitySummonArgumentType.entitySummon()).suggests(SuggestionProviders.SUMMONABLE_ENTITIES).executes((commandContext) -> {
            return execute((ServerCommandSource)commandContext.getSource(), EntitySummonArgumentType.getEntitySummon(commandContext, "entity"), ((ServerCommandSource)commandContext.getSource()).getPosition(), new CompoundTag(), true);
        })).then(((RequiredArgumentBuilder)CommandManager.argument("pos", Vec3ArgumentType.vec3()).executes((commandContext) -> {
            return execute((ServerCommandSource)commandContext.getSource(), EntitySummonArgumentType.getEntitySummon(commandContext, "entity"), Vec3ArgumentType.getVec3(commandContext, "pos"), new CompoundTag(), true);
        })).then(CommandManager.argument("nbt", NbtCompoundTagArgumentType.nbtCompound()).executes((commandContext) -> {
            return execute((ServerCommandSource)commandContext.getSource(), EntitySummonArgumentType.getEntitySummon(commandContext, "entity"), Vec3ArgumentType.getVec3(commandContext, "pos"), NbtCompoundTagArgumentType.getCompoundTag(commandContext, "nbt"), false);
        })))));
    }

    private static int execute(ServerCommandSource source, Identifier entity, Vec3d pos, CompoundTag nbt, boolean initialize) throws CommandSyntaxException {
        BlockPos blockPos = new BlockPos(pos);
        if (!World.method_25953(blockPos)) {
            throw INVALID_POSITION_EXCEPTION.create();
        } else {
            CompoundTag compoundTag = nbt.copy();
            compoundTag.putString("id", entity.toString());
            ServerWorld serverWorld = source.getWorld();
            Entity entity2 = EntityType.loadEntityWithPassengers(compoundTag, serverWorld, (entityx) -> {
                entityx.refreshPositionAndAngles(pos.x, pos.y, pos.z, entityx.yaw, entityx.pitch);
                return !serverWorld.tryLoadEntity(entityx) ? null : entityx;
            });
            if (entity2 == null) {
                throw FAILED_EXCEPTION.create();
            } else {
                if (initialize && entity2 instanceof MobEntity) {
                    if(!EldritchMobsMod.ELDRITCH_MODIFIERS.get(entity2).isUltra()) {
                        EldritchMobsMod.ELDRITCH_MODIFIERS.get(entity2).setIs_elite(true);
                        EldritchMobsMod.ELDRITCH_MODIFIERS.get(entity2).setIs_ultra(true);
                        EldritchMobsMod.ELDRITCH_MODIFIERS.get(entity2).setIs_eldritch(false);
                        EldritchMobsMod.ELDRITCH_MODIFIERS.get(entity2).setMods();
                    }
                    entity2.setCustomName(new TranslatableText(EldritchMobsMod.ELDRITCH_MODIFIERS.get(entity2).get_mod_string(), new Object[0]));
                    entity2.setCustomNameVisible(true);
                    ((MobEntity)entity2).initialize(source.getWorld(), source.getWorld().getLocalDifficulty(entity2.getBlockPos()), SpawnReason.COMMAND, (EntityData)null, (CompoundTag)null);

                }

                source.sendFeedback(new TranslatableText("commands.summon_ultra.success", new Object[]{entity2.getDisplayName()}), true);
                return 1;
            }
        }
    }
}
