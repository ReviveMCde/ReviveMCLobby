package de.revivemc.lobby.modules.lobby;

import de.revivemc.core.gameutils.sync.GameSync;
import de.revivemc.core.playerutils.ReviveMCPlayer;

public class LobbyModule {

    public LobbyModule(ReviveMCPlayer cyturaPlayer) {
        addShopLobbySync(cyturaPlayer);
    }

    public void addShopLobbySync(ReviveMCPlayer cyturaPlayer) {
        final GameSync gameSync = new GameSync(cyturaPlayer);
        gameSync.addUserSync(new String[]{"lobby.firework.status", "lobby.firework.buy"}, "false", "false");
    }
}
