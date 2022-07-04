package me.metropanties.sodiumplugin.context;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import me.metropanties.sodiumplugin.database.mongo.MongoRepository;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;

public class ClassScanner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassScanner.class);

    private final Reflections reflections;
    private final HashMap<Class<?>, Object> registeredRepositories = Maps.newHashMap();
    private final Set<Listener> registeredEventListeners = Sets.newHashSet();

    private JavaPlugin plugin;

    public ClassScanner(@NotNull Class<? extends JavaPlugin> pluginClass) {
        String packageName = pluginClass.getPackageName();
        this.reflections = new Reflections(packageName);
    }

    public void init(@NotNull JavaPlugin plugin) {
        this.plugin = plugin;
        scanRepositories();
        scanEventListeners();
    }

    private void scanRepositories() {
        Set<Class<? extends MongoRepository>> repositoryClasses = reflections.getSubTypesOf(MongoRepository.class);
        for (Class<? extends MongoRepository> clazz : repositoryClasses) {
            if (clazz == null)
                continue;

            try {
                MongoRepository<?, ?> instance = clazz.getDeclaredConstructor().newInstance();
                registeredRepositories.put(clazz, instance);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    private void scanEventListeners() {
        Set<Class<? extends Listener>> listenerClasses = reflections.getSubTypesOf(Listener.class);
        for (Class<? extends Listener> clazz : listenerClasses) {
            if (clazz == null)
                continue;

            try {
                Listener instance = clazz.getDeclaredConstructor().newInstance();
                plugin.getServer().getPluginManager().registerEvents(instance, plugin);
                registeredEventListeners.add(instance);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public <T> T getRepository(@NotNull Class<?> clazz) {
        return (T) registeredRepositories.get(clazz);
    }

    public HashMap<Class<?>, Object> getRegisteredRepositories() {
        return registeredRepositories;
    }

    public Set<Listener> getRegisteredEventListeners() {
        return registeredEventListeners;
    }

}
