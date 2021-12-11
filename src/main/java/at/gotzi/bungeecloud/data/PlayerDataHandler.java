package at.gotzi.bungeecloud.data;

import at.gotzi.bungeecloud.BungeeCloud;
import at.gotzi.bungeecloud.objects.dataholder.PlayerData;

import java.util.HashMap;
import java.util.UUID;

public class PlayerDataHandler extends AbstractPlayerDataHandler {
    private static final HashMap<UUID, PlayerData> playerDataHandler = new HashMap<>();

    public PlayerDataHandler(UUID player, BungeeCloud bungeeCloud) {
        super(player, bungeeCloud);
    }

    @Override
    public void add() {
        super.construct();
        this.saveRAM();
    }

    @Override
    public void remove() {
        super.playerData = playerDataHandler.get(super.player);
        this.removeRAM();
    }

    @Override
    public void removeRAM() {
        playerDataHandler.remove(super.player);
    }

    @Override
    public void saveRAM() {
        playerDataHandler.put(super.player, super.playerData);
    }

    public PlayerData getPlayerData() {
        return super.playerData;
    }

    public static PlayerData getPlayerData(UUID player) {
        return playerDataHandler.get(player);
    }

    public static void setPlayerData(UUID player, PlayerData playerData) {
        playerDataHandler.put(player, playerData);
    }

}
