package de.revivemc.lobby.listener.cancel;

import de.revivemc.lobby.modules.build.BuildModule;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;

public class LobbyCancelListener implements Listener {



    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        final BuildModule buildModule = new BuildModule(player.getUniqueId());
        if (buildModule.getBuildModeState()) {
            event.setCancelled(false);
            return;
        }
        event.setCancelled(true);

    }

    @EventHandler
    public void onhandle(PlayerInteractEvent event) {
        final BuildModule buildModule = new BuildModule(event.getPlayer().getUniqueId());
        try {
            if (event.getPlayer().getItemInHand().getType() == Material.FIREWORK) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.FENCE_GATE) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.TRAP_DOOR) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.DARK_OAK_DOOR) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.ACACIA_DOOR) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.BIRCH_DOOR) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.JUNGLE_DOOR) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.WOODEN_DOOR) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.BEACON) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.COMMAND) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.CHEST) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.ENDER_CHEST) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.ARMOR_STAND) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.DAYLIGHT_DETECTOR) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.DAYLIGHT_DETECTOR_INVERTED) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.HOPPER) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.ANVIL) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.FURNACE) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.DARK_OAK_FENCE_GATE) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.ACACIA_FENCE_GATE) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.BIRCH_FENCE_GATE) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.JUNGLE_FENCE_GATE) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.SPRUCE_FENCE_GATE) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.NOTE_BLOCK) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.WORKBENCH) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.LEVER) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.STONE_BUTTON) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.WOOD_BUTTON) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.DARK_OAK_DOOR) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.ACACIA_DOOR) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.WOODEN_DOOR) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.DISPENSER) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.JUNGLE_DOOR) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.BIRCH_DOOR) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.SPRUCE_DOOR) {
                if (buildModule.getBuildModeState()) {
                    event.setCancelled(false);
                    return;
                }
                event.setCancelled(true);
            }
        } catch (Exception ex) {
            ;
        }
    }

    @EventHandler
    public void noUproot(PlayerInteractEvent event) {
        final BuildModule buildModule = new BuildModule(event.getPlayer().getUniqueId());
        if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.SOIL) {
            if (buildModule.getBuildModeState()) {
                event.setCancelled(false);
                return;
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemFrameManipulate(PlayerInteractEntityEvent event) {
        final BuildModule buildModule = new BuildModule(event.getPlayer().getUniqueId());
        if (event.getRightClicked() instanceof ItemFrame) {
            if (buildModule.getBuildModeState()) {
                event.setCancelled(false);
                return;
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
        final BuildModule buildModule = new BuildModule(event.getPlayer().getUniqueId());
        if (buildModule.getBuildModeState()) {
            event.setCancelled(false);
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerItemHoldEvent(PlayerItemHeldEvent event) {
        final Player player = event.getPlayer();
        player.getInventory().getItem(event.getNewSlot());
        player.playSound(player.getLocation(), Sound.LAVA_POP, 1, 2);
    }

    @EventHandler
    public void onPlayerItemDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }
}
