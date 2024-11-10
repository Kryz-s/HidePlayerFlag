package net.kryz.hideflag.listeners;

import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.kryz.hideflag.Main;
import net.kryz.hideflag.events.PlayerEnterInRegionEvent;
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
        ProtectedRegion region = event.getRegion();
        StateFlag flag = this.main.getHidePlayer();
        assert player != null;

        for(Player showPlayer : Bukkit.getServer().getOnlinePlayers()) {
            for(ProtectedRegion region1 : this.main.getRegions(showPlayer.getUniqueId())){
                if(!region1.getFlags().containsKey(flag) && region1.getFlag(flag) != StateFlag.State.ALLOW) return;
                player.showPlayer(this.main, showPlayer);
                showPlayer.showPlayer(this.main, player);
            }
        }
    }
}
