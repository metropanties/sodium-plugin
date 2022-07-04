package me.metropanties.sodiumplugin;

import me.metropanties.sodiumplugin.context.PluginContext;
import me.metropanties.sodiumplugin.context.PluginContextImpl;
import org.bukkit.plugin.java.JavaPlugin;

public class SodiumPlugin extends JavaPlugin {

    private final PluginContext context;

    public SodiumPlugin() {
        PluginContextImpl.runPlugin(this);
        this.context = PluginContextImpl.getContext();
    }

    public PluginContext getContext() {
        return context;
    }

}
