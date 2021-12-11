package at.gotzi.bungeecloud.data.sql.getter;

import at.gotzi.bungeecloud.BungeeCloud;
import at.gotzi.bungeecloud.data.sql.SQLHelper;
import at.gotzi.bungeecloud.objects.dataholder.Value;
import at.gotzi.bungeecloud.data.sql.SQLHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractSQLGetter<T> implements ISQLGetter {

    private final BungeeCloud bungeeCloud;
    private final String table;
    private final T obj;
    private final String val;
    private final String key;
    private final String nativeData;
    private final Connection connection;

    public AbstractSQLGetter(String table, T obj, String val, String key, String nativeData,
                             BungeeCloud bungeeCloud, Connection connection) {
        this.table = table;
        this.obj = obj;
        this.bungeeCloud = bungeeCloud;
        this.val = val;
        this.key = key;
        this.nativeData = nativeData;
        this.connection = connection;

        if (!this.exists())
            this.create();
    }

    @Override
    public void create() {
        StringBuilder perform = new StringBuilder();
        perform.append("INSERT INTO ")
                .append(this.table)
                .append("(")
                .append(this.key)
                .append(", ")
                .append(this.val)
                .append(") VALUES(")
                .append("\"")
                .append(this.obj.toString())
                .append("\"")
                .append(", ")
                .append(this.nativeData)
                .append(")");
        try {
            if (!exists()) {
                PreparedStatement ps1 = this.connection.prepareStatement(perform.toString());
                ps1.executeUpdate();
            }
        } catch (SQLException e) {
            this.bungeeCloud.getGotziLogger().warning("Sql cannot create if not exists", this.getClass());
            this.bungeeCloud.getErrorHandler().registerError(this.getClass(), e);
        }
    }

    @Override
    public void update(int o, String from) {
        StringBuilder perform = new StringBuilder();
        perform.append("UPDATE ")
                .append(this.table)
                .append(" SET ")
                .append(this.val)
                .append("=? WHERE ")
                .append(from)
                .append("=?");

        try {
            PreparedStatement ps = this.connection.prepareStatement(perform.toString());
            ps.setInt(1, o);
            ps.setString(2, this.obj.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            this.bungeeCloud.getGotziLogger().warning("Sql cannot update if exists", this.getClass());
            this.bungeeCloud.getErrorHandler().registerError(this.getClass(), e);
        }

    }

    @Override
    public void update(Value<?> value, String from) {
        StringBuilder perform = new StringBuilder();
        perform.append("UPDATE ")
                .append(this.table)
                .append(" SET ")
                .append(this.val)
                .append("=? WHERE ")
                .append(from)
                .append("=?");
        try {
            PreparedStatement ps = this.connection.prepareStatement(perform.toString());
            ps.setString(1, this.obj.toString());
            ps.setString(2, value.getObj().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            this.bungeeCloud.getGotziLogger().warning("Sql cannot update if exists", this.getClass());
            this.bungeeCloud.getErrorHandler().registerError(this.getClass(), e);
        }

    }

    @Override
    public boolean exists() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM ").append(this.table).append(" WHERE ").append(this.key).append("=?");
        try {
            PreparedStatement ps = this.connection.prepareStatement(stringBuilder.toString());
            ps.setString(1, this.obj.toString());
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            this.bungeeCloud.getGotziLogger().warning("Sql cannot check if exists", this.getClass());
            this.bungeeCloud.getErrorHandler().registerError(this.getClass(), e);
        }
        return false;
    }

    @Override
    public Value<?> getValue(SQLHandler.DataType dataType) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT ")
                .append(this.key)
                .append(", ")
                .append(this.val)
                .append(" FROM ")
                .append(this.table)
                .append(" WHERE ")
                .append(this.key)
                .append("=").append("\"").append(this.obj.toString()).append("\"");
        try {
            PreparedStatement ps = this.connection.prepareCall(stringBuilder.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Value<>(rs.getString(2));
            }
            return null;
        } catch (SQLException e) {
            this.bungeeCloud.getGotziLogger().warning("Sql cannot check if exists", this.getClass());
            this.bungeeCloud.getErrorHandler().registerError(this.getClass(), e);
        }
        return null;
    }

    //"SELECT id_asp, ficha, apellido1, apellido2, nombre, genero, telefono1, telefono2, promedio_sec FROM datos WHERE id_asp = ?";

    public T getObj() {
        return obj;
    }

    public String getTable() {
        return table;
    }

    public String getVal() {
        return val;
    }
}
