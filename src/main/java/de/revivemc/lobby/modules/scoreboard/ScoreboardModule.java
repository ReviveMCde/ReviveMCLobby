package de.revivemc.lobby.modules.scoreboard;

import de.revivemc.core.ReviveMCAPI;
import de.revivemc.core.playerutils.ReviveMCPlayer;
import de.revivemc.core.playerutils.scoreboard.ReviveMCScoreboardBuilder;
import de.revivemc.lobby.modules.level.LevelModule;
import de.revivemc.lobby.modules.revivepass.RevivePassModule;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;

public class ScoreboardModule {

    public void buildScoreboard(final ReviveMCPlayer cyturaPlayer) {
        cyturaPlayer.initialScoreboard();
        final ReviveMCScoreboardBuilder cyturaScoreboardBuilder = cyturaPlayer.getCyturaScoreboardBuilder();
        final LevelModule levelModule = new LevelModule(cyturaPlayer.getUuid());
        final RevivePassModule revivePassModule = new RevivePassModule(cyturaPlayer.getUuid());
        cyturaScoreboardBuilder.setLine(13, "§8§m---------", "§8§m---------");
        cyturaScoreboardBuilder.setLine(12, " ", " ");
        cyturaScoreboardBuilder.setLine(11, " §8§l» ", "§7Rang");
        if (cyturaPlayer.getPermissionGroup().getName().equalsIgnoreCase("Admin")) {
            cyturaScoreboardBuilder.setLine(10, " §8» ", cyturaPlayer.getPermissionGroupColor() + "Administrator");
        } else {
            cyturaScoreboardBuilder.setLine(10, " §8» ", cyturaPlayer.getPermissionGroupColor() + cyturaPlayer.getPermissionGroup().getName());
        }
        cyturaScoreboardBuilder.setLine(9, " ", " ");
        cyturaScoreboardBuilder.setLine(8, " §8§l» ", "§7Coins");
        cyturaScoreboardBuilder.setLine(7, " §8» ", cyturaPlayer.getSecondColor() + "§l" + cyturaPlayer.getProperty("coins").getValueAsString());
        cyturaScoreboardBuilder.setLine(6, " ", " ");
        cyturaScoreboardBuilder.setLine(5, " §8§l» ", "§7Level");
        cyturaScoreboardBuilder.setLine(4, " §8» ", cyturaPlayer.getSecondColor() + levelModule.getPlayerLevel());
        cyturaScoreboardBuilder.setLine(3, " ", " ");
        cyturaScoreboardBuilder.setLine(2, " §8§l» ", "§7RevivePass");
        if (revivePassModule.getPurchaseState().equalsIgnoreCase("false")) {
            cyturaScoreboardBuilder.setLine(1, " §8» ", "§c✘");
        } else {
            cyturaScoreboardBuilder.setLine(1, " §8» ", "§a✔ §8| " + cyturaPlayer.getSecondColor() + revivePassModule.getRevivePassLevel());
        }
        cyturaScoreboardBuilder.setLine(0, " ", " ");
        MinecraftServer.getServer().postToMainThread(() -> {
            cyturaScoreboardBuilder.setBoard(cyturaPlayer.getFirstColor() + "§lReviveMC §8× §7Lobby");
            Bukkit.getOnlinePlayers().forEach(player -> {
                ReviveMCAPI.getInstance().getCyturaTablistManager().setDefaultTablist(ReviveMCAPI.getInstance().getCyturaPlayerManager().getPlayers().get(player.getUniqueId()));
            });
        });
    }
}
