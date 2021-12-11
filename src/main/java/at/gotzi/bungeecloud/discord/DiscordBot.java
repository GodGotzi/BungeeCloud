package at.gotzi.bungeecloud.discord;

import at.gotzi.bungeecloud.BungeeCloud;
import at.gotzi.bungeecloud.discord.error.ErrorScheduler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;

import javax.security.auth.login.LoginException;
import java.util.List;

public class DiscordBot {

    private final BungeeCloud bungeeCloud;
    private final ErrorScheduler errorScheduler;
    private JDA jda;

    public DiscordBot(BungeeCloud bungeeCloud) {
        this.bungeeCloud = bungeeCloud;
        this.build();
        this.errorScheduler = new ErrorScheduler(bungeeCloud, this);
    }

    private void build() {
        JDABuilder jdaBuilder = JDABuilder.createDefault(
                this.bungeeCloud.getConfig().getString("dcToken")
        );

        try {
            this.jda = jdaBuilder.build();
        } catch (LoginException e) {
            this.bungeeCloud.getGotziLogger().warning("Discord Bot Error", this.getClass());
            this.bungeeCloud.getErrorHandler().registerError(this.getClass(), e);
        }
    }

    public List<Guild> getGuilds() {
        return this.jda.getGuilds();
    }

    public ErrorScheduler getErrorScheduler() {
        return errorScheduler;
    }

    public JDA getJda() {
        return jda;
    }
}
