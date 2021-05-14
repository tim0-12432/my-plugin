package de.tim012432.myplugin;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.event.Listener;

/**
 * Main class of my plugin.
 */
public class MyPlugin extends PluginBase implements Listener {

    public ConfigFetcher config;

    private static MyPlugin instance;

    public static MyPlugin getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        if (!this.getServer().getName().equals("Nukkit")) {
            this.getLogger().warning(TextFormat.DARK_RED  + "MobPlugin does not support this software.");
            this.getLogger().error(TextFormat.DARK_RED  + "Incompatible server software. The plugin will be disabled.");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        } else if (!this.getServer().getCodename().isEmpty()) {
            this.getLogger().warning(TextFormat.DARK_RED  + "MobPlugin does not support unofficial Nukkit versions!");
        } else {
            this.getLogger().info(TextFormat.DARK_GREEN + "MyPlugin enabled!");
        }

        config = new ConfigFetcher(this);

        if (!config.init(this)) {
            return;
        }

        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
    }

    @Override
    public void onDisable() {
        this.getLogger().info(TextFormat.GOLD + "MyPlugin disabled!");
    }
}