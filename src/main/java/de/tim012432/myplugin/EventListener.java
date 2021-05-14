package de.tim012432.myplugin;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerLocallyInitializedEvent;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.scheduler.NukkitRunnable;

/**
 * Event listenenr for my plugin.
 */
public class EventListener implements Listener {
    MyPlugin plugin;

    public EventListener(MyPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerSpawned(PlayerLocallyInitializedEvent event) {
        Player player = event.getPlayer();
        if(plugin.config.joinDelay <= 0) {
            displayForm(player);
        } else {
            new NukkitRunnable(){
                public void run() {
                    if(player.isOnline()) {
                        displayForm(player);
                    }
                }
            }.runTaskLater(plugin, plugin.config.joinDelay);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onFormResponse(PlayerFormRespondedEvent event) {
        if (event.getResponse() == null) return;
        if (event.getWindow().wasClosed()) return;
        if (event.getWindow() instanceof FormWindowSimple) {
            if (plugin.config.title.equals(((FormWindowSimple) event.getWindow()).getTitle())) {
                if (plugin.config.button.equals(((FormWindowSimple) event.getWindow()).getResponse().getClickedButton().getText())) {
                    return;
                }
            }
        }
    }

    public void displayForm(Player player) {
        ConfigFetcher config = plugin.config;
        new Form(plugin.getServer(), player, config.title, config.welcoming, config.text, config.button);
    }
}
