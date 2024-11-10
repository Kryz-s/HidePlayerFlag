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
        assert player!= null;

        if(!region.getFlags().containsKey(flag) && region.getFlag(flag) != StateFlag.State.ALLOW && !player.isOnline()) return;
        for(Player showPlayer : Bukkit.getServer().getOnlinePlayers()) {
            player.showPlayer(main, showPlayer);
        }
    }
}
