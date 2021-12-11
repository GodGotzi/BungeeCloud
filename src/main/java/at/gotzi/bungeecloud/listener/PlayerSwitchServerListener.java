package at.gotzi.bungeecloud.listener;

import java.io.File;
import java.io.IOException;

import at.gotzi.bungeecloud.BungeeCloud;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;
public record PlayerSwitchServerListener(BungeeCloud htlkSystem) implements Listener{

    @EventHandler
    public void onSwitch(ServerConnectEvent e) throws IOException {
        ProxiedPlayer p = e.getPlayer();
        String servertargetInfo = e.getTarget().getName();
        File file = new File(this.htlkSystem.getDataFolder().getPath(), "config.yml");
        Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
    }

}
