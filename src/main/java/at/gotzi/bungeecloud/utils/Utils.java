package at.gotzi.bungeecloud.utils;


import net.md_5.bungee.api.ChatColor;

public class Utils {

    public static int[] calPlayTime(int min) {
        int hours = (int)((double)min/(double)60);
        int minutes = min-(hours*60);
        return new int[]{minutes, hours};
    }

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

}