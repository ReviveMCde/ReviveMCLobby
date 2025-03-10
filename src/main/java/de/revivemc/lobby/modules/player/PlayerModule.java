package de.revivemc.lobby.modules.player;

import de.revivemc.core.entitiesutils.items.ItemCreator;
import de.revivemc.core.gameutils.sync.GameSync;
import de.revivemc.core.playerutils.ReviveMCPlayer;
import de.revivemc.lobby.modules.nick.NickModule;
import de.revivemc.lobby.modules.scoreboard.ScoreboardModule;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class PlayerModule {

    private Player player;
    private ReviveMCPlayer cyturaPlayer;

    public PlayerModule(final Player player) {
        this.player = player;
    }

    public PlayerModule(final ReviveMCPlayer cyturaPlayer) {
        this.cyturaPlayer = cyturaPlayer;
    }

    public void setDefaultInventory(ReviveMCPlayer cyturaPlayer) {
        final GameSync gameSync = new GameSync(cyturaPlayer);
        player.getInventory().clear();
        player.getInventory().setItem(0, new ItemCreator(Material.COMPASS).setName("§8» §c§lNavigator §8× §7Rechtsklick").setAmount(1).toItemStack());
        player.getInventory().setItem(1, new ItemCreator(Material.REDSTONE_COMPARATOR).setName("§8» §b§lEinstellungen §8× §7Rechtsklick").setAmount(1).toItemStack());
        if (player.hasPermission("lobby.silentlobby")) {
            player.getInventory().setItem(2, new ItemCreator(Material.TNT).setName("§8» §4§lSilent-Lobby §8× §7Rechtsklick").setAmount(1).setFlags().toItemStack());
        }


        if (gameSync.getUserSync("lobby.firework.status").equalsIgnoreCase("true")) {
            player.getInventory().setItem(4, new ItemCreator(Material.FIREWORK).setName("§8» §cRakete §8× §7Gadget").setAmount(1).toItemStack());
        }
        //Verschoben in Villager QuickJoin Menü
        //player.getInventory().setItem(4, new ItemCreator(Material.FIREWORK).addEnchant(Enchantment.THORNS, 1).setFlags().setName("§8» §e§lQuickJoin §8× §7Rechtsklick").setAmount(1).toItemStack());
        if (player.hasPermission("lobby.nick")) {
            final NickModule nickModule = new NickModule(this.player.getUniqueId());
            if (nickModule.getNickState().equalsIgnoreCase("false")) {
                player.getInventory().setItem(6, new ItemCreator(Material.NAME_TAG).setName("§8» §5§lNick §7System §8× §c§lDeaktiviert").setAmount(1).toItemStack());
            } else if (nickModule.getNickState().equalsIgnoreCase("true")) {
                player.getInventory().setItem(6, new ItemCreator(Material.NAME_TAG).setName("§8» §5§lNick §7System §8× §a§lAktiviert").setAmount(1).toItemStack());
            }

        }
        player.getInventory().setItem(7, new ItemCreator(Material.BEACON).setName("§8» §a§lLobby §7Server §8× §7Rechtsklick").setAmount(1).toItemStack());
        player.getInventory().setItem(8, new ItemCreator(Material.SKULL_ITEM, 1, (short) 3).setName("§8» §9§lFreunde §8× §7Rechtsklick").setAmount(1).setSkullOwner(player.getName()).toItemStack());
    }

    public void updateColor() {
        cyturaPlayer.update();
        new ScoreboardModule().buildScoreboard(cyturaPlayer);
        this.setDefaultInventory(cyturaPlayer);
    }

}
