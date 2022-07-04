package me.metropanties.sodiumplugin.context;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class PluginContextImpl implements PluginContext {

    private static ClassScanner scanner;

    public static void runPlugin(@NotNull JavaPlugin plugin) {
        if (scanner == null) {
            scanner = new ClassScanner(plugin.getClass());
            scanner.init(plugin);
        }
    }

    @Contract(value = " -> new", pure = true)
    public static @NotNull PluginContext getContext() {
        return new PluginContextImpl();
    }

    @Override
    public <T> T getRepository(@NotNull Class<?> clazz) {
        return scanner.getRepository(clazz);
    }

    @Override
    public Set<Listener> getRegisteredListeners() {
        return scanner.getRegisteredEventListeners();
    }

}
