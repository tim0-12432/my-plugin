package de.tim012432.myplugin;

import cn.nukkit.utils.Config;

/**
 * Class for fetching the plugin configuration.
 */
public class ConfigFetcher {

    public Config pluginConfig;

    /** variables defined in the config. */
    public int joinDelay;
    public String title;
    public String welcoming;
    public String text;
    public String button;

    /** constructor. */
    public ConfigFetcher(MyPlugin plugin) {
        plugin.saveDefaultConfig();
        pluginConfig = plugin.getConfig();
    }

    /** initialization method fetching the config file. */
    public boolean init(MyPlugin plugin) {
        int version = 2;

        if(pluginConfig.getInt("config-version") != version) {
            if(pluginConfig.getInt("config-version") == 1) {
                pluginConfig.set("joinDelay", 1);
                pluginConfig.set("title", "Welcome");
                pluginConfig.set("welcoming", "Hi");
                pluginConfig.set("text", "Welcome on this server!");
                pluginConfig.set("button", "okay");
            } else {
                plugin.getLogger().warning("MobPlugin's config file is outdated. Please delete the old config.");
                plugin.getLogger().error("Config error. The plugin will be disabled.");
                plugin.getServer().getPluginManager().disablePlugin(plugin);
                return false;
            }
            pluginConfig.set("config-version", version);
            pluginConfig.save();
            plugin.getLogger().notice("Config file updated to version " + version);
        }
        joinDelay = pluginConfig.getInt("joinDelay");
        title = pluginConfig.getString("title");
        welcoming = pluginConfig.getString("welcoming");
        text = pluginConfig.getString("text");
        button = pluginConfig.getString("button");
        return true;
    }
}