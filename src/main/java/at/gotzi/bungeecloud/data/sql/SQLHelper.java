package at.gotzi.bungeecloud.data.sql;

import at.gotzi.bungeecloud.BungeeCloud;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLHelper {

    private final BungeeCloud bungeeCloud;
    private final Connection connection;

    public SQLHelper(BungeeCloud bungeeCloud) {
        this.bungeeCloud = bungeeCloud;
        this.connection = bungeeCloud.getSqlHandler().getConnection();
    }

    public void closeConnection() {
        try {
            if (this.connection != null && !this.connection.isClosed())
                this.connection.close();
        } catch (SQLException e) {
            this.bungeeCloud.getGotziLogger().warning("Sql could not close Connection", this.getClass());
            this.bungeeCloud.getErrorHandler().registerError(this.getClass(), e);
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

}