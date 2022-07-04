package me.metropanties.sodiumplugin.util;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class Chat {

    @NotNull
    public static String color(@NotNull String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    @NotNull
    public static String strip(@NotNull String string) {
        return ChatColor.stripColor(string);
    }

}
