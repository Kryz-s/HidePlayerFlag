package net.kryz.hideflag.listeners;

import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.kryz.hideflag.HidePlayerPlugin;
import net.kryz.hideflag.NMS;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final HidePlayerPlugin hidePlayerPlugin;
    public PlayerJoinListener(final HidePlayerPlugin hidePlayerPlugin){
        this.hidePlayerPlugin = hidePlayerPlugin;
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event){
        final Player player = event.getPlayer();
        final StateFlag flag = this.hidePlayerPlugin.getHidePlayer();
        for (final ProtectedRegion region : this.hidePlayerPlugin.getRegions(player.getUniqueId())){
            if(region.getFlag(flag) != StateFlag.State.ALLOW || !region.getFlags().containsKey(flag)) return;
            for (Player var1 : Bukkit.getServer().getOnlinePlayers()) {
                var1.hidePlayer(this.hidePlayerPlugin, player);
                NMS.newRemovePlayerEntity(player);
            }
        }
    }
}
