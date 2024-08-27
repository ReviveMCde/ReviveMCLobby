package de.revivemc.lobby.modules.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationModule {

    private Location location;

    public LocationModule(final Location location) {
        this.location = location;
    }

    public Location loadSpawnLocation() {
        location = new Location(Bukkit.getWorld("world"), 0.5D, 45.0D, 0.5D, 180.0F, 1.1F);
        return location;
    }

    public Location loadShopArmorstandLocation() {
        location = new Location(Bukkit.getWorld("world"), -10.45D, 44.0D, -10.47D, -45.2F, 2.2F);
        return location;
    }

    public Location loadDailyArmorstandLocation() {
        location = new Location(Bukkit.getWorld("world"), 11.60D, 44.0D, -10.59D, 45.4F, 2.2F);
        return location;
    }

    public Location loadLevelArmorStandLocation() {
        location = new Location(Bukkit.getWorld("world"), 16.51D, 44.0D, 0.49D, 89.7F, 1.1F);
        return location;
    }

    public Location loadQuestsArmorStandLocation() {
        location = new Location(Bukkit.getWorld("world"), -15.68D, 44.0D, 0.52D, -90.1F, 2.0F);
        return location;
    }

    public Location loadRevivePassArmorStandLocation() {
        location = new Location(Bukkit.getWorld("world"), -10.48D, 44.0D, 11.51D, -135.3F, 2.2F);
        return location;
    }

    public Location loadBedWarsLocation() {
        return location = new Location(Bukkit.getWorld("world"), -252.49D, 47.0D, 240.49D, 89.6F, 2.0F);
    }

    public Location loadBuildFFALocation() {
        return location = new Location(Bukkit.getWorld("world"), 89.48D, 46.0D, -40.49D, -90.4F,2.1F);
    }
}
