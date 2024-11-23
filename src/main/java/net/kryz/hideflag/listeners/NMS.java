package net.kryz.hideflag.listeners;

import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NMS {

    public static void newRemovePlayerEntity(Player vanished){
        EntityPlayer entityPlayer = getCraftPlayer(vanished);
        assert entityPlayer != null;
        ClientboundPlayerInfoUpdatePacket add_player = new ClientboundPlayerInfoUpdatePacket(
                ClientboundPlayerInfoUpdatePacket.a.a, entityPlayer
        );
        ClientboundPlayerInfoUpdatePacket display_name = new ClientboundPlayerInfoUpdatePacket(
                ClientboundPlayerInfoUpdatePacket.a.f, entityPlayer
        );
        ClientboundPlayerInfoUpdatePacket listed = new ClientboundPlayerInfoUpdatePacket(
                ClientboundPlayerInfoUpdatePacket.a.d, entityPlayer
        );
        entityPlayer.d.ae().a(add_player);
        entityPlayer.d.ae().a(display_name);
        entityPlayer.d.ae().a(listed);
    }

    private static EntityPlayer getCraftPlayer(final Player vanished){
        try {
            final String craftbukkitclass = Bukkit.getServer().getClass().getPackage().getName();
            final Class<?> craftPlayerClass = Class.forName(craftbukkitclass + ".entity.CraftPlayer");
            final Method getHandleMethod = craftPlayerClass.getMethod("getHandle");
            final Object entityPlayer = getHandleMethod.invoke(vanished);
            return (EntityPlayer) entityPlayer;
        } catch (NoSuchMethodException | NoSuchFieldError | IllegalAccessException | InvocationTargetException | ClassNotFoundException ignored){}
//            Field playerConnectionField = entityPlayer.getClass().getDeclaredField("c");
//            playerConnectionField.setAccessible(true);
//            Object playerConnection = playerConnectionField.get(entityPlayer);
//            Class <?> packetClass = Class.forName("net.minecraft.network.protocol.Packet")
//            Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("a", packetClass);
//            sendPacketMethod.setAccessible(true);
//            sendPacketMethod.invoke(playerConnection, packet);
        return null;
    }
}
