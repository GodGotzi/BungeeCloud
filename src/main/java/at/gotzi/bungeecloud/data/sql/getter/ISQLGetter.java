package at.gotzi.bungeecloud.data.sql.getter;

import at.gotzi.bungeecloud.data.sql.SQLHandler;
import at.gotzi.bungeecloud.objects.dataholder.Value;

public interface ISQLGetter {

    void create();

    boolean exists();

    void update(int time, String from);

    void update(Value<?> value, String from);

    Value<?> getValue(SQLHandler.DataType dataType);

}