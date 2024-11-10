package net.kryz.hideflag.listeners;

import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.kryz.hideflag.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Set;

public class PlayerJoinListener implements Listener {
    private final Main main;
    public PlayerJoinListener(final Main main){
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        Set<ProtectedRegion> regions = this.main.getRegions(player.getUniqueId());
        for(ProtectedRegion region : regions){
            if(!region.getFlags().containsKey(this.main.getHidePlayer()) && region.getFlag(this.main.getHidePlayer()) != StateFlag.State.ALLOW) return;
            for(Player hidePlayer : Bukkit.getServer().getOnlinePlayers()){
                player.hidePlayer(this.main, hidePlayer);
            }
        }
    }
}
