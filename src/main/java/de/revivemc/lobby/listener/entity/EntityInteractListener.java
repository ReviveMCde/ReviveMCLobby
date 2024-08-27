package de.revivemc.lobby.listener.entity;

import de.revivemc.lobby.modules.inventory.InventoryModule;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class EntityInteractListener implements Listener {

    @EventHandler
    public void onEntityInteract(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked().getType() == EntityType.ARMOR_STAND) {

            if (event.getRightClicked().getCustomName().equalsIgnoreCase("§8» §bShop §8«")) {

                new InventoryModule(event.getPlayer()).openShopInventory();
            }

            if (event.getRightClicked().getCustomName().equalsIgnoreCase("§8» §6Tägliche Belohnung §8«")) {

                new InventoryModule(event.getPlayer()).openDailyRewardInventory();
            }
        }
    }
}
