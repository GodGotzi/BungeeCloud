package at.gotzi.bungeecloud.discord.error;

import at.gotzi.bungeecloud.BungeeCloud;
import at.gotzi.bungeecloud.api.GotziRunnable;
import at.gotzi.bungeecloud.discord.DiscordBot;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public record ErrorScheduler(BungeeCloud bungeeCloud,
                             DiscordBot discordBot) {

    public ErrorScheduler(BungeeCloud bungeeCloud, DiscordBot discordBot) {
        this.bungeeCloud = bungeeCloud;
        this.discordBot = discordBot;
        this.start();
    }

    public void start() {
        new GotziRunnable() {
            @Override
            public void run() {
                try {
                    File errorFolder = new File("..//Error");
                    for (File f : Objects.requireNonNull(errorFolder.listFiles())) {
                        if (!f.isDirectory()) {
                            new ErrorSender(getFileContent(f),
                                    f.getName().split(",")[0] + "," +
                                            f.getName().split(",")[1].split("\\.")[0], discordBot);
                            f.renameTo(new File("..//Error//old//" + f.getName()));
                        }
                    }
                } catch (Exception e) {
                    bungeeCloud.getGotziLogger().warning("DiscordBot ErrorScheduler failed", ErrorScheduler.class);
                    bungeeCloud.getErrorHandler().registerError(ErrorScheduler.class, e);
                }
            }
        }.runRepeatingTaskAsync(60000);
    }

    private String getFileContent(@NotNull File file) throws FileNotFoundException {
        final StringBuilder stringBuilder = new StringBuilder();
        Scanner fileReader = new Scanner(file);
        while (fileReader.hasNextLine()) {
            stringBuilder.append("\n").append(fileReader.nextLine());
        }
        return stringBuilder.toString();
    }
}
