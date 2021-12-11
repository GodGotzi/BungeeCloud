package at.gotzi.bungeecloud;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

import at.gotzi.bungeecloud.api.GotziLogger;
import at.gotzi.bungeecloud.data.sql.SQLHandler;
import at.gotzi.bungeecloud.data.sql.builder.TableBuilder;
import at.gotzi.bungeecloud.discord.DiscordBot;
import at.gotzi.bungeecloud.handler.error.ErrorHandler;
import at.gotzi.bungeecloud.registers.RegisterCommands;
import at.gotzi.bungeecloud.registers.RegisterListener;
import at.gotzi.bungeecloud.server.connection.InternServerHandler;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class BungeeCloud extends Plugin {

    private SQLHandler sqlHandler;
    private GotziLogger gotziLogger;
    private ErrorHandler errorHandler;
    private InternServerHandler internServerHandler;
    private DiscordBot discordBot;

    private Configuration config;

    public boolean isDatabase;

    private final ProxyServer proxyServer = ProxyServer.getInstance();

    @Override
    public void onEnable() {
        super.onEnable();

        this.gotziLogger = new GotziLogger(super.getLogger());
        this.errorHandler = new ErrorHandler(this);
        this.internServerHandler = new InternServerHandler(this);

        File configFile = new File("bungeeCloud.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                this.gotziLogger.warning("Memory is full", getClass());
                this.errorHandler.registerError(getClass(), e);
            }
        }

        try {
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e) {
            this.gotziLogger.warning("BungeeCloud config could not be loaded", getClass());
            this.errorHandler.registerError(getClass(), e);
        }

        this.sqlHandler = new SQLHandler(this);
        this.gotziLogger.info("Connected to Database");

        this.discordBot = new DiscordBot(this);
        this.gotziLogger.info("Connected to DiscordBot \"ServerHandler\"");
        this.initializeTables();

        new RegisterCommands(this);
        new RegisterListener(this);
    }

    private void initializeTables() {
        new TableBuilder("playTime",
                new TableBuilder.Data[]{
                        new TableBuilder.Data("PLAYER", SQLHandler.DataType.VARCHAR, 40),
                        new TableBuilder.Data("MINUTES", SQLHandler.DataType.INT, 255)},
                this);
        new TableBuilder("registered",
                new TableBuilder.Data[]{
                        new TableBuilder.Data("PLAYER", SQLHandler.DataType.VARCHAR, 40),
                        new TableBuilder.Data("REGISTERED", SQLHandler.DataType.TINYINT, 1)},
                this);
        this.gotziLogger.info("Tables has been created!", this.getClass());
    }

    public ServerInfo getServerByPort(int port) {
        for (ServerInfo serverInfo : getProxy().getServers().values()) {
            if (serverInfo.getAddress().getPort() == port)
                return serverInfo;
        }
        return null;
    }

    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }

    public GotziLogger getGotziLogger() {
        return gotziLogger;
    }

    public SQLHandler getSqlHandler() {
        return sqlHandler;
    }

    public Configuration getConfig() {
        return this.config;
    }

    public DiscordBot getDiscordBot() {
        return discordBot;
    }

    public ProxyServer getProxyServer() {
        return proxyServer;
    }

    @Override
    public void onDisable() {
        this.gotziLogger.info("Shutdown");
        this.discordBot.getJda().shutdown();

        try {
            this.internServerHandler.getInternServerSocket().close();
        } catch (IOException e) {
            this.gotziLogger.warning("InternServerSocket couldn't shutdown", getClass());
            this.errorHandler.registerError(getClass(), e);
        }
    }

}
