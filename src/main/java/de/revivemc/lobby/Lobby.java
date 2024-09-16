package de.revivemc.lobby;

import de.revivemc.core.playerutils.ReviveMCPlayer;
import de.revivemc.lobby.commands.BuildCommand;
import de.revivemc.lobby.commands.SpawnArmorStandsCommand;
import de.revivemc.lobby.listener.cancel.LobbyCancelListener;
import de.revivemc.lobby.listener.entity.EntityDamageListener;
import de.revivemc.lobby.listener.entity.EntityInteractListener;
import de.revivemc.lobby.listener.entity.EntitySpawnListener;
import de.revivemc.lobby.listener.inventory.InventoryClickListener;
import de.revivemc.lobby.listener.player.*;
import de.revivemc.lobby.listener.world.BlockBreakListener;
import de.revivemc.lobby.listener.world.BlockPlaceListener;
import de.revivemc.lobby.listener.world.WorldWeatherChangeListener;
import de.revivemc.lobby.modules.database.DatabaseDriver;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


public class Lobby extends JavaPlugin {

    private static Lobby instance;
    private DatabaseDriver databaseDriver;

    @Override
    public void onEnable() {
        instance = this;
        databaseDriver = new DatabaseDriver("localhost", "ReviveMC_Cloud", "root", "~aO_8QPm|5S!LNp{?PZt(+Ez%ldr$iY%6My[kjEaYy*D`(4A0FmM1ajku{z402]0");
        databaseDriver.update("CREATE TABLE IF NOT EXISTS cooldown(UUID varchar(64), time varchar(100))");
        databaseDriver.update("CREATE TABLE IF NOT EXISTS premiumcooldown(UUID varchar(64), time varchar(100))");
        databaseDriver.update("CREATE TABLE IF NOT EXISTS deluxecooldown(UUID varchar(64), time varchar(100))");
        databaseDriver.update("CREATE TABLE IF NOT EXISTS revivemc_revivepass(UUID varchar(64), purchaseState varchar(12), revivepasslevel integer(3), pointsToNextLevel integer(255))");
        databaseDriver.update("CREATE TABLE IF NOT EXISTS revivemc_level(UUID varchar(64), level integer(100), pointsToNextLevel integer(255))");
        databaseDriver.update("CREATE TABLE IF NOT EXISTS revivemc_lobby_player(UUID varchar(64), rules varchar(12))");
        databaseDriver.update("CREATE TABLE IF NOT EXISTS revivemc_lobby_build(UUID varchar(64), state varchar(12))");
        initListener();
        initCommands();
    }

    @Override
    public void onDisable() {
        databaseDriver.close();
    }

    public void initListener() {
        Bukkit.getPluginManager().registerEvents(new CyturaPlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new LobbyCancelListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntitySpawnListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new WorldWeatherChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);
    }

    public void initCommands() {
        getCommand("build").setExecutor(new BuildCommand());
        getCommand("armorstands").setExecutor(new SpawnArmorStandsCommand());
    }

    public static Lobby getInstance() {
        return instance;
    }

    public String getPrefix(final ReviveMCPlayer reviveMCPlayer) {
        return "§8» " + reviveMCPlayer.getFirstColor() + "Lobby §8× §7";
    }

    public DatabaseDriver getDatabaseDriver() {
        return databaseDriver;
    }

    public static boolean playerExists(UUID uuid) {
        try {
            ResultSet rs = Lobby.getInstance().getDatabaseDriver().query("SELECT * FROM `revivemc_lobby_player` WHERE  `UUID` = '" + uuid.toString() + "'");
            if (rs.next()) {
                return rs.getString("UUID") != null;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }





    public void insertPlayer(UUID uuid) {
        try {
            if (!playerExists(uuid)) {
                getDatabaseDriver().update("INSERT INTO `revivemc_lobby_player`(`UUID`, `rules`) VALUES ('" + uuid + "' ,'false')");
                getDatabaseDriver().update("INSERT INTO `revivemc_revivepass`(`UUID`, `purchaseState`, `revivepasslevel`, `pointsToNextLevel`) VALUES ('" + uuid + "' ,'false', '0', '1000')");
                getDatabaseDriver().update("INSERT INTO `revivemc_level`(`UUID`, `level`, `pointsToNextLevel`) VALUES ('" + uuid + "' ,'0', '1000')");
                getDatabaseDriver().update("INSERT INTO `cooldown`(`UUID`, `time`) VALUES ('" + uuid + "' ,'0')");
                getDatabaseDriver().update("INSERT INTO `premiumcooldown`(`UUID`, `time`) VALUES ('" + uuid + "' ,'0')");
                getDatabaseDriver().update("INSERT INTO `deluxecooldown`(`UUID`, `time`) VALUES ('" + uuid + "' ,'0')");
            }
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }



}
