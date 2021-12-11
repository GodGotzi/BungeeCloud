package at.gotzi.bungeecloud.objects.dataholder;

import at.gotzi.bungeecloud.data.sql.data.SQLPlayerData;
import at.gotzi.bungeecloud.data.sql.getter.AbstractSQLGetter;

import java.util.HashMap;

public class PlayerData {

    private final int minute;
    private final boolean registered;

    public PlayerData(int minute, boolean registered) {
        this.minute = minute;
        this.registered = registered;
    }

    private final HashMap<Class<? extends AbstractSQLGetter<?>>, Value<?>> values = new HashMap<>();


    public Value<?> getValue(Class<? extends AbstractSQLGetter<?>> cls) {
        if (cls == SQLPlayerData.PlayTime.class)
            return new Value<>(this.minute);
        else if (cls == SQLPlayerData.Registered.class) {
            return new Value<>(this.registered);
        }
        return null;
    }

    public Class<? extends AbstractSQLGetter<?>>[] getClasses() {
        return new Class[]{SQLPlayerData.PlayTime.class};
    }
}
