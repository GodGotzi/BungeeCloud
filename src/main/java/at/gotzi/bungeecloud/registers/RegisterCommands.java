package at.gotzi.bungeecloud.registers;

import at.gotzi.bungeecloud.BungeeCloud;
import at.gotzi.bungeecloud.commands.Search_CMD;

public record RegisterCommands(BungeeCloud bungeeCloud) {

    public RegisterCommands(BungeeCloud bungeeCloud) {
        this.bungeeCloud = bungeeCloud;
        this.register();
    }

    public void register() {
        this.bungeeCloud.getProxyServer().getPluginManager().registerCommand(this.bungeeCloud,
                new Search_CMD("search"));
    }
}
