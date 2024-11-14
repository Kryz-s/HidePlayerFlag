package net.kryz.hideflag;

import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.Handler;
import net.kryz.hideflag.events.PlayerEnterInRegionEvent;
import net.kryz.hideflag.events.PlayerExitInRegionEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.Set;

public class Entry extends Handler implements Listener {

    private static final PluginManager pm = Bukkit.getPluginManager();
    public static final Factory factory = new Factory();

    public static class Factory extends Handler.Factory<Entry> {
        @Override
        public Entry create(final Session session) {
            return new Entry(session);
        }
    }

    public Entry(Session session) {
        super(session);
    }

    @Override
    public boolean onCrossBoundary(final LocalPlayer player, final Location from, final Location to, final ApplicableRegionSet toSet, final Set<ProtectedRegion> entered, final Set<ProtectedRegion> left, final MoveType moveType)
    {
        for(ProtectedRegion r : entered) {
            PlayerEnterInRegionEvent regentered = new PlayerEnterInRegionEvent(player.getUniqueId(), r);
            pm.callEvent(regentered);
            if(regentered.isCancelled()) return false;
        }
        for(ProtectedRegion r : left) {
            PlayerExitInRegionEvent regleft = new PlayerExitInRegionEvent(player.getUniqueId(), r);
            pm.callEvent(regleft);
            if(regleft.isCancelled()) return false;
        }
        return true;
    }
}
