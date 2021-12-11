package at.gotzi.bungeecloud.handler.error;

import at.gotzi.bungeecloud.BungeeCloud;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ErrorHandler {

    private final File errorFolder;
    private final File oldErrorFolder;
    private final BungeeCloud htlkSystem;

    public ErrorHandler(BungeeCloud htlkSystem) {
        this.htlkSystem = htlkSystem;
        this.errorFolder = new File("..//Error");
        this.oldErrorFolder = new File("..//Error//old");
        this.createFolder();
    }

    public void createFolder() {
        if (!this.errorFolder.exists())
            this.errorFolder.mkdirs();
        if (!this.oldErrorFolder.exists())
            this.oldErrorFolder.mkdirs();
    }

    public void registerError(Class<?> cls, Exception error) {
        int i = 0;

        File file;
        do {
            file = new File(this.errorFolder.getPath() + "//" + cls.getCanonicalName() + "," + i + ".yml");
            i++;
        } while(file.exists());

        try {
            file.createNewFile();
        } catch (IOException e) {
            this.htlkSystem.getGotziLogger().warning("Cannot create Error file", this.getClass());
            e.printStackTrace();
        }

        try {
            FileWriter fileWriter = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            error.printStackTrace(printWriter);
            fileWriter.close();
        } catch (IOException e) {
            this.htlkSystem.getGotziLogger().warning("Cannot write in Error file", this.getClass());
            e.printStackTrace();
        }
    }

    public File getErrorFolder() {
        return errorFolder;
    }

    public File getOldErrorFolder() {
        return oldErrorFolder;
    }
}
