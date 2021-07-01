package net.hyper_pigeon.eldritch_mobs.eldritch_boss_bar;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.BossBarS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class EldritchBossBar extends ServerBossBar {

    private LivingEntity owner;


    public EldritchBossBar(Text displayName, BossBar.Color color, BossBar.Style style, LivingEntity special_mob) {
        super(displayName, color, style);
        owner = special_mob;
    }

    public LivingEntity getOwner(){
        return owner;
    }

    private boolean isPlayerStaring(PlayerEntity player) {
        return player.canSee(owner);
    }

//    private void sendPacket(BossBarS2CPacket.Type type, ServerPlayerEntity player) {
//            BossBarS2CPacket bossBarS2CPacket = new BossBarS2CPacket(type, this);
//            if(isPlayerStaring(player)){
//                player.networkHandler.sendPacket(bossBarS2CPacket);
//            }
//    }
}
