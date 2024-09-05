package de.revivemc.lobby.listener.player;

import de.revivemc.core.ReviveMCAPI;
import de.revivemc.core.gameutils.coins.CoinsUtils;
import de.revivemc.core.gameutils.sync.GameSync;
import de.revivemc.core.playerutils.ReviveMCPlayer;
import de.revivemc.core.playerutils.events.ReviveMCPlayerJoinEvent;
import de.revivemc.lobby.Lobby;
import de.revivemc.lobby.modules.armorstands.ArmorstandModule;
import de.revivemc.lobby.modules.lobby.LobbyModule;
import de.revivemc.lobby.modules.location.LocationModule;
import de.revivemc.lobby.modules.player.PlayerModule;
import de.revivemc.lobby.modules.scoreboard.ScoreboardModule;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

public class CyturaPlayerJoinListener implements Listener {

    @EventHandler
    public void onCyturaPlayerJoin(ReviveMCPlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final ReviveMCPlayer reviveMCPlayer = ReviveMCAPI.getInstance().getCyturaPlayerManager().getPlayers().get(player.getUniqueId());
        player.teleport(new LocationModule(player.getLocation()).loadSpawnLocation());
        player.getInventory().clear();
        player.setFoodLevel(20);
        if (player.hasPotionEffect(PotionEffectType.BLINDNESS)) {
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.removePotionEffect(PotionEffectType.SLOW);
        }

        Lobby.getInstance().insertPlayer(player.getUniqueId());
        final String prefix = Lobby.getInstance().getPrefix(reviveMCPlayer);
        if (Objects.equals(hasPlayerRulesAccepted(player.getUniqueId()), "false")) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 999, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 999, false, false));
            return;
        }

        reviveMCPlayer.setTablist();
        new LobbyModule(reviveMCPlayer);
        new PlayerModule(player).setDefaultInventory(reviveMCPlayer);
        new ScoreboardModule().buildScoreboard(reviveMCPlayer);
        player.teleport(new LocationModule(player.getLocation()).loadSpawnLocation());

    }

    public static String hasPlayerRulesAccepted(UUID uuid) {
        try {
            ResultSet resultSet = Lobby.getInstance().getDatabaseDriver().query("SELECT * FROM `revivemc_lobby_player` WHERE  `UUID` = '" + uuid.toString() + "'");
            if (resultSet.next()) {
                return resultSet.getString("rules");
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addDefaultProperty(ReviveMCPlayer cyturaPlayer) {
        cyturaPlayer.addProperty("coins", 500);
        cyturaPlayer.addProperty("color", "§9");
        cyturaPlayer.addProperty("colorsub", "§b");
    }

}
