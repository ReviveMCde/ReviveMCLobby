package de.revivemc.lobby.listener.world;

import de.revivemc.lobby.modules.build.BuildModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        final BuildModule buildModule = new BuildModule(event.getPlayer().getUniqueId());
        if (buildModule.getBuildModeState()) {
            event.setCancelled(false);
            return;
        }
        event.setCancelled(true);
    }
}
