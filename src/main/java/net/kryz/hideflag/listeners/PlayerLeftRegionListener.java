package net.kryz.hideflag.listeners;

import net.kryz.hideflag.Main;
import net.kryz.hideflag.events.PlayerExitInRegionEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerLeftRegionListener implements Listener {
    private final Main main;
    public PlayerLeftRegionListener(final Main main){
        this.main = main;
    }

    @EventHandler
    public void onLeftRegion(PlayerExitInRegionEvent event){
        Player player = event.getPlayer();
        assert player != null;

        for(Player showPlayer : Bukkit.getServer().getOnlinePlayers()) {
            showPlayer.showPlayer(this.main, player);
        }
    }
}
