package de.revivemc.lobby.listener.world;

import de.revivemc.lobby.modules.build.BuildModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        final BuildModule buildModule = new BuildModule(event.getPlayer().getUniqueId());
        if (buildModule.getBuildModeState()) {
            event.setCancelled(false);
            return;
        }
        event.setCancelled(true);
    }
}
