package at.gotzi.bungeecloud.data.sql.data;

import at.gotzi.bungeecloud.BungeeCloud;
import at.gotzi.bungeecloud.data.sql.SQLHelper;
import at.gotzi.bungeecloud.objects.dataholder.Value;
import at.gotzi.bungeecloud.data.sql.SQLHandler;
import at.gotzi.bungeecloud.data.sql.getter.AbstractSQLGetter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class SQLPlayerData extends SQLHelper {

    private final PlayTime playTime;
    private final Registered registered;
    private final BungeeCloud bungeeCloud;

    public SQLPlayerData(UUID player, BungeeCloud bungeeCloud) {
        super(bungeeCloud);
        this.bungeeCloud = bungeeCloud;
        this.playTime = new PlayTime("playTime", player, bungeeCloud, super.getConnection());
        this.registered = new Registered("registered", player, bungeeCloud, super.getConnection());
    }

    public void create(Class<? extends AbstractSQLGetter<?>> cls) {
        if (cls == PlayTime.class)
            this.playTime.create();
        else if (cls == Registered.class) {
            this.registered.create();
        }
    }

    public boolean exists(Class<? extends AbstractSQLGetter<?>> cls) {
        if (cls == PlayTime.class)
            return this.playTime.exists();
        else if (cls == Registered.class) {
            return this.registered.exists();
        }
        return false;
    }

    public void update(Class<? extends AbstractSQLGetter<?>> cls, Value<?> value) {
        if (cls == PlayTime.class)
            this.playTime.update(value, "minutes");
        else if (cls == Registered.class) {
            this.registered.update(value, "registered");
        }
    }

    public Value<?> getValue(Class<? extends AbstractSQLGetter<?>> cls) {
        if (cls == PlayTime.class)
            return this.playTime.getValue(SQLHandler.DataType.INT);
        else if (cls == Registered.class)
            return this.playTime.getValue(SQLHandler.DataType.TINYINT);
        return null;
    }

    public static class PlayTime extends AbstractSQLGetter<UUID> {
        private PlayTime(String table, UUID player, BungeeCloud bungeeCloud, Connection connection) {
            super(table, player, "MINUTES", "PLAYER", "0", bungeeCloud, connection);
        }
    }

    public static class Registered extends AbstractSQLGetter<UUID> {
        public Registered(String table, UUID player, BungeeCloud bungeeCloud, Connection connection) {
            super(table, player, "REGISTERED", "PLAYER", "false", bungeeCloud, connection);
        }
    }
}
