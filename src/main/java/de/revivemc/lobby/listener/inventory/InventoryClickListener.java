package de.revivemc.lobby.listener.inventory;

import de.revivemc.core.playerutils.ReviveMCPlayer;
import de.revivemc.lobby.modules.revivepass.RevivePassModule;
import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.player.ICloudPlayer;
import eu.thesimplecloud.api.player.connection.IPlayerAddress;
import eu.thesimplecloud.api.service.ICloudService;
import de.revivemc.core.ReviveMCAPI;
import de.revivemc.core.entitiesutils.items.ItemCreator;
import de.revivemc.core.gameutils.coins.CoinsUtils;
import de.revivemc.core.gameutils.sync.GameSync;
import de.revivemc.core.playerutils.ReviveMCPlayer;
import de.revivemc.core.playerutils.scoreboard.ReviveMCScoreboardBuilder;
import de.revivemc.lobby.Lobby;
import de.revivemc.lobby.modules.database.node.DeluxeCooldown;
import de.revivemc.lobby.modules.database.node.PlayerCooldown;
import de.revivemc.lobby.modules.database.node.PremiumCooldown;
import de.revivemc.lobby.modules.inventory.InventoryModule;
import de.revivemc.lobby.modules.lobby.LobbyModule;
import de.revivemc.lobby.modules.location.LocationModule;
import de.revivemc.lobby.modules.player.PlayerModule;
import de.revivemc.lobby.modules.scoreboard.ScoreboardModule;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import sun.security.x509.OtherName;

import javax.swing.*;
import java.util.Objects;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        final ReviveMCPlayer reviveMCPlayer = ReviveMCAPI.getInstance().getCyturaPlayerManager().getPlayers().get(player.getUniqueId());
        final InventoryModule inventoryModule = new InventoryModule(player);
        String lobbyShopBuyName = "null";
        String lobbyShopBuyData = "null";
        int lobbyShopBuyId = 1;
        int lobbyShopBuyPrice = 0;

        if (event.getCurrentItem() == null) {
            return;
        }

        if (event.getCurrentItem().getItemMeta() == null) {
            return;
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName() == null) {
            return;
        }

        if (event.getInventory().getName().equalsIgnoreCase("§8» §cNavigator §8«")) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §aSpawn")) {
                player.teleport(new LocationModule(player.getLocation()).loadSpawnLocation());
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §cBedWars")) {
                player.teleport(new LocationModule(player.getLocation()).loadBedWarsLocation());
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §9BuildFFA")) {
                player.teleport(new LocationModule(player.getLocation()).loadBuildFFALocation());
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
            }
        }

        if (event.getInventory().getName().equalsIgnoreCase("§8» §9RevivePass §8«")) {

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Kaufen für 100.000 Coins.")) {
                inventoryModule.openRevivePassInventory(90);
            }
        }

        if (event.getInventory().getName().equalsIgnoreCase("§8» §9RevivePass §8× §aKaufen §8«")) {
            final RevivePassModule revivePassModule = new RevivePassModule(player.getUniqueId());


            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §aKaufen §8× §6100.000 §7Coins")) {
                if (Integer.parseInt(reviveMCPlayer.getProperty("coins").getValueAsString()) <= 100000) {
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "§cDu hast nicht ausreichende Coins um den RevivePass zu kaufen.");
                    player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 1, 1);
                    event.getView().close();
                    return;
                }

                revivePassModule.setPurchaseState("true");
                reviveMCPlayer.setProperty("coins", Integer.parseInt(reviveMCPlayer.getProperty("coins").getValueAsString()) - 100000);
                reviveMCPlayer.update();

                final ReviveMCScoreboardBuilder cyturaScoreboardBuilder = reviveMCPlayer.getCyturaScoreboardBuilder();
                cyturaScoreboardBuilder.updateBoard(7, " §8» ", reviveMCPlayer.getSecondColor() + "§l" + reviveMCPlayer.getProperty("coins").getValueAsString());
                cyturaScoreboardBuilder.updateBoard(1, " §8» ", "§a✔ §8| " + reviveMCPlayer.getSecondColor() + revivePassModule.getRevivePassLevel());

                player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du hast dir §aerfolgreich §7den RevivePass gekauft.");
                event.getView().close();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §cAbbrechen")) {
                event.getView().close();
            }
        }

        if (event.getInventory().getName().equalsIgnoreCase("§8» §bShop §8«")) {

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §9BuildFFA §8× §7Shop")) {
                inventoryModule.openBuildFFAShopInventory(1);
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §cBedWars §8× §7Shop")) {
                inventoryModule.openBedWarsShopInventory(1);
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §bRushFight §8× §7Shop")) {
                inventoryModule.openRushFighShopInventory(1);
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eMLGRush §8× §7Shop")) {
                inventoryModule.openMLGRushShopInventory(1);
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §aLobby §8× §7Shop")) {
                inventoryModule.openLobbyShopInventory(1);
            }

        }

        if (event.getInventory().getName().equalsIgnoreCase("§8» §9BuildFFA §8× §7Shop §8«")) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §7Zurück")) {
                inventoryModule.openShopInventory();
            }

        }

        if (event.getInventory().getName().equalsIgnoreCase("§8» §cBedWars §8× §7Shop §8«")) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §7Zurück")) {
                inventoryModule.openShopInventory();
            }

        }

        if (event.getInventory().getName().equalsIgnoreCase("§8» §bRushFight §8× §7Shop §8«")) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §7Zurück")) {
                inventoryModule.openShopInventory();
            }

        }

        if (event.getInventory().getName().equalsIgnoreCase("§8» §eMLGRush §8× §7Shop §8«")) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §7Zurück")) {
                inventoryModule.openShopInventory();
            }

        }

        if (event.getInventory().getName().equalsIgnoreCase("§8» §aLobby §8× §7Shop §8«")) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §7Zurück")) {
                inventoryModule.openShopInventory();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §aGadgets")) {
                inventoryModule.openLobbyShopInventory(2);
            }
        }


        if (event.getInventory().getName().equalsIgnoreCase("§8» §aGadgets §8× §7Auswahl §8«")) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §7Zurück")) {
                inventoryModule.openLobbyShopInventory(1);
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §cRakete")) {
                GameSync gameSync = new GameSync(reviveMCPlayer);
                if (gameSync.getUserSync("lobby.firework.buy").equalsIgnoreCase("false")) {
                    lobbyShopBuyName = "§8» §cRakete §8× §7Kaufen";
                    lobbyShopBuyPrice = 3500;
                    inventoryModule.openLobbyShopBuyInventory(lobbyShopBuyName, lobbyShopBuyPrice);
                } else {
                    if (event.getClick().isLeftClick()) {
                        if (gameSync.getUserSync("lobby.firework.status").equalsIgnoreCase("false")) {
                            gameSync.setUserSync(new String[]{"lobby.firework.status"}, "true");
                            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 2);
                            player.getInventory().setItem(4, new ItemCreator(Material.FIREWORK).setName("§8» §cRakete §8× §7Gadget").setAmount(1).toItemStack());
                            event.getView().close();
                        } else {
                            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 2);
                            return;
                        }

                    }

                    if (event.getClick().isRightClick()) {
                        if (gameSync.getUserSync("lobby.firework.status").equalsIgnoreCase("true")) {
                            gameSync.setUserSync(new String[]{"lobby.firework.status"}, "false");
                            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 2);
                            player.getInventory().clear(2);
                            event.getView().close();
                        } else {
                            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 2);
                            return;
                        }
                    }
                }
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §bLobbyFly")) {
                GameSync gameSync = new GameSync(reviveMCPlayer);
                if (gameSync.getUserSync("lobby.fly.buy").equalsIgnoreCase("false")) {
                    lobbyShopBuyName = "§8» §bLobbyFly §8× §7Kaufen";
                    lobbyShopBuyPrice = 3500;
                    inventoryModule.openLobbyShopBuyInventory(lobbyShopBuyName, lobbyShopBuyPrice);
                } else {
                    if (event.getClick().isLeftClick()) {
                        event.getView().close();
                        player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du hast dieses Gadget bereits erworben.");
                        player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Um dieses Gadget benutzen zu können musst du in die Lobby-Einstellungen gehen.");
                        player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 1, 1);
                    }
                }
            }
        }

        if (event.getInventory().getName().equalsIgnoreCase("§8» §cRakete §8× §7Kaufen")) {
            lobbyShopBuyData = "lobby.firework.buy";
            lobbyShopBuyId = 2;
            lobbyShopBuyPrice = 3500;
            applyLobbyShopData(player, reviveMCPlayer, lobbyShopBuyData, lobbyShopBuyId, lobbyShopBuyPrice, event);
        }

        if (event.getInventory().getName().equalsIgnoreCase("§8» §bLobbyFly §8× §7Kaufen")) {
            lobbyShopBuyData = "lobby.fly.buy";
            lobbyShopBuyId = 2;
            lobbyShopBuyPrice = 5000;
            applyLobbyShopData(player, reviveMCPlayer, lobbyShopBuyData, lobbyShopBuyId, lobbyShopBuyPrice, event);
        }
        


        if (event.getInventory().getName().equalsIgnoreCase("§8» §6Tägliche Belohnung §8«")) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §7Spieler Belohnung")) {
                if (!PlayerCooldown.isInCooldown(player.getUniqueId())) {
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du hast dir deine Tägliche Spieler Belohnung abgeholt.");
                    reviveMCPlayer.setProperty("coins", Integer.parseInt(reviveMCPlayer.getProperty("coins").getValueAsString()) + 150);
                    reviveMCPlayer.update();

                    ReviveMCScoreboardBuilder cyturaScoreboardBuilder = reviveMCPlayer.getCyturaScoreboardBuilder();
                    cyturaScoreboardBuilder.updateBoard(7, " §8» ", reviveMCPlayer.getSecondColor() + "§l" + reviveMCPlayer.getProperty("coins").getValueAsString());
                    PlayerCooldown.setTime(player.getUniqueId(), String.valueOf(System.currentTimeMillis() + 86400000));
                } else {
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du musst noch " + reviveMCPlayer.getSecondColor() + PlayerCooldown.remainingTime(player.getUniqueId()) + " §7warten.");
                    event.getView().close();
                }
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §6Premium Belohnung")) {
                if (!player.hasPermission("lobby.premium.access")) {
                    event.getView().close();
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du benötigst den Premium Rang um dir diese Belohnung abholen zukönnen.");
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Shop: " + reviveMCPlayer.getSecondColor() + "https://shop.cytura.net");
                    return;
                }
                if (!PremiumCooldown.isInCooldown(player.getUniqueId())) {
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du hast dir deine Tägliche Premium Belohnung abgeholt.");
                    reviveMCPlayer.setProperty("coins", Integer.parseInt(reviveMCPlayer.getProperty("coins").getValueAsString()) + 250);
                    reviveMCPlayer.update();

                    ReviveMCScoreboardBuilder cyturaScoreboardBuilder = reviveMCPlayer.getCyturaScoreboardBuilder();
                    cyturaScoreboardBuilder.updateBoard(7, " §8» ", reviveMCPlayer.getSecondColor() + "§l" + reviveMCPlayer.getProperty("coins").getValueAsString());
                    PremiumCooldown.setTime(player.getUniqueId(), String.valueOf(System.currentTimeMillis() + 86400000));
                } else {
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du musst noch " + reviveMCPlayer.getSecondColor() + PremiumCooldown.remainingTime(player.getUniqueId()) + " §7warten.");
                    event.getView().close();
                }
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §bDeluxe Belohnung")) {
                if (!player.hasPermission("lobby.deluxe.access")) {
                    event.getView().close();
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du benötigst den Deluxe Rang um dir diese Belohnung abholen zukönnen.");
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Shop: " + reviveMCPlayer.getSecondColor() + "https://shop.cytura.net");
                    return;
                }

                if (!DeluxeCooldown.isInCooldown(player.getUniqueId())) {
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du hast dir deine Tägliche Deluxe Belohnung abgeholt.");
                    reviveMCPlayer.setProperty("coins", Integer.parseInt(reviveMCPlayer.getProperty("coins").getValueAsString()) + 350);
                    reviveMCPlayer.update();

                    ReviveMCScoreboardBuilder cyturaScoreboardBuilder = reviveMCPlayer.getCyturaScoreboardBuilder();
                    cyturaScoreboardBuilder.updateBoard(7, " §8» ", reviveMCPlayer.getSecondColor() + "§l" + reviveMCPlayer.getProperty("coins").getValueAsString());
                    DeluxeCooldown.setTime(player.getUniqueId(), String.valueOf(System.currentTimeMillis() + 86400000));
                } else {
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du musst noch " + reviveMCPlayer.getSecondColor() + DeluxeCooldown.remainingTime(player.getUniqueId()) + " §7warten.");
                    event.getView().close();
                }
            }
        }

        if (event.getInventory().getName().equalsIgnoreCase("§8» §bEinstellungen §8«")) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» " + reviveMCPlayer.getFirstColor() + "Farbauswahl")) {
                inventoryModule.openColorSettingsInventory();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §cLobby Einstellungen")) {
                inventoryModule.openLobbySettingsInventory();
            }
        }

        if (event.getInventory().getName().equalsIgnoreCase("§8» §bEinstellungen §8× §7Farbauswahl §8«")) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(inventoryModule.getCurrentFirstColor().getItemMeta().getDisplayName())) {
                inventoryModule.openFirstColorSettingsInventory();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(inventoryModule.getCurrentSecondColor().getItemMeta().getDisplayName())) {
                inventoryModule.openSecondColorSettingsInventory();
            }


            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §7Zurück")) {
                inventoryModule.openSettingsInventory();
            }
        }

        if (event.getInventory().getName().equalsIgnoreCase("§8» §bEinstellungen §8× §7Lobby §8«")) {

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §7Zurück")) {
                inventoryModule.openSettingsInventory();
            }
        }

        if (event.getInventory().getName().equalsIgnoreCase("§8» "+ reviveMCPlayer.getFirstColor() +" Farbauswahl §8× §7Hauptfarbe §8«")) {
            final PlayerModule playerModule = new PlayerModule(reviveMCPlayer);

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §a§lGrün")) {
                event.getView().close();
                reviveMCPlayer.setProperty("color", "§a");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §2§lDunkelgrün")) {
                event.getView().close();
                reviveMCPlayer.setProperty("color", "§2");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §e§lGelb")) {
                event.getView().close();
                reviveMCPlayer.setProperty("color", "§e");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §6§lOrange")) {
                event.getView().close();
                reviveMCPlayer.setProperty("color", "§6");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §0§lSchwarz")) {
                event.getView().close();
                reviveMCPlayer.setProperty("color", "§0");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §8§lGrau")) {
                event.getView().close();
                reviveMCPlayer.setProperty("color", "§8");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §5§lLila")) {
                event.getView().close();
                reviveMCPlayer.setProperty("color", "§5");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §c§lRot")) {
                event.getView().close();
                if (!player.hasPermission("lobby.color.full")) {
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du benötigst den Premium Rang oder höher um diese Farbe auswählen zu können.");
                    return;
                }

                reviveMCPlayer.setProperty("color", "§c");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §d§lPink")) {
                event.getView().close();
                if (!player.hasPermission("lobby.color.full")) {
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du benötigst den Premium Rang oder höher um diese Farbe auswählen zu können.");
                    return;
                }

                reviveMCPlayer.setProperty("color", "§d");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §f§lWeiß")) {
                event.getView().close();
                if (!player.hasPermission("lobby.color.full")) {
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du benötigst den Premium Rang oder höher um diese Farbe auswählen zu können.");
                    return;
                }

                reviveMCPlayer.setProperty("color", "§f");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §7§lHellgrau")) {
                event.getView().close();
                if (!player.hasPermission("lobby.color.full")) {
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du benötigst den Premium Rang oder höher um diese Farbe auswählen zu können.");
                    return;
                }

                reviveMCPlayer.setProperty("color", "§7");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §9§lBlau")) {
                event.getView().close();
                if (!player.hasPermission("lobby.color.full")) {
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du benötigst den Premium Rang oder höher um diese Farbe auswählen zu können.");
                    return;
                }

                reviveMCPlayer.setProperty("color", "§9");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §3§lHellblau")) {
                event.getView().close();
                if (!player.hasPermission("lobby.color.full")) {
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du benötigst den Premium Rang oder höher um diese Farbe auswählen zu können.");
                    return;
                }

                reviveMCPlayer.setProperty("color", "§3");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §b§lTürkis")) {
                event.getView().close();
                if (!player.hasPermission("lobby.color.full")) {
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du benötigst den Premium Rang oder höher um diese Farbe auswählen zu können.");
                    return;
                }

                reviveMCPlayer.setProperty("color", "§b");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §7Zurück")) {
                inventoryModule.openColorSettingsInventory();
            }
        }

        if (event.getInventory().getName().equalsIgnoreCase("§8» "+ reviveMCPlayer.getSecondColor() +" Farbauswahl §8× §7Nebenfarbe §8«")) {
            final PlayerModule playerModule = new PlayerModule(reviveMCPlayer);

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §a§lGrün")) {
                event.getView().close();
                reviveMCPlayer.setProperty("colorsub", "§a");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §2§lDunkelgrün")) {
                event.getView().close();
                reviveMCPlayer.setProperty("colorsub", "§2");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §e§lGelb")) {
                event.getView().close();
                reviveMCPlayer.setProperty("colorsub", "§e");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §6§lOrange")) {
                event.getView().close();
                reviveMCPlayer.setProperty("colorsub", "§6");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §0§lSchwarz")) {
                event.getView().close();
                reviveMCPlayer.setProperty("colorsub", "§0");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §8§lGrau")) {
                event.getView().close();
                reviveMCPlayer.setProperty("colorsub", "§8");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §5§lLila")) {
                event.getView().close();
                reviveMCPlayer.setProperty("colorsub", "§5");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §c§lRot")) {
                event.getView().close();
                if (!player.hasPermission("lobby.color.full")) {
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du benötigst den Premium Rang oder höher um diese Farbe auswählen zu können.");
                    return;
                }

                reviveMCPlayer.setProperty("colorsub", "§c");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §d§lPink")) {
                event.getView().close();
                if (!player.hasPermission("lobby.color.full")) {
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du benötigst den Premium Rang oder höher um diese Farbe auswählen zu können.");
                    return;
                }

                reviveMCPlayer.setProperty("colorsub", "§d");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §f§lWeiß")) {
                event.getView().close();
                if (!player.hasPermission("lobby.color.full")) {
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du benötigst den Premium Rang oder höher um diese Farbe auswählen zu können.");
                    return;
                }

                reviveMCPlayer.setProperty("colorsub", "§f");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §7§lHellgrau")) {
                event.getView().close();
                if (!player.hasPermission("lobby.color.full")) {
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du benötigst den Premium Rang oder höher um diese Farbe auswählen zu können.");
                    return;
                }

                reviveMCPlayer.setProperty("colorsub", "§7");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §9§lBlau")) {
                event.getView().close();
                if (!player.hasPermission("lobby.color.full")) {
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du benötigst den Premium Rang oder höher um diese Farbe auswählen zu können.");
                    return;
                }

                reviveMCPlayer.setProperty("colorsub", "§9");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §3§lHellblau")) {
                event.getView().close();
                if (!player.hasPermission("lobby.color.full")) {
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du benötigst den Premium Rang oder höher um diese Farbe auswählen zu können.");
                    return;
                }

                reviveMCPlayer.setProperty("colorsub", "§3");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§ §b§lTürkis")) {
                event.getView().close();
                if (!player.hasPermission("lobby.color.full")) {
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du benötigst den Premium Rang oder höher um diese Farbe auswählen zu können.");
                    return;
                }

                reviveMCPlayer.setProperty("colorsub", "§b");
                playerModule.updateColor();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §7Zurück")) {
                inventoryModule.openColorSettingsInventory();
            }
        }

        if (event.getInventory().getName().equalsIgnoreCase("§8» §eQuickJoin §8«")) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §cBedWars §8«")) {
                inventoryModule.openQuickJumpInventory(1);
                return;
            }


        }

        if (event.getInventory().getName().equalsIgnoreCase("§8» §eQuickJoin §8× §cBedWars §8«")) {
            final ICloudPlayer cloudPlayer = CloudAPI.getInstance().getCloudPlayerManager().getCachedCloudPlayer(player.getUniqueId());
            if (cloudPlayer == null) {
                return;
            }

            final ICloudService service = CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName(event.getCurrentItem().getItemMeta().getDisplayName());
            if (service != null) {
                if (service.isOnline()) {
                    if (service.isActive()) {
                        if (!service.isFull()) {
                            event.getView().close();
                            player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du wirst nun auf dem Server " + reviveMCPlayer.getSecondColor() + service.getName() + " §7verbunden.");
                            cloudPlayer.connect(service);
                            return;
                        } else {

                        }
                    } else {
                        event.getView().close();
                        player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Die Runde hat schon begeonnen.");
                    }
                } else {
                    event.getView().close();
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Der Server startet noch...");
                }
            }
        }

        if (event.getInventory().getName().equalsIgnoreCase("§8» §aLobby §7Server §8«")) {
            final ICloudPlayer cloudPlayer = CloudAPI.getInstance().getCloudPlayerManager().getCachedCloudPlayer(player.getUniqueId());
            if (cloudPlayer == null) {
                return;
            }
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §7Lobby-1")) {
                final ICloudService service = CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName("Lobby-1");
                if (service != null) {
                    if (service.isOnline()) {
                        if (!service.isFull()) {
                            if (!Objects.requireNonNull(cloudPlayer.getConnectedServerName()).equalsIgnoreCase("Lobby-1")) {
                                event.getView().close();
                                cloudPlayer.connect(service);
                                player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du befindest dich nun auf Lobby-1");
                            } else {
                                event.getView().close();
                                player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du befindest dich derzeit auf dieser Lobby.");
                            }
                        } else {
                            event.getView().close();
                            player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Die Lobby ist voll.");
                        }
                    } else {
                        event.getView().close();
                        player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Diese Lobby wird noch gestartet...");
                    }
                }
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §7Lobby-2")) {
                final ICloudService service = CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName("Lobby-2");
                if (service != null) {
                    if (service.isOnline()) {
                        if (!service.isFull()) {
                            if (!Objects.requireNonNull(cloudPlayer.getConnectedServerName()).equalsIgnoreCase("Lobby-2")) {
                                event.getView().close();
                                cloudPlayer.connect(service);
                                player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du befindest dich nun auf Lobby-2");
                            } else {
                                event.getView().close();
                                player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du befindest dich derzeit auf dieser Lobby.");
                            }
                        } else {
                            event.getView().close();
                            player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Die Lobby ist voll.");
                        }
                    } else {
                        event.getView().close();
                        player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Diese Lobby wird noch gestartet...");
                    }
                }
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §7Lobby-3")) {
                final ICloudService service = CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName("Lobby-3");
                if (service != null) {
                    if (service.isOnline()) {
                        if (!service.isFull()) {
                            if (!Objects.requireNonNull(cloudPlayer.getConnectedServerName()).equalsIgnoreCase("Lobby-3")) {
                                event.getView().close();
                                cloudPlayer.connect(service);
                                player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du befindest dich nun auf Lobby-3");
                            } else {
                                event.getView().close();
                                player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du befindest dich derzeit auf dieser Lobby.");
                            }
                        } else {
                            event.getView().close();
                            player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Die Lobby ist voll.");
                        }
                    } else {
                        event.getView().close();
                        player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Diese Lobby wird noch gestartet...");
                    }
                }
            }
        }
    }

    public void applyLobbyShopData(Player player, ReviveMCPlayer reviveMCPlayer, String dataUUID, int siteId, int price, InventoryClickEvent event) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §7Zurück")) {
                new InventoryModule(player).openLobbyShopInventory(siteId);
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §aKaufen §8× §7" + price + " Coins")) {
                if (Integer.parseInt(reviveMCPlayer.getProperty("coins").getValueAsString()) <= price) {
                    player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "§cDu hast nicht ausreichende Coins um dieses Gadget zu kaufen.");
                    player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 1, 1);
                    event.getView().close();
                    return;
                }

                event.getView().close();
                reviveMCPlayer.setProperty("coins", Integer.parseInt(reviveMCPlayer.getProperty("coins").getValueAsString()) - price);
                reviveMCPlayer.update();

                new GameSync(reviveMCPlayer).setUserSync(new String[]{dataUUID}, "true");

                final ReviveMCScoreboardBuilder cyturaScoreboardBuilder = reviveMCPlayer.getCyturaScoreboardBuilder();
                cyturaScoreboardBuilder.updateBoard(7, " §8» ", reviveMCPlayer.getSecondColor() + "§l" + reviveMCPlayer.getProperty("coins").getValueAsString());

                player.sendMessage(Lobby.getInstance().getPrefix(reviveMCPlayer) + "Du hast dir erfolgreich das Gadget für " + reviveMCPlayer.getSecondColor() + price + " §7Coins gekauft.");
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §cAbbrechen")) {
                new InventoryModule(player).openLobbyShopInventory(siteId);
            }
    }
}
