package de.revivemc.lobby.listener.player;

import de.revivemc.lobby.modules.location.LocationModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        /* if (player.getLocation().getY() == 40.0D) {
            player.teleport(new LocationModule(player.getLocation()).loadSpawnLocation());
        } */
    }
}
