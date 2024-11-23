package net.kryz.hideflag;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import net.kryz.hideflag.listeners.PlayerEnterRegionListener;
import net.kryz.hideflag.listeners.PlayerJoinListener;
import net.kryz.hideflag.listeners.PlayerLeftRegionListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

public class Main extends JavaPlugin {
    private static StateFlag HIDE_PLAYER = new StateFlag("hide-player", false);
    private static RegionContainer container;
    @Override
    public void onLoad(){
        this.setupFlag();
    }
    @Override
    public void onEnable(){
        PluginManager pm = Bukkit.getPluginManager();
        Logger log = Bukkit.getLogger();
        if (!WorldGuard.getInstance().getPlatform().getSessionManager().registerHandler(Entry.factory, null)) {
            log.severe("[WorldGuardEvents] Could not register the entry handler !");
            log.severe("[WorldGuardEvents] Please report this error. The plugin will now be disabled.");

            pm.disablePlugin(this);
            return;
        }
        this.setupListeners();
        container = WorldGuard.getInstance().getPlatform().getRegionContainer();
    }

    private void setupFlag(){
        final FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        try {
            registry.register(HIDE_PLAYER);
        } catch (FlagConflictException e) {
            Flag<?> existing = registry.get("hide-player");
            if (existing instanceof StateFlag) {
                HIDE_PLAYER = (StateFlag) existing;
            }
        }
    }

    private void setupListeners(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerEnterRegionListener(this), this);
        pm.registerEvents(new PlayerLeftRegionListener(this), this);
        pm.registerEvents(new PlayerJoinListener(this), this);
        //pm.registerEvents(new PlayerTeleportListener(this), this);
    }

    public StateFlag getHidePlayer(){
        return HIDE_PLAYER;
    }
    public Set<ProtectedRegion> getRegions(UUID playerUUID) {
        Player player = Bukkit.getServer().getPlayer(playerUUID);
        if (player == null || !player.isOnline())
            return Collections.emptySet();

        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(player.getLocation()));
        return set.getRegions();
    }
    public Set<ProtectedRegion> getRegionsAtLocation(final Location loc) {
        final RegionQuery query = container.createQuery();
        final ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(loc));
        return set.getRegions();
    }
}
