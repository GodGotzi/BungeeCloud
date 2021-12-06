package at.gotzi.bungeecloud.registers;


import at.gotzi.bungeecloud.BungeeCloud;
import at.gotzi.bungeecloud.listener.player.ConnectDisconnectListener;
import net.md_5.bungee.api.plugin.PluginManager;

public class RegisterListener {

    private final PluginManager pluginManager;
    private final BungeeCloud bungeeCloud;

    public RegisterListener(BungeeCloud bungeeCloud) {
        this.bungeeCloud = bungeeCloud;
        this.pluginManager = bungeeCloud.getProxyServer().getPluginManager();
        this.register();
    }

    public void register() {
        this.pluginManager.registerListener(this.bungeeCloud, new ConnectDisconnectListener(this.bungeeCloud));
    }
}
