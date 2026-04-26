package net.hyper_pigeon.eldritch_mobs.command;

import net.minecraft.command.permission.Permission;
import net.minecraft.command.permission.PermissionLevel;
import net.minecraft.server.command.ServerCommandSource;

public class CommandPermissionHelper {
    protected static boolean hasPermission(ServerCommandSource source, int legacyPermissionLevel){
        Permission requestedPermission;
        switch (legacyPermissionLevel){
            case 0 -> requestedPermission = new Permission.Level(PermissionLevel.ALL);
            case 1 -> requestedPermission = new Permission.Level(PermissionLevel.MODERATORS);
            case 2 -> requestedPermission = new Permission.Level(PermissionLevel.GAMEMASTERS);
            case 3 -> requestedPermission = new Permission.Level(PermissionLevel.ADMINS);
            case 4 -> requestedPermission = new Permission.Level(PermissionLevel.OWNERS);

            default -> {
                return false;
            }
        }
        return source.getPermissions().hasPermission(requestedPermission);
    }
}
