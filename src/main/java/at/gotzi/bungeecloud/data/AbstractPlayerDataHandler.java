package at.gotzi.bungeecloud.data;

import at.gotzi.bungeecloud.BungeeCloud;
import at.gotzi.bungeecloud.data.sql.data.SQLPlayerData;
import at.gotzi.bungeecloud.objects.dataholder.PlayerData;

import java.util.UUID;

public abstract class AbstractPlayerDataHandler {

    protected final UUID player;
    private final BungeeCloud bungeeCloud;
    private SQLPlayerData sqlPlayerData;
    protected PlayerData playerData;

    public AbstractPlayerDataHandler(UUID player, BungeeCloud bungeeCloud) {
        this.player = player;
        this.bungeeCloud = bungeeCloud;
    }

    public void construct() {
        this.sqlPlayerData = new SQLPlayerData(player, this.bungeeCloud);
        this.playerData = new PlayerData(Integer.parseInt(this.sqlPlayerData.getValue(SQLPlayerData.PlayTime.class).getObj().toString()),
                Boolean.getBoolean(this.sqlPlayerData.getValue(SQLPlayerData.Registered.class).getObj().toString()));
    }

    public abstract void add();
    public abstract void remove();
    public abstract void saveRAM();
    public abstract void removeRAM();
}
