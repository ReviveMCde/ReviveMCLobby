package de.revivemc.lobby.commands;

import de.revivemc.core.ReviveMCAPI;
import de.revivemc.core.playerutils.ReviveMCPlayer;
import de.revivemc.lobby.Lobby;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.revivemc.lobby.modules.armorstands.ArmorstandModule;

public class SpawnArmorStandsCommand implements CommandExecutor {



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        final Player player = (Player) sender;
        final ReviveMCPlayer reviveMCPlayer = ReviveMCAPI.getInstance().getCyturaPlayerManager().getPlayers().get(player.getUniqueId());
        final String prefix = Lobby.getInstance().getPrefix(reviveMCPlayer);
        if (!player.hasPermission("lobby.armorstands")) {
            player.sendMessage(prefix + "Du hast keine Rechte auf diesen Befehl.");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(prefix + "Verwende 'armorstands (spawn/reload/remove)'");
            return true;
        }


        switch (args[0]) {
            case "spawn":
                new ArmorstandModule(player).spawnAll();
                player.sendMessage(prefix + "Du hast die ArmorStands platziert.");
                return true;
            case "reload":
                new ArmorstandModule(player).removeAll();
                new ArmorstandModule(player).spawnAll();
                player.sendMessage(prefix + "Du hast die ArmorStands neugeladen.");
                return true;
            case "remove":
                new ArmorstandModule(player).removeAll();
                player.sendMessage(prefix + "Du hast die ArmorStands entfernt.");
                return true;
        }


        return false;
    }
}
