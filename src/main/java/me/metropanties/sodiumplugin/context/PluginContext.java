package me.metropanties.sodiumplugin.context;

import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface PluginContext {

    @Nullable
    <T> T getRepository(@NotNull Class<?> clazz);

    Set<Listener> getRegisteredListeners();

}
