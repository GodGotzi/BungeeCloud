package at.gotzi.bungeecloud.data.sql;

import at.gotzi.bungeecloud.BungeeCloud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHandler {

    private final BungeeCloud bungeeCloud;

    public SQLHandler(BungeeCloud htlkSystem) {
        this.bungeeCloud = htlkSystem;
        this.host = htlkSystem.getConfig().getString("host");
        this.port = htlkSystem.getConfig().getString("port");
        this.database = htlkSystem.getConfig().getString("database");
        this.user = htlkSystem.getConfig().getString("user");
        this.password = htlkSystem.getConfig().getString("password");
    }

    private final String host;
    private final String port;
    private final String database;
    private final String user;
    private final String password;

    private Connection connection;

    public boolean isConnected() {
        return connection != null;
    }

    public void connect() throws SQLException {
        if (!isConnected()) {
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?useSSL=false",
                    this.user,
                    this.password);
        }
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public enum DataType {
        VARCHAR,
        INT,
        DOUBLE,
        TINYINT;
    }

    public String getDatabase() {
        return database;
    }

    public String getHost() {
        return host;
    }

    public String getPassword() {
        return password;
    }

    public String getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }
}
