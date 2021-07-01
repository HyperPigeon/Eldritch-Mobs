package net.hyper_pigeon.eldritch_mobs.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import me.shedaniel.autoconfig.AutoConfig;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.minecraft.command.argument.EntitySummonArgumentType;
import net.minecraft.command.argument.NbtCompoundArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SummonEldritchCommand {
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.summon_eldritch.failed"));
    private static final SimpleCommandExceptionType INVALID_POSITION_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.summon_eldritch.invalidPosition"));

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("summon_eldritch").requires((serverCommandSource) -> {
            return serverCommandSource.hasPermissionLevel(2);
        })).then(((RequiredArgumentBuilder)CommandManager.argument("entity", EntitySummonArgumentType.entitySummon()).suggests(SuggestionProviders.SUMMONABLE_ENTITIES).executes((commandContext) -> {
            return execute((ServerCommandSource)commandContext.getSource(), EntitySummonArgumentType.getEntitySummon(commandContext, "entity"), ((ServerCommandSource)commandContext.getSource()).getPosition(), new NbtCompound(), true);
        })).then(((RequiredArgumentBuilder)CommandManager.argument("pos", Vec3ArgumentType.vec3()).executes((commandContext) -> {
            return execute((ServerCommandSource)commandContext.getSource(), EntitySummonArgumentType.getEntitySummon(commandContext, "entity"), Vec3ArgumentType.getVec3(commandContext, "pos"), new NbtCompound(), true);
        })).then(CommandManager.argument("nbt", NbtCompoundArgumentType.nbtCompound()).executes((commandContext) -> {
            return execute((ServerCommandSource)commandContext.getSource(), EntitySummonArgumentType.getEntitySummon(commandContext, "entity"), Vec3ArgumentType.getVec3(commandContext, "pos"), NbtCompoundArgumentType.getNbtCompound(commandContext, "nbt"), false);
        })))));
    }

    private static int execute(ServerCommandSource source, Identifier entity, Vec3d pos, NbtCompound nbt, boolean initialize) throws CommandSyntaxException {
        EldritchMobsConfig config = AutoConfig.getConfigHolder(EldritchMobsConfig.class).getConfig();
        BlockPos blockPos = new BlockPos(pos);
        if (!World.isValid(blockPos)) {
            throw INVALID_POSITION_EXCEPTION.create();
        } else {
            NbtCompound compoundTag = nbt.copy();
            compoundTag.putString("id", entity.toString());
            ServerWorld serverWorld = source.getWorld();
            Entity entity2 = EntityType.loadEntityWithPassengers(compoundTag, serverWorld, (entityx) -> {
                entityx.refreshPositionAndAngles(pos.x, pos.y, pos.z, entityx.getYaw(), entityx.getPitch());
                return !serverWorld.tryLoadEntity(entityx) ? null : entityx;
            });
            if (entity2 == null) {
                throw FAILED_EXCEPTION.create();
            } else {
                if (initialize && (entity2.getType().isIn(EldritchMobsMod.ELDRITCH_ALLOWED))
                        && !(entity2.getType().isIn(EldritchMobsMod.ELDRITCH_BLACKLIST))) {
                    if(!EldritchMobsMod.ELDRITCH_MODIFIERS.get(entity2).isEldritch()) {
                        EldritchMobsMod.ELDRITCH_MODIFIERS.get(entity2).setIs_elite(true);
                        EldritchMobsMod.ELDRITCH_MODIFIERS.get(entity2).setIs_ultra(true);
                        EldritchMobsMod.ELDRITCH_MODIFIERS.get(entity2).setIs_eldritch(true);
                        EldritchMobsMod.ELDRITCH_MODIFIERS.get(entity2).setMods();
                    }
//                    if(!(config.turnOffNames)) {
//                        entity2.setCustomName(new TranslatableText(EldritchMobsMod.ELDRITCH_MODIFIERS.get(entity2).get_mod_string(), new Object[0]));
//                        entity2.setCustomNameVisible(true);
//                    }
                    ((MobEntity)entity2).initialize(source.getWorld(), source.getWorld().getLocalDifficulty(entity2.getBlockPos()), SpawnReason.COMMAND, (EntityData)null, (NbtCompound) null);

                }
                else {
                    throw FAILED_EXCEPTION.create();
                }

                source.sendFeedback(new TranslatableText("commands.summon_eldritch.success", new Object[]{entity2.getDisplayName()}), true);
                return 1;
            }
        }
    }
}
