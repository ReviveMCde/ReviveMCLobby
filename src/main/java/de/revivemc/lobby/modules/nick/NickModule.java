package de.revivemc.lobby.modules.nick;

import de.revivemc.lobby.Lobby;
import de.revivemc.lobby.modules.database.DatabaseDriver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class NickModule {

    private final UUID uuid;
    private final DatabaseDriver databaseDriver = Lobby.getInstance().getDatabaseDriver();

    public NickModule(final UUID uuid) {
        this.uuid = uuid;
    }


    public void updateNick(String nickState) {
        this.databaseDriver.update("UPDATE `revivemc_lobby_player` SET `nick`='" + nickState + "' WHERE `uuid`='" + this.uuid + "'");
    }

    public String getNickState() {
        try {
            ResultSet resultSet = this.databaseDriver.query("SELECT * FROM `revivemc_lobby_player` WHERE UUID='" + this.uuid + "'");
            while (resultSet.next()) {
                return resultSet.getString("nick");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }


}
