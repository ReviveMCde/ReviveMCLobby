package de.revivemc.lobby.modules.revivepass;

import de.revivemc.lobby.Lobby;
import de.revivemc.lobby.modules.database.DatabaseDriver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RevivePassModule {

    private final DatabaseDriver databaseDriver = Lobby.getInstance().getDatabaseDriver();
    private final UUID uuid;

    public RevivePassModule(final UUID  uuid) {
        this.uuid = uuid;
    }

    public void setPurchaseState(final String state) {
        this.databaseDriver.update("UPDATE `revivemc_revivepass` SET `purchaseState`='" + state + "' WHERE UUID='" + this.uuid + "'");
    }

    public void setRevivePassLevel(final int level) {
        this.databaseDriver.update("UPDATE `revivemc_revivepass` SET `revivepasslevel`='" + level + "' WHERE UUID='" + this.uuid + "'");
    }

    public void setPointsToNextLevel(final int pointsToNextLevel) {
        this.databaseDriver.update("UPDATE `revivemc_revivepass` SET `pointsToNextLevel`='" + pointsToNextLevel + "' WHERE UUID='" + this.uuid + "'");
    }

    public String getPurchaseState() {
        try {
            ResultSet resultSet = Lobby.getInstance().getDatabaseDriver().query("SELECT * FROM `revivemc_revivepass` WHERE  `UUID` = '" + this.uuid.toString() + "'");
            if (resultSet.next()) {
                return resultSet.getString("purchaseState");
            }
        }catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public int getRevivePassLevel() {
        try {
            ResultSet resultSet = Lobby.getInstance().getDatabaseDriver().query("SELECT * FROM `revivemc_revivepass` WHERE  `UUID` = '" + this.uuid.toString() + "'");
            if (resultSet.next()) {
                return resultSet.getInt("revivepasslevel");
            }
        }catch (SQLException exception) {
            exception.printStackTrace();
        }
        return -1;
    }

    public int getPlayerPointsToNextLevel() {
        try {
            ResultSet resultSet = Lobby.getInstance().getDatabaseDriver().query("SELECT * FROM `revivemc_revivepass` WHERE  `UUID` = '" + this.uuid.toString() + "'");
            if (resultSet.next()) {
                return resultSet.getInt("pointsToNextLevel");
            }
        }catch (SQLException exception) {
            exception.printStackTrace();
        }
        return -1;
    }
}
