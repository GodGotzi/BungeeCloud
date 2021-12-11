package at.gotzi.bungeecloud.api;

import java.util.logging.Logger;

public record GotziLogger(Logger logger) {

    public synchronized void info(String msg) {
        this.logger.info(Colors.CYAN + "[" +
                Colors.GREEN + "Information" + Colors.CYAN + "] " +  Colors.PURPLE + msg + Colors.RESET);
    }

    public synchronized void info(String msg, Class<?> cls) {
        this.logger.info( Colors.BLUE +
                "Class: " +
                        cls.getCanonicalName()  + " " + Colors.CYAN +
                        "["+ Colors.GREEN + "Information" + Colors.CYAN + "] " +  Colors.PURPLE + msg + Colors.RESET);
    }

    public synchronized void warning(String msg) {
        this.logger.info(Colors.CYAN + "[" +
                Colors.YELLOW + "Warning" + Colors.CYAN + "] " +  Colors.PURPLE + msg + Colors.RESET);
    }

    public synchronized void warning(String msg, Class<?> cls) {
        this.logger.info(Colors.RED +
                "Class: " +
                cls.getCanonicalName() + " " + Colors.CYAN +
                "["+ Colors.YELLOW + "Warning" + Colors.CYAN + "] " +  Colors.PURPLE + msg + Colors.RESET);
    }

    public static String getDefaultInfo() {
        return Colors.PURPLE;
    }

    public static String getDefaultWarning() {
        return Colors.PURPLE;
    }

}