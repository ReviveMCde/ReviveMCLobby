package de.revivemc.lobby.modules.friend;

import de.revivemc.lobby.Lobby;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class FriendModule {

    private final UUID uuid;

    public FriendModule(final UUID uuid) {
        this.uuid = uuid;
    }

    public void addRequest(UUID targetUUID) {
        if (hasFriends() == null) {
            Lobby.getInstance().getDatabaseDriver().update("UPDATE `revivemc_friends_data` SET `Requests`= JSON_OBJECT(\"uuid\", \"" + targetUUID.toString() + "\")  WHERE `UUID` = '" + this.uuid.toString() + "'");
        }else {
            Lobby.getInstance().getDatabaseDriver().update("UPDATE `revivemc_friends_data` SET `Requests`= JSON_ARRAY_APPEND(Friends ,\"$.uuid\", \"" + targetUUID.toString() + "\")  WHERE `UUID` = '" + this.uuid.toString() + "'");
        }

    }

    public void removeRequest(UUID targetUUID) {

    }

    public void addFriend(UUID targetUUID) {
        if (hasFriends().equalsIgnoreCase("-")) {
            Lobby.getInstance().getDatabaseDriver().update("UPDATE `revivemc_friends_data` SET `Friends`='" + targetUUID.toString() + ",' WHERE `UUID` = '" + this.uuid.toString() + "'");
        }else {
            Lobby.getInstance().getDatabaseDriver().update("UPDATE `revivemc_friends_data` SET `Friends`='" + getFriendsRAW() + targetUUID.toString() +",'  WHERE `UUID` = '" + this.uuid.toString() + "'");
        }
    }

    public String hasFriends() {
        try {
            ResultSet resultSet = Lobby.getInstance().getDatabaseDriver().query("SELECT * FROM `revivemc_friends_data` WHERE  `UUID` = '" + this.uuid.toString() + "'");
            if (resultSet.next()) {
                return resultSet.getString("Friends");
            }
        }catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public String getFriendsRAW() {
        try {
            ResultSet resultSet = Lobby.getInstance().getDatabaseDriver().query("SELECT * FROM `revivemc_friends_data` WHERE  `UUID` = '" + this.uuid.toString() + "'");

            if (resultSet.next()) {
                return resultSet.getString("Friends");
            }
        }catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public String[] getFriendsArray() {
        try {
            ResultSet resultSet = Lobby.getInstance().getDatabaseDriver().query("SELECT * FROM `revivemc_friends_data` WHERE  `UUID` = '" + this.uuid.toString() + "'");

            if (resultSet.next()) {
                return resultSet.getString("Friends").split(",");
            }
        }catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public String getRequests() {
        try {
            ResultSet resultSet = Lobby.getInstance().getDatabaseDriver().query("SELECT JSON_QUERY(Requests, \"$\") as UUID FROM `revivemc_friends_data` WHERE  `UUID` = '" + this.uuid.toString() + "'");
            if (resultSet.next()) {
                return resultSet.getString("UUID");
            }
        }catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public void removeFriend(UUID targetUUID) {
        //ProxySystem.getInstance().getDatabaseDriver().update("UPDATE `revivemc_friends_data` SET `Friends`='" + getFriends() + targetUUID + "' WHERE `UUID`='" + uuid.toString() + "'");

    }

    public void acceptFriend(UUID targetUUID) {

    }

    public void denyFriend(UUID targetUUID) {

    }

    public boolean isInRequest(UUID targetUUID) {
        return false;
    }

    public boolean isFriend(UUID targetUUID) {
        return false;
    }

    public boolean playerExists(UUID uuid) {
        try {
            ResultSet rs = Lobby.getInstance().getDatabaseDriver().query("SELECT * FROM `revivemc_proxy_player` WHERE  `UUID` = '" + this.uuid + "'");
            if (rs.next()) {
                return rs.getString("UUID") != null;
            }
            return false;
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return false;
    }
}
