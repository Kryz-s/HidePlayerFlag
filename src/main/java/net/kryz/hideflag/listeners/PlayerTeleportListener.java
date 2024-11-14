package net.kryz.hideflag.listeners;

import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.kryz.hideflag.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Set;

public class PlayerTeleportListener implements Listener {
    private final Main main;

    public PlayerTeleportListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onTeleport(final PlayerTeleportEvent event){
        final Player player = event.getPlayer();
        final Set<ProtectedRegion> regions = main.getRegionsAtLocation(event.getTo());
        for(final Player p : Bukkit.getServer().getOnlinePlayers()){
            for(final ProtectedRegion region : regions){
                if(region.getFlags().containsKey(main.getHidePlayer()) || region.getFlag(main.getHidePlayer()) == StateFlag.State.ALLOW){
                    p.hideEntity(main, player);
                }
            }
        }
    }
}
