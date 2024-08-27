package de.revivemc.lobby.modules.database.node;

import de.revivemc.lobby.Lobby;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerCooldown {
    public static boolean playerExists(UUID uuid) {
        try {
            ResultSet rs = Lobby.getInstance().getDatabaseDriver().query("SELECT * FROM  `revivemc_proxy_player` WHERE  `UUID` = '" + uuid.toString() + "'");
            if (rs.next()) {
                return rs.getString("UUID") != null;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Long getTime(UUID uuid) {
        Long i = 0L;
        if (playerExists(uuid)) {
            try {
                ResultSet rs = Lobby.getInstance().getDatabaseDriver().query("SELECT * FROM cooldown WHERE UUID= '" + uuid + "'");
                if (rs.next()) {
                    Long.valueOf(rs.getString("time"));
                }
                i = Long.valueOf(rs.getString("time"));
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }


    public static void setTime(UUID uuid, String time) {
        if (playerExists(uuid)) {
            Lobby.getInstance().getDatabaseDriver().update("UPDATE cooldown SET time = '" + time + "' WHERE UUID= '" + uuid + "';");
        }
    }


    public static boolean isInCooldown(UUID uuid) {
        Long time = getTime(uuid);
        if((time)>System.currentTimeMillis()) {
            return true;
        }
        return false;
    }

    public static String remainingTime(UUID uuid) {
        long current = System.currentTimeMillis();
        long end = getTime(uuid);
        if (end == -1L) {
            return "§eFEHLER";
        }
        long millis = end - current;

        long seconds = 0L;
        long minutes = 0L;
        long hours = 0L;
        long days = 0L;
        long weeks = 0L;
        long years = 0L;
        while (millis > 1000L) {
            millis -= 1000L;
            seconds += 1L;
        }
        while (seconds > 60L) {
            seconds -= 60L;
            minutes += 1L;
        }
        while (minutes > 60L) {
            minutes -= 60L;
            hours += 1L;
        }
        while (hours > 24L) {
            hours -= 24L;
            days += 1L;
        }
        while (days > 7L) {
            days -= 7L;
            weeks += 1L;
        }
        while (weeks > 52L) {
            weeks -= 52L;
            years += 1L;
        }
        String result = "";
        if (years != 0L) {
            if (years == 1L) {
                result = result + "§c" + years + " Jahr ";
            } else {
                result = result + "§c" + years + " Jahre ";
            }
        }

        if (weeks != 0L) {
            if (weeks == 1L) {
                result = result + "§c" + weeks + " Woche ";
            } else {
                result = result + "§c" + weeks + " Wochen ";
            }
        }

        if (days != 0L) {
            if (days == 1L) {
                result = result + "§c" + days + " Tag ";
            } else {
                result = result + "§c" + days + " Tage ";
            }
        }

        if (hours != 0L) {
            if (hours == 1L) {
                result = result + "§c" + hours + " Stunde ";
            } else {
                result = result + "§c" + hours + " Stunden ";
            }
        }
        if (minutes != 0L) {
            if (minutes == 1L) {
                result = result + "§c" + minutes + " Minute ";
            } else {
                result = result + "§c" + minutes + " Minuten ";
            }
        }

        if (seconds != 0L) {
            if (seconds == 1L) {
                result = result + "§c" + seconds + " Sekunde ";
            } else {
                result = result + "§c" + seconds + " Sekunden ";
            }
        }
        return result;
    }
}
