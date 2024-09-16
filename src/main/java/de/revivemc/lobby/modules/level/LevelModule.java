package de.revivemc.lobby.modules.level;

import de.revivemc.lobby.Lobby;
import de.revivemc.lobby.modules.database.DatabaseDriver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class LevelModule {

    private final DatabaseDriver databaseDriver = Lobby.getInstance().getDatabaseDriver();
    private final UUID uuid;

    public LevelModule(final UUID  uuid) {
        this.uuid = uuid;
    }

    public void setLevel(final int level) {
        this.databaseDriver.update("UPDATE `revivemc_level` SET `level`='" + level + "' WHERE UUID='" + this.uuid + "'");
    }

    public void setPointsToNextLevel(final int pointsToNextLevel) {
        this.databaseDriver.update("UPDATE `revivemc_level` SET `pointsToNextLevel`='" + pointsToNextLevel + "' WHERE UUID='" + this.uuid + "'");
    }

    public int getPlayerLevel() {
        try {
            ResultSet resultSet = Lobby.getInstance().getDatabaseDriver().query("SELECT * FROM `revivemc_level` WHERE  `UUID` = '" + this.uuid.toString() + "'");
            if (resultSet.next()) {
                return resultSet.getInt("level");
            }
        }catch (SQLException exception) {
            exception.printStackTrace();
        }
        return -1;
    }

    public int getPlayerPointsToNextLevel() {
        try {
            ResultSet resultSet = Lobby.getInstance().getDatabaseDriver().query("SELECT * FROM `revivemc_level` WHERE  `UUID` = '" + this.uuid.toString() + "'");
            if (resultSet.next()) {
                return resultSet.getInt("pointsToNextLevel");
            }
        }catch (SQLException exception) {
            exception.printStackTrace();
        }
        return -1;
    }


}
