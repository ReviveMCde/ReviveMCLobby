package de.revivemc.lobby.modules.scoreboard;

import de.revivemc.core.ReviveMCAPI;
import de.revivemc.core.playerutils.ReviveMCPlayer;
import de.revivemc.core.playerutils.scoreboard.ReviveMCScoreboardBuilder;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;

public class ScoreboardModule {

    public void buildScoreboard(final ReviveMCPlayer cyturaPlayer) {
        cyturaPlayer.initialScoreboard();
        final ReviveMCScoreboardBuilder cyturaScoreboardBuilder = cyturaPlayer.getCyturaScoreboardBuilder();
        cyturaScoreboardBuilder.setLine(10, "§8§m---------", "§8§m---------");
        cyturaScoreboardBuilder.setLine(9, " ", " ");
        cyturaScoreboardBuilder.setLine(8, " §8§l» ", "§7Rang");
        cyturaScoreboardBuilder.setLine(7, " §8» ", cyturaPlayer.getPermissionGroupColor() + cyturaPlayer.getPermissionGroup().getName());
        cyturaScoreboardBuilder.setLine(6, " ", " ");
        cyturaScoreboardBuilder.setLine(5, " §8§l» ", "§7Coins");
        cyturaScoreboardBuilder.setLine(4, " §8» ", cyturaPlayer.getSecondColor() + "§l" + cyturaPlayer.getProperty("coins").getValueAsString());
        cyturaScoreboardBuilder.setLine(3, " ", " ");
        cyturaScoreboardBuilder.setLine(2, " §8§l» ", "§7Discord");
        cyturaScoreboardBuilder.setLine(1, " §8» ", cyturaPlayer.getSecondColor() + "discord.gg/");
        cyturaScoreboardBuilder.setLine(0, " ", " ");
        MinecraftServer.getServer().postToMainThread(() -> {
            cyturaScoreboardBuilder.setBoard(cyturaPlayer.getFirstColor() + "§lReviveMC §8× §7Lobby");
            Bukkit.getOnlinePlayers().forEach(player -> {
                ReviveMCAPI.getInstance().getCyturaTablistManager().setDefaultTablist(ReviveMCAPI.getInstance().getCyturaPlayerManager().getPlayers().get(player.getUniqueId()));
            });
        });
    }
}
