package at.gotzi.bungeecloud.discord.error;

import at.gotzi.bungeecloud.discord.DiscordBot;
import com.google.common.base.Splitter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class ErrorSender {

    private final DiscordBot discordBot;
    private final String msg;
    private final String title;
    private MessageEmbed messageEmbed;

    public ErrorSender(String msg, String title, DiscordBot discordBot) {
        this.msg = msg;
        this.title = title;
        this.discordBot = discordBot;
        this.build();
        this.send();
    }

    private void build() {
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle(this.title)
                .setAuthor("ErrorHandler");
        this.messageEmbed = embedBuilder.build();
    }

    private void send() {
        for (Guild g : this.discordBot.getGuilds()) {
            List<TextChannel> textChannels = g.getTextChannelsByName("╠-errors", false);
            for (TextChannel textChannel : textChannels) {
                textChannel.sendMessage(this.messageEmbed).queue();
                this.sendMsg(textChannel);
            }
        }
    }

    private void sendMsg(TextChannel textChannel) {
        Iterable<String> pieces = Splitter.fixedLength(1800).split(this.msg);
        for (String s : pieces) {
            textChannel.sendMessage(s).queue();
        }
    }
}
