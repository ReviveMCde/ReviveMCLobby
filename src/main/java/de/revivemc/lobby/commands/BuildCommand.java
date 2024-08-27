package de.revivemc.lobby.commands;

import de.revivemc.core.playerutils.ReviveMCPlayer;
import de.revivemc.core.ReviveMCAPI;
import de.revivemc.lobby.Lobby;
import de.revivemc.lobby.modules.build.BuildModule;
import de.revivemc.lobby.modules.player.PlayerModule;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class BuildCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        final ReviveMCPlayer reviveMCPlayer = ReviveMCAPI.getInstance().getCyturaPlayerManager().getPlayers().get(player.getUniqueId());
        final String prefix = Lobby.getInstance().getPrefix(reviveMCPlayer);
        if (!player.hasPermission("lobby.build")) {
            player.sendMessage(prefix + "Du hast keine Rechte auf diesen Befehl.");
            return true;
        }

        if (args.length > 1) {
            player.sendMessage(prefix + "Verwende: 'build'");
            return true;
        }

        final BuildModule buildModule = new BuildModule(player.getUniqueId());

        if (!buildModule.playerExists()) {
            buildModule.insertIntoDatabase();
        }

        if (!buildModule.getBuildModeState()) {
            player.getInventory().clear();
            player.setGameMode(GameMode.CREATIVE);
            buildModule.setBuildModeState(true);
            player.sendMessage(prefix + "§cDu wurdest in den Bau-Modus versetzt.");
            return true;
        }

        player.setGameMode(GameMode.SURVIVAL);
        new PlayerModule(player).setDefaultInventory(reviveMCPlayer);
        buildModule.setBuildModeState(false);
        player.sendMessage(prefix + "§cDu wurdest aus den Bau-Modus versetzt.");
        return false;
    }
}
