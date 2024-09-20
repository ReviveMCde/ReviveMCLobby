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
            final InventoryModule inventoryModule = new InventoryModule(event.getPlayer());

            if (event.getRightClicked().getCustomName().equalsIgnoreCase("§8» §bShop §8«")) {

                inventoryModule.openShopInventory();
            }

            if (event.getRightClicked().getCustomName().equalsIgnoreCase("§8» §6Tägliche Belohnung §8«")) {

                inventoryModule.openDailyRewardInventory();
            }

            if (event.getRightClicked().getCustomName().equalsIgnoreCase("§8» §9RevivePass §8«")) {

                inventoryModule.openRevivePassInventory(0);
            }
        }
    }
}
