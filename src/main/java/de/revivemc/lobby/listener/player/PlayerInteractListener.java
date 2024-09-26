package de.revivemc.lobby.listener.player;

import de.revivemc.core.ReviveMCAPI;
import de.revivemc.core.entitiesutils.items.ItemCreator;
import de.revivemc.core.playerutils.ReviveMCPlayer;
import de.revivemc.lobby.Lobby;
import de.revivemc.lobby.modules.inventory.InventoryModule;
import de.revivemc.lobby.modules.nick.NickModule;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import javax.swing.*;

public class PlayerInteractListener implements Listener {

    private int task;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final InventoryModule inventoryModule = new InventoryModule(player);
        final ReviveMCPlayer reviveMCPlayer = ReviveMCAPI.getInstance().getCyturaPlayerManager().getPlayers().get(player.getUniqueId());
        final String prefix = Lobby.getInstance().getPrefix(reviveMCPlayer);
        final NickModule nickModule = new NickModule(player.getUniqueId());

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (player.getItemInHand() == null) {
                return;
            }

            if (player.getItemInHand().getItemMeta() == null) {
                return;
            }

            if (player.getItemInHand().getItemMeta().getDisplayName() == null) {
                return;
            }

            if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §cRakete §8× §7Gadget")) {
                player.playSound(player.getLocation(), Sound.FIREWORK_LAUNCH, 1, 1);
                player.setVelocity(new Vector(0, 3, 0));

                task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Lobby.getInstance(), new Runnable() {
                    int cooldown = 3;
                    @Override
                    public void run() {
                        if (cooldown == 3) {
                            player.getInventory().setItem(2, new ItemCreator(Material.FIREWORK_CHARGE).setName("§7...").setAmount(1).toItemStack());
                            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 2);
                        }

                        if (cooldown == 2) {
                            player.getInventory().setItem(2, new ItemCreator(Material.FIREWORK_CHARGE).setName("§7..").setAmount(1).toItemStack());
                            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 2);
                        }

                        if (cooldown == 1) {
                            player.getInventory().setItem(2, new ItemCreator(Material.FIREWORK_CHARGE).setName("§7.").setAmount(1).toItemStack());
                            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 2);
                        }

                        if (cooldown == 0) {
                            Bukkit.getScheduler().cancelTask(task);
                            cooldown = 4;
                            player.getInventory().setItem(2, new ItemCreator(Material.FIREWORK).setName("§8» §cRakete §8× §7Gadget").setAmount(1).toItemStack());
                            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 2);
                        }

                        cooldown--;
                    }
                }, 0, 20);
            }

            if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §c§lNavigator §8× §7Rechtsklick")) {
                inventoryModule.openNavigatorInventory();
                return;
            }

            if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §a§lLobby §7Server §8× §7Rechtsklick")) {
                inventoryModule.openLobbyServerInventory();
                return;
            }

            if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §b§lEinstellungen §8× §7Rechtsklick")) {
                inventoryModule.openSettingsInventory();
                return;
            }

            if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §e§lQuickJoin §8× §7Rechtsklick")) {
                inventoryModule.openQuickJumpInventory(0);
                return;
            }

            if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §b§lEinstellungen §8× §7Rechtsklick")) {
                inventoryModule.openSettingsInventory();
                return;
            }

            if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §9§lFreunde §8× §7Rechtsklick")) {
                inventoryModule.openFriendsInventory();
                return;
            }

            if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §5§lNick §7System §8× §c§lDeaktiviert")) {
                nickModule.updateNick("true");
                player.getInventory().setItem(6, new ItemCreator(Material.NAME_TAG).setName("§8» §5§lNick §7System §8× §a§lAktiviert").setAmount(1).toItemStack());
                player.sendMessage(prefix + "§aDu hast dein Nick System nun aktiviert.");
                return;
            }

            if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §5§lNick §7System §8× §a§lAktiviert")) {
                nickModule.updateNick("false");
                player.getInventory().setItem(6, new ItemCreator(Material.NAME_TAG).setName("§8» §5§lNick §7System §8× §c§lDeaktiviert").setAmount(1).toItemStack());
                player.sendMessage(prefix + "§cDu hast dein Nick System nun deaktiviert.");
            }
        }
    }
}
