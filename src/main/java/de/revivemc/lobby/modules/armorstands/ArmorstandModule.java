package de.revivemc.lobby.modules.armorstands;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import de.revivemc.core.ReviveMCAPI;
import de.revivemc.lobby.modules.location.LocationModule;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class ArmorstandModule {

    private ArmorStand armorStand;
    private ArmorStand dailyReward;
    private ArmorStand questArmorStand;
    private ArmorStand levelArmorStand;
    private ArmorStand battlePassArmorStand;
    private final Player player;

    public ArmorstandModule(Player player) {
        this.player = player;
    }

    public void spawnAll() {
        this.armorStand = (ArmorStand) player.getWorld().spawnEntity(new LocationModule(player.getLocation()).loadShopArmorstandLocation(), EntityType.ARMOR_STAND);
        this.armorStand.setHelmet(ReviveMCAPI.getInstance().getItemHandler().createNewItemCreator("9d1c686bad64e8993fd0dc56aec00d2d3a1863d0133943151083392e3ff04cf1").toItemStack());
        this.armorStand.setCustomName("§8» §bShop §8«");
        this.armorStand.setCustomNameVisible(true);
        this.armorStand.setVisible(false);


        this.dailyReward = (ArmorStand) player.getWorld().spawnEntity(new LocationModule(player.getLocation()).loadDailyArmorstandLocation(), EntityType.ARMOR_STAND);
        this.dailyReward.setHelmet(ReviveMCAPI.getInstance().getItemHandler().createNewItemCreator("bf257726937ba1814096209eb7b5168c66552d25e41b1e514aa6d31c4d3aa6dc").toItemStack());
        this.dailyReward.setCustomName("§8» §6Tägliche Belohnung §8«");
        this.dailyReward.setCustomNameVisible(true);
        this.dailyReward.setVisible(false);

        this.questArmorStand = (ArmorStand) player.getWorld().spawnEntity(new LocationModule(player.getLocation()).loadQuestsArmorStandLocation(), EntityType.ARMOR_STAND);
        this.questArmorStand.setHelmet(ReviveMCAPI.getInstance().getItemHandler().createNewItemCreator("9e5bb8b31f46aa9af1baa88b74f0ff383518cd23faac52a3acb96cfe91e22ebc").toItemStack());
        this.questArmorStand.setCustomName("§8» §eDeine Quests §8«");
        this.questArmorStand.setCustomNameVisible(true);
        this.questArmorStand.setVisible(false);

        this.levelArmorStand = (ArmorStand) player.getWorld().spawnEntity(new LocationModule(player.getLocation()).loadLevelArmorStandLocation(), EntityType.ARMOR_STAND);
        this.levelArmorStand.setHelmet(ReviveMCAPI.getInstance().getItemHandler().createNewItemCreator("399ad7a0431692994b6c412c7eafb9e0fc49975240b73a27d24ed797035fb894").toItemStack());
        this.levelArmorStand.setCustomName("§8» §aDein Level §8«");
        this.levelArmorStand.setCustomNameVisible(true);
        this.levelArmorStand.setVisible(false);

        this.battlePassArmorStand = (ArmorStand) player.getWorld().spawnEntity(new LocationModule(player.getLocation()).loadRevivePassArmorStandLocation(), EntityType.ARMOR_STAND);
        this.battlePassArmorStand.setHelmet(ReviveMCAPI.getInstance().getItemHandler().createNewItemCreator("a988419dd5b386f698a96913db1d97c2418e16d416d7f439d48acd41e3a436ce").toItemStack());
        this.battlePassArmorStand.setCustomName("§8» §9RevivePass §8«");
        this.battlePassArmorStand.setCustomNameVisible(true);
        this.battlePassArmorStand.setVisible(false);
    }

    public void removeAll() {
        player.getWorld().getEntities().clear();
    }

    public ArmorStand getArmorStand() {
        return this.armorStand;
    }

    public ArmorStand getDailyReward() {
        return this.dailyReward;
    }
}
