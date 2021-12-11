package at.gotzi.bungeecloud.listener.player;

import at.gotzi.bungeecloud.BungeeCloud;
import at.gotzi.bungeecloud.api.Colors;
import at.gotzi.bungeecloud.api.GotziLogger;
import at.gotzi.bungeecloud.data.PlayerDataHandler;
import at.gotzi.bungeecloud.data.sql.data.SQLPlayerData;
import at.gotzi.bungeecloud.objects.dataholder.PlayerData;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public record ConnectDisconnectListener(BungeeCloud bungeeCloud) implements Listener {

    @EventHandler
    public void onConnect(ServerConnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        this.bungeeCloud.getProxyServer().getScheduler().runAsync(this.bungeeCloud, () -> {
            this.connectMysql(player);
        });
    }

    @EventHandler
    public void onDisconnect(ServerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();

        this.bungeeCloud.getProxyServer().getScheduler().runAsync(this.bungeeCloud, () -> {
            this.disconnectMysql(player);
        });
    }

    public void connectMysql(ProxiedPlayer player) {
        try {
            PlayerDataHandler playerDataHandler = new PlayerDataHandler(player.getUniqueId(),
                    this.bungeeCloud);
            playerDataHandler.add();
            PlayerData playerData = playerDataHandler.getPlayerData();
            playerDataHandler.getSqlPlayerData().closeConnection();

            this.bungeeCloud.getGotziLogger().info(player.getUniqueId() +
                    " logged " + Colors.GREEN + "in" + GotziLogger.getDefaultInfo() + " with data: time(" +
                    playerData.getValue(SQLPlayerData.PlayTime.class).toString() +
                    "), registered(" +
                    playerData.getValue(SQLPlayerData.Registered.class).toString() + ")");
        } catch (Exception e) {
            this.bungeeCloud.getGotziLogger().warning("PlayerData Error", this.getClass());
            this.bungeeCloud.getErrorHandler().registerError(this.getClass(), e);
        }
    }

    public void disconnectMysql(ProxiedPlayer player) {
        try {
            PlayerDataHandler playerDataHandler = new PlayerDataHandler(player.getUniqueId(), this.bungeeCloud);
            playerDataHandler.remove();
            PlayerData playerData = playerDataHandler.getPlayerData();
            this.bungeeCloud.getGotziLogger().info(player.getUniqueId() +
                    " logged " + Colors.RED + "out" + GotziLogger.getDefaultInfo() + " with data: time(" +
                    playerData.getValue(SQLPlayerData.PlayTime.class).toString() +
                    "), registered(" +
                    playerData.getValue(SQLPlayerData.Registered.class).toString() + ")");
            if (playerDataHandler.getSqlPlayerData() != null)
                playerDataHandler.getSqlPlayerData().closeConnection();
        } catch (Exception e) {
            this.bungeeCloud.getGotziLogger().warning("PlayerData Error", this.getClass());
            this.bungeeCloud.getErrorHandler().registerError(this.getClass(), e);
        }
    }

}
