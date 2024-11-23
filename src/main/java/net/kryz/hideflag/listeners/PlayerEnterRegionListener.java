package net.kryz.hideflag.listeners;

import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.kryz.hideflag.Main;
import net.kryz.hideflag.events.PlayerEnterInRegionEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerEnterRegionListener implements Listener {
    private final Main main;

    public PlayerEnterRegionListener(final Main main) {
        this.main = main;
    }

    @EventHandler
    public void onEnterRegion(PlayerEnterInRegionEvent event){
        Player player = event.getPlayer();
        assert player != null;
        ProtectedRegion region = event.getRegion();
        StateFlag flag = this.main.getHidePlayer();

        if (!region.getFlags().containsKey(flag) || region.getFlag(flag) != StateFlag.State.ALLOW) return;
        for (Player var1 : Bukkit.getServer().getOnlinePlayers()) {
            var1.hidePlayer(this.main, player);
            NMS.newRemovePlayerEntity(player);
        }
    }
}
