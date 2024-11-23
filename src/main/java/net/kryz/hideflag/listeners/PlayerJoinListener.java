package net.kryz.hideflag.listeners;

import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.kryz.hideflag.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final Main main;
    public PlayerJoinListener(final Main main){
        this.main = main;
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event){
        final Player player = event.getPlayer();
        final StateFlag flag = this.main.getHidePlayer();
        for (final ProtectedRegion region : this.main.getRegions(player.getUniqueId())){
            if(region.getFlag(flag) != StateFlag.State.ALLOW || !region.getFlags().containsKey(flag)) return;
            for (Player var1 : Bukkit.getServer().getOnlinePlayers()) {
                var1.hidePlayer(this.main, player);
                NMS.newRemovePlayerEntity(player);
            }
        }
    }
}
