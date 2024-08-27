package de.revivemc.lobby.modules.inventory;

import com.google.common.collect.Lists;
import de.revivemc.core.ReviveMCAPI;
import de.revivemc.core.playerutils.ReviveMCPlayer;
import de.revivemc.lobby.modules.friend.FriendModule;
import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.service.ICloudService;
import de.revivemc.core.entitiesutils.items.ItemCreator;
import de.revivemc.core.gameutils.sync.GameSync;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import de.revivemc.lobby.modules.uuid.*;
import org.omg.CORBA.BAD_INV_ORDER;

import java.util.ArrayList;

public class InventoryModule {

    private final Player player;
    private final ReviveMCPlayer cyturaPlayer;

    public InventoryModule(final Player player) {
        this.player = player;
        this.cyturaPlayer = ReviveMCAPI.getInstance().getCyturaPlayerManager().getPlayers().get(player.getUniqueId());
    }


    public void openNavigatorInventory() {
        final Inventory inventory = Bukkit.createInventory(null, 9 * 5, "§8» §cNavigator §8«");

        setPlaceholder(inventory);
        inventory.setItem(13, new ItemCreator(Material.NETHER_STAR).setName("§8» §aSpawn").setAmount(1).toItemStack());
        inventory.setItem(20, new ItemCreator(Material.BED).setName("§8» §cBedWars").setAmount(1).toItemStack());
        inventory.setItem(19, new ItemCreator(Material.RED_SANDSTONE).setName("§8» §bRushFight").setAmount(1).toItemStack());
        inventory.setItem(24, new ItemCreator(Material.IRON_SWORD).setName("§8» §9BuildFFA").setAmount(1).toItemStack());
        inventory.setItem(25, new ItemCreator(Material.SANDSTONE).setName("§8» §eMLGRush").setAmount(1).toItemStack());

        openInventory(inventory);
    }

    public void openLobbyServerInventory() {
        final Inventory inventory = Bukkit.createInventory(null, 9 * 5, "§8» §aLobby §7Server §8«");

        final ItemStack pane = new ItemCreator(Material.STAINED_GLASS_PANE, (short) 7).setName(" ").setAmount(1).toItemStack();

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, pane);
        }

        for (int i = 36; i < 45; i++) {
            inventory.setItem(i, pane);
        }

        final ArrayList<ICloudService> server = Lists.newArrayList();
        server.addAll(CloudAPI.getInstance().getCloudServiceManager().getCloudServicesByGroupName("Lobby"));
        server.forEach(iCloudService -> {
            if (iCloudService.getName().equalsIgnoreCase("Lobby-1")) {
                inventory.setItem(9, new ItemCreator(Material.BEACON).setName("§8» §7" + iCloudService.getName()).setAmount(iCloudService.getOnlineCount()).toItemStack());
            }

            if (iCloudService.getName().equalsIgnoreCase("Lobby-2")) {
                inventory.setItem(10, new ItemCreator(Material.BEACON).setName("§8» §7" + iCloudService.getName()).setAmount(iCloudService.getOnlineCount()).toItemStack());
            }

            if (iCloudService.getName().equalsIgnoreCase("Lobby-3")) {
                inventory.setItem(11, new ItemCreator(Material.BEACON).setName("§8» §7" + iCloudService.getName()).setAmount(iCloudService.getOnlineCount()).toItemStack());
            }
        });

        openInventory(inventory);
    }

    public void openSettingsInventory() {
        final Inventory inventory = Bukkit.createInventory(null, 9 * 3, "§8» §bEinstellungen §8«");

        setPlaceholder(inventory);
        inventory.setItem(11, new ItemCreator("4f8e6598701eab201a7fc90a800f9e1128b2b09b0781ec230bb56b1552575169").setName("§8» " + cyturaPlayer.getFirstColor() + "Farbauswahl").setAmount(1).toItemStack());
        inventory.setItem(15, new ItemCreator(Material.BEACON).setName("§8» §cLobby Einstellungen").setAmount(1).toItemStack());

        openInventory(inventory);
    }

    public void openLobbySettingsInventory() {
        final Inventory inventory = Bukkit.createInventory(null, 9 * 3, "§8» §bEinstellungen §8× §7Lobby §8«");

        setPlaceholder(inventory);
        addBackToPage(inventory);

        openInventory(inventory);
    }

    public void openColorSettingsInventory() {
        final Inventory inventory = Bukkit.createInventory(null, 9 * 5, "§8» §bEinstellungen §8× §7Farbauswahl §8«");

        setPlaceholder(inventory);
        inventory.setItem(20, new ItemCreator("71bc2bcfb2bd3759e6b1e86fc7a79585e1127dd357fc202893f9de241bc9e530").setName("§7Verändere die Hauptfarbe für deinen Geschmack").setAmount(1).toItemStack());
        inventory.setItem(23, new ItemCreator("4cd9eeee883468881d83848a46bf3012485c23f75753b8fbe8487341419847").setName("§7Verändere die Nebenfarbe für deinen Geschmack").setAmount(1).toItemStack());

        inventory.setItem(21, getCurrentFirstColor());
        inventory.setItem(24, getCurrentSecondColor());

        addBackToPage(inventory);

        openInventory(inventory);
    }

    public void openFirstColorSettingsInventory() {
        final Inventory inventory = Bukkit.createInventory(null, 9 * 4, "§8» "+ cyturaPlayer.getFirstColor() +" Farbauswahl §8× §7Hauptfarbe §8«");

        setPlaceholder(inventory);
        setColorItems(inventory);
        addBackToPage(inventory);

        openInventory(inventory);
    }

    public void openSecondColorSettingsInventory() {
        final Inventory inventory = Bukkit.createInventory(null, 9 * 4, "§8» " + cyturaPlayer.getSecondColor() + " Farbauswahl §8× §7Nebenfarbe §8«");

        setPlaceholder(inventory);
        setColorItems(inventory);
        addBackToPage(inventory);

        openInventory(inventory);
    }

    public void openDailyRewardInventory() {
        final Inventory inventory = Bukkit.createInventory(null, 9 * 3, "§8» §6Tägliche Belohnung §8«");

        setPlaceholder(inventory);

        inventory.setItem(11, new ItemCreator("d5c6dc2bbf51c36cfc7714585a6a5683ef2b14d47d8ff714654a893f5da622").setName("§8» §7Spieler Belohnung").setAmount(1).toItemStack());
        inventory.setItem(13, new ItemCreator("4d9dedfe01d8efd96e7dca2e930d984568c411ba83449c190af0c5ef052f2729").setName("§8» §6Premium Belohnung").setAmount(1).toItemStack());
        inventory.setItem(15, new ItemCreator("1b8fe1c44acbeeb918d38bc42d550bedd5c3dd049889fd9eeea1160ab8b6a").setName("§8» §bDeluxe Belohnung").setAmount(1).toItemStack());

        openInventory(inventory);
    }

    public void openQuickJumpInventory() {
        final Inventory inventory = Bukkit.createInventory(null, 9 * 5, "§8» §eQuickJoin §8«");
        final ItemStack pane = new ItemCreator(Material.STAINED_GLASS_PANE, (short) 7).setName(" ").setAmount(1).toItemStack();

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, pane);
        }

        for (int i = 36; i < 45; i++) {
            inventory.setItem(i, pane);
        }


        final ArrayList<ICloudService> buildffaservers = Lists.newArrayList();
        buildffaservers.addAll(CloudAPI.getInstance().getCloudServiceManager().getCloudServicesByGroupName("BuildFFA"));
        /*ArrayList<ICloudService> mlgrushservers = Lists.newArrayList();
        mlgrushservers.addAll(CloudAPI.getInstance().getCloudServiceManager().getCloudServicesByGroupName("MLGRush"));
        ArrayList<ICloudService> rushfightservers = Lists.newArrayList();
        rushfightservers.addAll(CloudAPI.getInstance().getCloudServiceManager().getCloudServicesByGroupName("RF-2x1"));
        ArrayList<ICloudService> bedwarsserver = Lists.newArrayList();
        bedwarsserver.addAll(CloudAPI.getInstance().getCloudServiceManager().getCloudServicesByGroupName("BW-2x1")); */

        buildffaservers.forEach(buildffa -> {
            inventory.setItem(9, new ItemCreator(Material.IRON_SWORD).setName("§8» §7" + buildffa.getName()).setAmount(buildffa.getOnlineCount()).toItemStack());
        });

        openInventory(inventory);
    }

    public void openShopInventory() {
        final Inventory inventory = Bukkit.createInventory(null, 9 * 5, "§8» §bShop §8«");

        setPlaceholder(inventory);
        inventory.setItem(19, new ItemCreator(Material.IRON_SWORD).setName("§8» §9BuildFFA §8× §7Shop").setAmount(1).setFlags().toItemStack());
        inventory.setItem(20, new ItemCreator(Material.BED).setName("§8» §cBedWars §8× §7Shop").setAmount(1).toItemStack());
        inventory.setItem(24, new ItemCreator(Material.RED_SANDSTONE).setName("§8» §bRushFight §8× §7Shop").setAmount(1).toItemStack());
        inventory.setItem(25, new ItemCreator(Material.SANDSTONE).setName("§8» §eMLGRush §8× §7Shop").setAmount(1).toItemStack());

        inventory.setItem(22, new ItemCreator(Material.BLAZE_POWDER).setName("§8» §aLobby §8× §7Shop").setAmount(1).toItemStack());


        openInventory(inventory);
    }

    public void openBuildFFAShopInventory(final int site) {
        if (site == 1) {
            final Inventory inventory = Bukkit.createInventory(null, 9 * 4, "§8» §9BuildFFA §8× §7Shop §8«");

            setPlaceholder(inventory);

            addBackToPage(inventory);

            openInventory(inventory);
        }
    }

    public void openBedWarsShopInventory(final int site) {
        if (site == 1) {
            final Inventory inventory = Bukkit.createInventory(null, 9 * 4, "§8» §cBedWars §8× §7Shop §8«");

            setPlaceholder(inventory);

            addBackToPage(inventory);

            openInventory(inventory);
        }
    }

    public void openRushFighShopInventory(final int site) {
        if (site == 1) {
            final Inventory inventory = Bukkit.createInventory(null, 9 * 4, "§8» §bRushFight §8× §7Shop §8«");

            setPlaceholder(inventory);

            addBackToPage(inventory);

            openInventory(inventory);
        }
    }

    public void openMLGRushShopInventory(final int site) {
        if (site == 1) {
            final Inventory inventory = Bukkit.createInventory(null, 9 * 4, "§8» §eMLGRush §8× §7Shop §8«");

            setPlaceholder(inventory);


            addBackToPage(inventory);

            openInventory(inventory);
        }
    }

    public void openFriendsInventory() {
        final Inventory inventory = Bukkit.createInventory(null, 9*6, "§8» §9Freunde §8«");


        inventory.setItem(36, new ItemCreator(Material.STAINED_GLASS_PANE, (short) 7).setName(" ").setAmount(1).toItemStack());
        inventory.setItem(37, new ItemCreator(Material.STAINED_GLASS_PANE, (short) 7).setName(" ").setAmount(1).toItemStack());
        inventory.setItem(38, new ItemCreator(Material.STAINED_GLASS_PANE, (short) 7).setName(" ").setAmount(1).toItemStack());
        inventory.setItem(39, new ItemCreator(Material.STAINED_GLASS_PANE, (short) 7).setName(" ").setAmount(1).toItemStack());
        inventory.setItem(40, new ItemCreator(Material.STAINED_GLASS_PANE, (short) 7).setName(" ").setAmount(1).toItemStack());
        inventory.setItem(41, new ItemCreator(Material.STAINED_GLASS_PANE, (short) 7).setName(" ").setAmount(1).toItemStack());
        inventory.setItem(42, new ItemCreator(Material.STAINED_GLASS_PANE, (short) 7).setName(" ").setAmount(1).toItemStack());
        inventory.setItem(43, new ItemCreator(Material.STAINED_GLASS_PANE, (short) 7).setName(" ").setAmount(1).toItemStack());
        inventory.setItem(44, new ItemCreator(Material.STAINED_GLASS_PANE, (short) 7).setName(" ").setAmount(1).toItemStack());

        final FriendModule friendModule = new FriendModule(player.getUniqueId());
        int friendsNumber = friendModule.getFriendsArray().length;
        String[] friends = friendModule.getFriendsArray();
        for (int i = 0; i < friendsNumber;) {
            String currentFriendUUID = friends[i];
            String currentFriendName = NameFetcher.getName(currentFriendUUID);
            inventory.setItem(i, new ItemCreator(Material.SKULL_ITEM, 1, (short) 3).setName("§8» §a" + currentFriendName).setSkullOwner(currentFriendName).setAmount(1).toItemStack());
            i++;
        }

        inventory.setItem(48, new ItemCreator("1f7dadf1063b4d4419ed4a5d900455754988b23418bad8cca2bf7950c3070abf").setName("§8« §7Zurück").setAmount(1).toItemStack());
        inventory.setItem(49, new ItemCreator("d2a6f0e84daefc8b21aa99415b16ed5fdaa6d8dc0c3cd591f49ca832b575").setName("1").setAmount(1).toItemStack());
        inventory.setItem(50, new ItemCreator("70ef38b73e0a44886eb312cff555ed2ee88b624277f8eca47ef44c7a4ed74796").setName("§7Weiter §8»").setAmount(1).toItemStack());
        inventory.setItem(53, new ItemCreator(Material.REDSTONE_COMPARATOR).setName("§8» §7Einstellungen").setAmount(1).setFlags().toItemStack());

        openInventory(inventory);
    }

    public void openLobbyShopInventory(final int site) {
        if (site == 1) {
            final Inventory inventory = Bukkit.createInventory(null, 9 * 6, "§8» §aLobby §8× §7Shop §8«");

            setPlaceholder(inventory);
            inventory.setItem(12, new ItemCreator(Material.ENDER_PEARL).setName("§8» §aGadgets").setAmount(1).toItemStack());
            inventory.setItem(14, new ItemCreator(Material.HARD_CLAY, (short) 11).setName("§8» §dBallons").setAmount(1).toItemStack());
            inventory.setItem(29, new ItemCreator(Material.MONSTER_EGG).setName("§8» §eHaustiere").setAmount(1).toItemStack());
            inventory.setItem(40, new ItemCreator(Material.LEATHER_BOOTS).setLeatherArmorColor(Color.RED).setName("§8» §cSchuhe").setAmount(1).toItemStack());
            inventory.setItem(33, new ItemCreator(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner(player.getName()).setName("§8» §4Köpfe").setAmount(1).toItemStack());

            addBackToPage(inventory);

            openInventory(inventory);
        }

        if (site == 2) {
            final Inventory inventory = Bukkit.createInventory(null, 9 * 3, "§8» §aGadgets §8× §7Auswahl §8«");
            final GameSync gameSync = new GameSync(cyturaPlayer);

            setPlaceholder(inventory);
            if (gameSync.getUserSync("lobby.firework.buy").equalsIgnoreCase("true")) {
                inventory.setItem(11, new ItemCreator(Material.FIREWORK).setName("§8» §cRakete").setLore("§aLinksklick zum Auswählen", "§cRechtsklick zum Ablegen").setAmount(1).toItemStack());
            } else {
                inventory.setItem(11, new ItemCreator(Material.FIREWORK).setName("§8» §cRakete").setLore("§7Preis §8» §a3500 Coins").setAmount(1).toItemStack());
            }

            addBackToPage(inventory);

            openInventory(inventory);
        }
    }

    public void openLobbyShopBuyInventory(String inventoryName, int price) {
        final Inventory inventory = Bukkit.createInventory(null, 9 * 3, inventoryName);

        setPlaceholder(inventory);
        inventory.setItem(11, new ItemCreator("9cb81a35d2b48d5fd81249369433c078b7c8bf42df5aa9c375c1ac85f4514").setName("§8» §aKaufen §8× §7" + price + " Coins").toItemStack());
        inventory.setItem(15, new ItemCreator("f9a51f27d2d938897bc42a3fe2c3135da2671686f57824115f8f8da78a").setName("§8» §cAbbrechen").toItemStack());

        addBackToPage(inventory);

        openInventory(inventory);
    }

    private void openInventory(final Inventory inventory) {
        player.openInventory(inventory);
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
    }

    /*private void setInventoryDesignNew(Inventory inventory) {
        for (int i = 0; i < 9; i++) {
            final ItemStack pane = new ItemCreator(Material.STAINED_GLASS_PANE, (short) 7).setName(" ").setAmount(1).toItemStack();
            inventory.setItem(i, pane);
        }
    } */

    private void setPlaceholder(final Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            final ItemStack pane = new ItemCreator(Material.STAINED_GLASS_PANE, (short) 7).setName(" ").setAmount(1).toItemStack();
            inventory.setItem(i, pane);
        }
    }

    private void addBackToPage(final Inventory inventory) {
        inventory.setItem(inventory.getSize() - 9, new ItemCreator(Material.ARROW).setName("§8» §7Zurück").setAmount(1).toItemStack());
    }

    private void setColorItems(final Inventory inventory) {
        inventory.setItem(10, (new ItemCreator("9cb81a35d2b48d5fd81249369433c078b7c8bf42df5aa9c375c1ac85f4514")).setName("§8§ §a§lGrün").toItemStack());
        inventory.setItem(11, (new ItemCreator("66d53a57685f25f293c7e9c7b4418d452f6931c53a9849a0b3ca109385577")).setName("§8§ §2§lDunkelgrün").toItemStack());
        inventory.setItem(12, (new ItemCreator("f7a9964f572fd03c32dfa2586155fa3d10e627df779a41f262fde82bfb41ba0")).setName("§8§ §e§lGelb").toItemStack());
        inventory.setItem(13, (new ItemCreator("85bec1574de373dfb775ab58b2c1e4621d92c6daac7c6a74d78fb70fff4d0")).setName("§8§ §6§lOrange").toItemStack());
        inventory.setItem(14, (new ItemCreator("2342b9bf9f1f6295842b0efb591697b14451f803a165ae58d0dcebd98eacc")).setName("§8§ §0§lSchwarz").toItemStack());
        inventory.setItem(15, (new ItemCreator("36dc6d42495817b8d4db1311115d1f5cfbede6832192b52f32d48ef7d8b5d")).setName("§8§ §8§lGrau").toItemStack());
        inventory.setItem(16, (new ItemCreator("6c4dc8338415b2b989ab79495986ec7895aa03679d2bcdd6d9ebb633b87fc4")).setName("§8§ §5§lLila").toItemStack());
        inventory.setItem(19, (new ItemCreator("f9a51f27d2d938897bc42a3fe2c3135da2671686f57824115f8f8da78a")).setName("§8§ §c§lRot").toItemStack());
        inventory.setItem(20, (new ItemCreator("df387b69f94feea25671fc399eb75dbeac60f512a4bfe78e454171ee3fc0")).setName("§8§ §d§lPink").toItemStack());
        inventory.setItem(21, (new ItemCreator("af3eaf4c15ad67c51dff9097bd7abd4a82bab7bed83ab77a6177f2e57b")).setName("§8§ §f§lWeiß").toItemStack());
        inventory.setItem(22, (new ItemCreator("1a4b0eaf5269a34e278cae8bc5c6cb11202344a94bd8f4a79ae6b812cd6cff")).setName("§8§ §7§lHellgrau").toItemStack());
        inventory.setItem(23, (new ItemCreator("1da76d6ddcdbb4973332155796c030cfa2fe855ce539e813e588c61d4d6bca5c")).setName("§8§ §9§lBlau").toItemStack());
        inventory.setItem(24, (new ItemCreator("481899d07ed8a5441bb3faf1e19dcc97537cbdf91c703ebf85ac31c88198fc")).setName("§8§ §3§lHellblau").toItemStack());
        inventory.setItem(25, (new ItemCreator("29e4a399befd967ec282c719fbfd644863281374dcb8137a72f7c4d013a73f")).setName("§8§ §b§lTürkis").toItemStack());
    }


    public final ItemStack getCurrentFirstColor() {
        if (cyturaPlayer.getFirstColor().equalsIgnoreCase("§a")) {
            return (new ItemCreator("9cb81a35d2b48d5fd81249369433c078b7c8bf42df5aa9c375c1ac85f4514")).setName("§8§ §a§lGrün").toItemStack();
        } else if (cyturaPlayer.getFirstColor().equalsIgnoreCase("§2")) {
            return (new ItemCreator("66d53a57685f25f293c7e9c7b4418d452f6931c53a9849a0b3ca109385577")).setName("§8§ §2§lDunkelgrün").toItemStack();
        } else if (cyturaPlayer.getFirstColor().equalsIgnoreCase("§e")) {
            return (new ItemCreator("f7a9964f572fd03c32dfa2586155fa3d10e627df779a41f262fde82bfb41ba0")).setName("§8§ §e§lGelb").toItemStack();
        } else if (cyturaPlayer.getFirstColor().equalsIgnoreCase("§6")) {
            return (new ItemCreator("85bec1574de373dfb775ab58b2c1e4621d92c6daac7c6a74d78fb70fff4d0")).setName("§8§ §6§lOrange").toItemStack();
        } else if (cyturaPlayer.getFirstColor().equalsIgnoreCase("§0")) {
            return (new ItemCreator("2342b9bf9f1f6295842b0efb591697b14451f803a165ae58d0dcebd98eacc")).setName("§8§ §0§lSchwarz").toItemStack();
        } else if (cyturaPlayer.getFirstColor().equalsIgnoreCase("§8")) {
            return (new ItemCreator("36dc6d42495817b8d4db1311115d1f5cfbede6832192b52f32d48ef7d8b5d")).setName("§8§ §8§lGrau").toItemStack();
        } else if (cyturaPlayer.getFirstColor().equalsIgnoreCase("§5")) {
            return (new ItemCreator("6c4dc8338415b2b989ab79495986ec7895aa03679d2bcdd6d9ebb633b87fc4")).setName("§8§ §5§lLila").toItemStack();
        } else if (cyturaPlayer.getFirstColor().equalsIgnoreCase("§c")) {
            return (new ItemCreator("f9a51f27d2d938897bc42a3fe2c3135da2671686f57824115f8f8da78a")).setName("§8§ §c§lRot").toItemStack();
        } else if (cyturaPlayer.getFirstColor().equalsIgnoreCase("§d")) {
            return (new ItemCreator("df387b69f94feea25671fc399eb75dbeac60f512a4bfe78e454171ee3fc0")).setName("§8§ §d§lPink").toItemStack();
        } else if (cyturaPlayer.getFirstColor().equalsIgnoreCase("§f")) {
            return (new ItemCreator("af3eaf4c15ad67c51dff9097bd7abd4a82bab7bed83ab77a6177f2e57b")).setName("§8§ §f§lWeiß").toItemStack();
        } else if (cyturaPlayer.getFirstColor().equalsIgnoreCase("§7")) {
            return (new ItemCreator("1a4b0eaf5269a34e278cae8bc5c6cb11202344a94bd8f4a79ae6b812cd6cff")).setName("§8§ §7§lHellgrau").toItemStack();
        } else if (cyturaPlayer.getFirstColor().equalsIgnoreCase("§9")) {
            return (new ItemCreator("1da76d6ddcdbb4973332155796c030cfa2fe855ce539e813e588c61d4d6bca5c")).setName("§8§ §9§lBlau").toItemStack();
        } else if (cyturaPlayer.getFirstColor().equalsIgnoreCase("§3")) {
            return (new ItemCreator("481899d07ed8a5441bb3faf1e19dcc97537cbdf91c703ebf85ac31c88198fc")).setName("§8§ §3§lHellblau").toItemStack();
        } else if (cyturaPlayer.getFirstColor().equalsIgnoreCase("§b")) {
            return (new ItemCreator("29e4a399befd967ec282c719fbfd644863281374dcb8137a72f7c4d013a73f")).setName("§8§ §b§lTürkis").toItemStack();
        } else {
            return null;
        }
    }

    public ItemStack getCurrentSecondColor() {
        if (cyturaPlayer.getSecondColor().equalsIgnoreCase("§a")) {
            return (new ItemCreator("9cb81a35d2b48d5fd81249369433c078b7c8bf42df5aa9c375c1ac85f4514")).setName("§8§ §a§lGrün").toItemStack();
        } else if (cyturaPlayer.getSecondColor().equalsIgnoreCase("§2")) {
            return (new ItemCreator("66d53a57685f25f293c7e9c7b4418d452f6931c53a9849a0b3ca109385577")).setName("§8§ §2§lDunkelgrün").toItemStack();
        } else if (cyturaPlayer.getSecondColor().equalsIgnoreCase("§e")) {
            return (new ItemCreator("f7a9964f572fd03c32dfa2586155fa3d10e627df779a41f262fde82bfb41ba0")).setName("§8§ §e§lGelb").toItemStack();
        } else if (cyturaPlayer.getSecondColor().equalsIgnoreCase("§6")) {
            return (new ItemCreator("85bec1574de373dfb775ab58b2c1e4621d92c6daac7c6a74d78fb70fff4d0")).setName("§8§ §6§lOrange").toItemStack();
        } else if (cyturaPlayer.getSecondColor().equalsIgnoreCase("§0")) {
            return (new ItemCreator("2342b9bf9f1f6295842b0efb591697b14451f803a165ae58d0dcebd98eacc")).setName("§8§ §0§lSchwarz").toItemStack();
        } else if (cyturaPlayer.getSecondColor().equalsIgnoreCase("§8")) {
            return (new ItemCreator("36dc6d42495817b8d4db1311115d1f5cfbede6832192b52f32d48ef7d8b5d")).setName("§8§ §8§lGrau").toItemStack();
        } else if (cyturaPlayer.getSecondColor().equalsIgnoreCase("§5")) {
            return (new ItemCreator("6c4dc8338415b2b989ab79495986ec7895aa03679d2bcdd6d9ebb633b87fc4")).setName("§8§ §5§lLila").toItemStack();
        } else if (cyturaPlayer.getSecondColor().equalsIgnoreCase("§c")) {
            return (new ItemCreator("f9a51f27d2d938897bc42a3fe2c3135da2671686f57824115f8f8da78a")).setName("§8§ §c§lRot").toItemStack();
        } else if (cyturaPlayer.getSecondColor().equalsIgnoreCase("§d")) {
            return (new ItemCreator("df387b69f94feea25671fc399eb75dbeac60f512a4bfe78e454171ee3fc0")).setName("§8§ §d§lPink").toItemStack();
        } else if (cyturaPlayer.getSecondColor().equalsIgnoreCase("§f")) {
            return (new ItemCreator("af3eaf4c15ad67c51dff9097bd7abd4a82bab7bed83ab77a6177f2e57b")).setName("§8§ §f§lWeiß").toItemStack();
        } else if (cyturaPlayer.getSecondColor().equalsIgnoreCase("§7")) {
            return (new ItemCreator("1a4b0eaf5269a34e278cae8bc5c6cb11202344a94bd8f4a79ae6b812cd6cff")).setName("§8§ §7§lHellgrau").toItemStack();
        } else if (cyturaPlayer.getSecondColor().equalsIgnoreCase("§9")) {
            return (new ItemCreator("1da76d6ddcdbb4973332155796c030cfa2fe855ce539e813e588c61d4d6bca5c")).setName("§8§ §9§lBlau").toItemStack();
        } else if (cyturaPlayer.getSecondColor().equalsIgnoreCase("§3")) {
            return (new ItemCreator("481899d07ed8a5441bb3faf1e19dcc97537cbdf91c703ebf85ac31c88198fc")).setName("§8§ §3§lHellblau").toItemStack();
        } else if (cyturaPlayer.getSecondColor().equalsIgnoreCase("§b")) {
            return (new ItemCreator("29e4a399befd967ec282c719fbfd644863281374dcb8137a72f7c4d013a73f")).setName("§8§ §b§lTürkis").toItemStack();
        } else {
            return null;
        }
    }
}
