package at.gotzi.bungeecloud.commands;



import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Search_CMD extends Command{

    public Search_CMD(String name) {
        super(name);

    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer p) {
            if(p.hasPermission("player")) {
                if(args.length == 0) {
                    p.sendMessage("Bitte benutze /search [Player]");
                } else if (args.length == 1) {
                    ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
                    ServerInfo serverInfo = target.getServer().getInfo();
                    String info = target.getServer().getInfo().getName();
                    if(target == null || info == null) {
                        p.sendMessage("Der Spieler befindet sich nicht auf dem Netzwerk!");
                    } else {
                        p.sendMessage("Der Spieler " + target.getName() + " befindet sich auf dem Server " + info);
                        TextComponent clickableComponent = new TextComponent(info);
                        clickableComponent.setClickEvent(onClick(p, serverInfo));
                    }
                } else
                    p.sendMessage("Bitte benutze /search [Player]");
            }
        } else
            sender.sendMessage("Du bist kein Spieler!");
    }
    public ClickEvent onClick(ProxiedPlayer p, ServerInfo info) {
        p.connect(info);
        return null;
    }
}
